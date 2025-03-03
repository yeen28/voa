package com.project.voa.domain;

import com.project.voa.dto.UserInfoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String userEmail;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column
	private String profile;

	@ManyToOne
	private Team team;

	public static UserInfo of(UserInfoDto userInfoDto) {
		return UserInfo.builder()
				.userEmail(userInfoDto.getUserEmail())
				.userName(userInfoDto.getUserName())
				.password(userInfoDto.getPassword())
				.profile(userInfoDto.getProfile())
				.build();
	}
}