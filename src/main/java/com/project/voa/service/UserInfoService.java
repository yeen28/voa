package com.project.voa.service;

import com.project.voa.domain.UserInfo;
import com.project.voa.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
	private final UserInfoRepository userInfoRepository;

	public void insertUser() {
		// TODO 파라미터 하드코딩
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("보아");
		userInfo.setPassword("123");
		userInfo.setUserEmail("voa@email.com");
		userInfo.setProfile("profile");
		userInfoRepository.save(userInfo);
	}
}