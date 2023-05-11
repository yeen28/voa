package com.project.voa.domain;

import jakarta.persistence.*;

@Entity
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String user_email;

	@Column(nullable = false)
	private String user_name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String profile;
}
