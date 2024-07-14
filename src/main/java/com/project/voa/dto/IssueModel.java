package com.project.voa.dto;

import com.project.voa.domain.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class IssueModel {
	private String id;
	private String title;
	private long typeId;
	private String issueType;
	private String rank;
	private List<String> versionNames;
	private long ownerId;
	private String ownerName;
	private String reporterName;
	private String env;
	private String description;
	private List<String> labelNames;
	private String issueLinkType;
	private String issueLink;
	private IssueStatus issueStatus;
	private List<String> attachmentNames;
	private String createdAt;

	public static IssueModel of(Issue issue) {
		return IssueModel.builder()
				.id(String.valueOf(issue.getId()))
				.title(issue.getTitle())
				.typeId(issue.getIssueType().getId())
				.issueType(issue.getIssueType().getName())
				.rank(String.valueOf(issue.getRank()))
				.versionNames(Objects.isNull(issue.getVersions()) ?
						null :
						issue.getVersions().stream().map(Version::getName).toList())
				.ownerId(issue.getOwner().getId())
				.ownerName(issue.getOwner().getUserName())
				.reporterName(issue.getReporter().getUserName())
				.env(issue.getEnv())
				.description(issue.getDescription())
				.labelNames(Objects.isNull(issue.getLabels()) ?
						null :
						issue.getLabels().stream().map(Label::getName).toList())
				.issueLinkType(String.valueOf(issue.getIssueLinkType()))
				.issueLink(issue.getIssueLink())
				.issueStatus(issue.getIssueStatus())
				.attachmentNames(Objects.isNull(issue.getAttachments()) ?
						null :
						issue.getAttachments().stream().map(Attachment::getName).toList())
				.createdAt(String.valueOf(issue.getCreatedAt()))
				.build();
	}

	public static List<IssueModel> of(List<Issue> issues) {
		return issues.stream().map(IssueModel::of).toList();
	}
}
