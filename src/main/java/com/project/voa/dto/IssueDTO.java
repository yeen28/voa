package com.project.voa.dto;

import com.project.voa.domain.IssueStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueDTO {
	private long issueTypeId;
	@NotNull(message = "TITLE_IS_NULL")
	private String title;
	@NotNull(message = "RANK_IS_NULL")
	private long rank;
	private List<String> versionNames;
	@NotNull(message = "OWNER_IS_NULL")
	private long ownerId;
	@NotNull(message = "REPORTER_IS_NULL")
	private long reporterId;
	private String env;
	private String description;
	private List<String> labelNames;
	private long issueLinkType;
	private String issueLink;
	private IssueStatus issueStatus;
}
