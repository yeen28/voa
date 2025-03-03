package com.project.voa.dto;

import com.project.voa.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoModel {
	private String id;
	private String userName;
	private String userEmail;
	private String profile;
	private String teamName;

	public static UserInfoModel of(UserInfo userInfo) {
		return UserInfoModel.builder()
				.id(String.valueOf(userInfo.getId()))
				.userName(userInfo.getUserName())
				.userEmail(userInfo.getUserEmail())
				.profile(userInfo.getProfile())
				.teamName(userInfo.getTeam().getName())
				.build();
	}

	public static List<UserInfoModel> of(List<UserInfo> userInfoList) {
		return userInfoList.stream().map(UserInfoModel::of).toList();
	}
}
