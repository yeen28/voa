package com.project.voa.service;

import com.project.voa.domain.UserInfo;
import com.project.voa.dto.LoginUserInfoDto;
import com.project.voa.dto.UserInfoDto;
import com.project.voa.dto.UserInfoModel;
import com.project.voa.jwt.JwtTokenInfo;
import com.project.voa.jwt.JwtTokenProvider;
import com.project.voa.repository.UserInfoRepository;
import com.project.voa.error.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
	private final UserInfoRepository userInfoRepository;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	/**
	 * 사용자 등록
	 * @return
	 */
	public UserInfoModel insertUser(UserInfoDto userInfoDto) {
		UserInfo userInfo = UserInfo.of(userInfoDto);

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
	public JwtTokenInfo login(final LoginUserInfoDto dto) {
		UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(dto.getEmail());
		if (Objects.isNull(userInfo)) {
			throw new UsernameNotFoundException(ErrorCodes.EMAIL_NOT_FOUND.name());
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
		// 검증
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 검증된 인증 정보로 JWT 토큰 생성
		return jwtTokenProvider.generateToken(authentication);
	}

	/**
	 * session으로 인증하는 로그인
	 * @param dto
	 * @return
	 */
	@Transactional
	public String sessionLogin(final LoginUserInfoDto dto) {
		UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(dto.getEmail());

		// TODO 존재하지 않는 사용자에 대한 예외처리 수정 필요
		if (Objects.isNull(userInfo)) {
			throw new SecurityException(ErrorCodes.USER_NOT_FOUND.name());
		}

		if (userInfo.getPassword().equals(dto.getPassword())) {
			return "";
		}

		// TODO 비밀번호 틀렸을 때 처리 필요
		return "";
	}

	public long signup(final UserInfoDto userInfoDto) {
//		boolean check = checkEmailExists(userInfoDto.getUserEmail());

//		if (check) {
//			throw new IllegalArgumentException("이미 존재하는 유저입니다.");
//		}

		UserInfo userInfo = UserInfo.of(userInfoDto);
		userInfo.setPassword(encoder.encode(userInfoDto.getPassword()));

		return userInfoRepository.save(userInfo).getId();
	}
}