package com.project.voa.dto;

import com.project.voa.domain.Label;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LabelModel {
	private String id;
	private String name;

	public static LabelModel of(Label label) {
		return LabelModel.builder()
				.id(String.valueOf(label.getId()))
				.name(label.getName())
				.build();
	}

	public static List<LabelModel> labelsToLabelModelList(List<Label> labels) {
		return labels.stream()
				.map(LabelModel::of)
				.toList();
	}
}
