package com.project.voa.domain;

import com.project.voa.dto.IssueDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Issue extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Project project;

	@ManyToOne
	private IssueType issueType;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(nullable = false)
	private long rank;

	@ManyToMany
	private List<Version> versions;

	@ManyToOne
	private UserInfo userInfo;

	@Column
	private String env;

	@Column
	private String description;

	@OneToMany
	private List<Attachment> attachments;

	@ManyToMany
	private List<Label> labels;

	@Column
	private long issueLinkType;

	@Column
	private String issueLink;

	@ManyToOne
	@JoinColumn(nullable = false)
	private UserInfo owner;

	@ManyToOne
	@JoinColumn(nullable = false)
	private UserInfo reporter;

	@Column(nullable = false)
	private IssueStatus issueStatus;

	public static Issue of(
			IssueDTO issueDTO,
			IssueType issueType,
			List<Version> versions,
			UserInfo owner,
			UserInfo reporter,
			List<Label> labels
	) {
		return Issue.builder()
				.issueType(issueType)
				.title(issueDTO.getTitle())
				.rank(issueDTO.getRank())
				.versions(versions)
				.owner(owner)
				.reporter(reporter)
				.env(issueDTO.getEnv())
				.description(issueDTO.getDescription())
				.labels(labels)
				.issueLinkType(issueDTO.getIssueLinkType())
				.issueLink(issueDTO.getIssueLink())
				.issueStatus(IssueStatus.TO_DO)
				.build();
	}
}