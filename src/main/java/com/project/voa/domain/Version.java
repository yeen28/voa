package com.project.voa.domain;

import jakarta.persistence.*;

@Entity
public class Version extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 100, nullable = false)
	private String name;
}
