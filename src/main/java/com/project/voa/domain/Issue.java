package com.project.voa.domain;

import com.project.voa.dto.IssueDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	private String env;

	@Column
	private String description;

	@OneToMany
	private List<Attachment> attachment;

	@ManyToMany
	private List<Label> label;

	@Getter
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

	public static Issue of(IssueDTO issueDTO) {
		return Issue.builder()
				.title(issueDTO.getTitle())
				.rank(issueDTO.getRank())
				.owner(issueDTO.getOwner())
				.reporter(issueDTO.getReporter())
				.build();
	}
}
