package com.project.voa.service;

import com.project.voa.domain.UserInfo;
import com.project.voa.dto.LoginUserInfoDto;
import com.project.voa.dto.UserInfoDto;
import com.project.voa.dto.UserInfoModel;
import com.project.voa.jwt.JwtTokenInfo;
import com.project.voa.jwt.JwtTokenProvider;
import com.project.voa.repository.TeamRepository;
import com.project.voa.repository.UserInfoRepository;
import com.project.voa.error.ErrorCodes;
import com.project.voa.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
	private final UserInfoRepository userInfoRepository;
	private final TeamRepository teamRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	/**
	 * 사용자 등록
	 * @param userInfoDto
	 * @return
	 */
	public UserInfoModel insertUser(UserInfoDto userInfoDto) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userInfoDto.getUserName());
		userInfo.setPassword(userInfoDto.getPassword());
		userInfo.setUserEmail(userInfoDto.getUserEmail());
		userInfo.setProfile(userInfoDto.getProfile());
		userInfo.setTeam(teamRepository.findById(NumberUtils.toLong(userInfoDto.getTeamId(), 0))
				.orElseThrow(() -> new EntityNotFoundException(ErrorCodes.TEAM_NOT_FOUND.name())));

		if (userInfoRepository.existsByUserEmail(userInfoDto.getUserEmail())) {
			throw new DuplicateKeyException(ErrorCodes.DUPLICATED_EMAIL.name());
		}
		return UserInfoModel.of(userInfoRepository.save(userInfo));
	}

	/**
	 * 사용자들 목록 조회
	 * @return
	 */
	public List<UserInfoModel> getUsers() {
		return UserInfoModel.of((List<UserInfo>) userInfoRepository.findAll());
	}

	/**
	 * 로그인 성공/실패
	 * @param dto 로그인 정보
	 * @return 이동할 URL
	 */
	@Transactional
	public JwtTokenInfo login(HttpServletResponse response, final LoginUserInfoDto dto) {
		UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(dto.getEmail());
		if (Objects.isNull(userInfo)) {
			throw new UsernameNotFoundException(ErrorCodes.EMAIL_NOT_FOUND.name());
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
		// 검증
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 검증된 인증 정보로 JWT 토큰 생성
		JwtTokenInfo jwtTokenInfo = jwtTokenProvider.generateToken(authentication);

		// token을 쿠키에 저장
		CookieUtils.setCookie(
				response,
				"token",
				jwtTokenInfo.getAccessToken(),
				(int)Duration.ofDays(1).toSeconds(),
				"/",
				true);
		// 검증된 인증 정보로 JWT 토큰 생성
		return jwtTokenInfo;
	}
}