package com.project.voa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "version", indexes = {@Index(name = "version_id_index", columnList = "id")})
public class Version extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	public Version(String name) {
		this.name = name;
	}
}
