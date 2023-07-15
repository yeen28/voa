package com.project.voa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Attachment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String uuidName;

	@Column(nullable = false)
	private String relativePath;

	@CreatedDate
	private LocalDateTime createdAt;

	public Attachment(String name, String uuidName, String relativePath) {
		this.name = name;
		this.uuidName = uuidName;
		this.relativePath = relativePath;
	}
}