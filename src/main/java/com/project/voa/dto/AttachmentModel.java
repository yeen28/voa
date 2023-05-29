package com.project.voa.dto;

import com.project.voa.domain.Attachment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttachmentModel {
	private String name;
	private String path;
	private String createdAt;

	public static AttachmentModel of(Attachment attachment) {
		return AttachmentModel.builder()
				.name(attachment.getName())
				.path(attachment.getFilePath())
				.createdAt(String.valueOf(attachment.getCreatedAt()))
				.build();
	}
}
