package com.project.voa.dto;

import com.project.voa.domain.IssueType;
import com.project.voa.domain.Label;
import com.project.voa.domain.UserInfo;
import com.project.voa.domain.Version;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueDTO {
	private long issueTypeId;
	private String title;
	private long rank;
	private List<Long> versionIds;
	private long ownerId;
	private long reporterId;
	private String env;
	private String description;
	private List<Long> labelIds;
	private long issueLinkType;
	private String issueLink;
}
