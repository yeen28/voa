package com.project.voa.dto;

import com.project.voa.domain.Version;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class VersionModel {
	private String id;
	private String name;

	public static VersionModel of(Version version) {
		return VersionModel.builder()
				.id(String.valueOf(version.getId()))
				.name(version.getName())
				.build();
	}

	public static List<VersionModel> versionsToVersionModels(List<Version> versions) {
		return versions.stream().map(VersionModel::of).toList();
	}

	@Override
	public String toString() {
		return String.format("id: %s, name: %s", this.id, this.name);
	}
}
