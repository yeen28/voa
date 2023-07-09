package com.project.voa.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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
}
