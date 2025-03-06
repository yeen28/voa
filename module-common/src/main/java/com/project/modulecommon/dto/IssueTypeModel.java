package com.project.modulecommon.dto;

import com.project.modulecommon.domain.IssueType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class IssueTypeModel {
	private String id;
	private String name;

	public static IssueTypeModel of(IssueType issueType) {
		return IssueTypeModel.builder()
				.id(String.valueOf(issueType.getId()))
				.name(issueType.getName())
				.build();
	}

	public static List<IssueTypeModel> issueTypesToIssueTypeModels(List<IssueType> issueTypes) {
		return issueTypes.stream()
				.map(IssueTypeModel::of)
				.toList();
	}
}
