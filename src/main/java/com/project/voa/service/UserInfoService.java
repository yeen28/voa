package com.project.voa.service;

import com.project.voa.domain.Team;
import com.project.voa.domain.UserInfo;
import com.project.voa.dto.UserInfoDto;
import com.project.voa.dto.UserInfoModel;
import com.project.voa.repository.TeamRepository;
import com.project.voa.repository.UserInfoRepository;
import com.project.voa.error.ErrorCodes;
import lombok.RequiredArgsConstructor;
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
		// team name을 DB에서 찾을 수 없으면 team 생성
		Team team = teamRepository.findByName(userInfoDto.getTeamName())
				.orElseGet(() -> createTeam(userInfoDto.getTeamName()));

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userInfoDto.getUserName());
		userInfo.setPassword(userInfoDto.getPassword());
		userInfo.setUserEmail(userInfoDto.getUserEmail());
		userInfo.setProfile(userInfoDto.getProfile());
		userInfo.setTeam(team);

		if (userInfoRepository.existsByUserEmail(userInfoDto.getUserEmail())) {
			throw new DuplicateKeyException(ErrorCodes.DUPLICATED_EMAIL.name());
		}
		return UserInfoModel.of(userInfoRepository.save(userInfo));
	}

	private Team createTeam(String name) {
		Team team = new Team();
		team.setName(name);
		return teamRepository.save(team);
	}

	/**
	 * 사용자들 목록 조회
	 * @return
	 */
	public List<UserInfoModel> getUsers() {
		return UserInfoModel.of((List<UserInfo>) userInfoRepository.findAll());
	}
}