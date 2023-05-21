package com.project.voa.dto;

import com.project.voa.domain.Issue;
import com.project.voa.domain.Label;
import com.project.voa.domain.Version;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class IssueModel {
	private String id;
	private String title;
	private String issueType;
	private String rank;
	private List<String> versionNames;
	private String ownerName;
	private String reporterName;
	private String env;
	private String description;
	private List<String> labelNames;
	private String issueLinkType;
	private String issueLink;
	private String createdAt;

	public static IssueModel of(Issue issue) {
		return IssueModel.builder()
				.id(String.valueOf(issue.getId()))
				.title(issue.getTitle())
				.issueType(issue.getIssueType().getName())
				.rank(String.valueOf(issue.getRank()))
				.versionNames(issue.getVersion().stream().map(Version::getName).toList())
				.ownerName(issue.getOwner().getUserName())
				.reporterName(issue.getReporter().getUserName())
				.env(issue.getEnv())
				.description(issue.getDescription())
				.labelNames(issue.getLabel().stream().map(Label::getName).toList())
				.issueLinkType(String.valueOf(issue.getIssueLinkType()))
				.issueLink(issue.getIssueLink())
				.createdAt(issue.getCreatedAt().toString())
				.build();
	}

	public static List<IssueModel> of(List<Issue> issues) {
		return issues.stream().map(IssueModel::of).toList();
	}
}
