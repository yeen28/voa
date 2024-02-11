package com.project.voa.service;

import com.project.voa.domain.UserInfo;
import com.project.voa.dto.LoginUserInfoDto;
import com.project.voa.dto.UserInfoDto;
import com.project.voa.dto.UserInfoModel;
import com.project.voa.repository.UserInfoRepository;
import com.project.voa.error.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserInfoService {
	private final UserInfoRepository userInfoRepository;

	/**
	 * 사용자 등록
	 * @return
	 */
	public UserInfoModel insertUser(UserInfoDto userInfoDto) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userInfoDto.getUserName());
		userInfo.setPassword(userInfoDto.getPassword());
		userInfo.setUserEmail(userInfoDto.getUserEmail());
		userInfo.setProfile(userInfoDto.getProfile());

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
	 * @return
	 */
	public UserInfoModel login(final LoginUserInfoDto dto) {
		UserInfo userInfo = userInfoRepository.findUserInfoByUserEmail(dto.getEmail());

		// TODO 존재하지 않는 사용자에 대한 예외처리 수정 필요
		if (Objects.isNull(userInfo)) {
			throw new SecurityException(ErrorCodes.USER_NOT_FOUND.name());
		}

		if (userInfo.getPassword().equals(dto.getPassword())) {
			return UserInfoModel.of(userInfo);
		}

		// TODO 비밀번호 틀렸을 때 처리 필요
		return UserInfoModel.of(new UserInfo());
	}
}