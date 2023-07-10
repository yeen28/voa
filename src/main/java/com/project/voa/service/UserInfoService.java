package com.project.voa.service;

import com.project.voa.domain.UserInfo;
import com.project.voa.dto.UserInfoDto;
import com.project.voa.dto.UserInfoModel;
import com.project.voa.repository.TeamRepository;
import com.project.voa.repository.UserInfoRepository;
import com.project.voa.error.ErrorCodes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService {
	private final UserInfoRepository userInfoRepository;
	private final TeamRepository teamRepository;

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
}