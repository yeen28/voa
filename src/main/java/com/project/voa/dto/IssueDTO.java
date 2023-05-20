package com.project.voa.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class IssueDTO {
	private long issueTypeId;
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
}
