package com.project.voa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
public class Issue extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Project project;

	@OneToMany
	private List<IssueType> issueType;

	@Getter
	@Column(length = 100, nullable = false)
	private String title;

	@Column(nullable = false)
	private long rank;

	@ManyToMany
	private List<Version> version;

	@ManyToOne
	private UserInfo userInfo;

	@Column
	private String env = null;

	@Column
	private String description = null;

	@OneToMany
	private List<Attachment> attachment = null;

	@ManyToMany
	private List<Label> label = null;

	@Getter
	@Column
	private long issueLinkType;

	@Column
	private String issueLink = null;

	@ManyToOne
	@JoinColumn(nullable = false)
	private UserInfo owner;

	@ManyToOne
	@JoinColumn(nullable = false)
	private UserInfo reporter;
}
