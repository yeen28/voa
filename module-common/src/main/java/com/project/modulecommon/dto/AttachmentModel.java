package com.project.modulecommon.dto;

import com.project.modulecommon.domain.Attachment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttachmentModel {
	private String id;
	private String name;
	private String createdAt;

	public static AttachmentModel of(Attachment attachment) {
		return AttachmentModel.builder()
				.id(String.valueOf(attachment.getId()))
				.name(attachment.getName())
				.createdAt(String.valueOf(attachment.getCreatedAt()))
				.build();
	}
}
