package com.project.voa.dto;

import com.project.voa.domain.IssueType;
import com.project.voa.domain.Label;
import com.project.voa.domain.UserInfo;
import com.project.voa.domain.Version;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueDTO {
	private IssueType issueType;
	private String title;
	private long rank;
	private Version version;
	private UserInfo owner;
	private UserInfo reporter;
	private String env;
	private String description;
	private List<Label> label;
	private long issueLinkType;
	private String issueLink;
}
