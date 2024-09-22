package com.project.voa.dto;

import com.project.voa.domain.IssueStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueDTO {
	private long typeId;
	private String title;
	private long rank;
	private List<String> versionNames;
	private long ownerId;
	private long reporterId;
	private String env;
	private String description;
	private List<String> labelNames;
	private long issueLinkType;
	private String issueLink;
	private IssueStatus issueStatus;
}
