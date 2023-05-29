package com.project.voa.controller;

import com.project.voa.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AttachmentController {
	private final AttachmentService attachmentService;

	@Operation(summary = "첨부파일 업로드")
	@PostMapping(value = "/issue/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadAttachment(
			@RequestPart(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) throws IOException {
		return new ResponseEntity<>(attachmentService.uploadAttachment(request, file), HttpStatus.OK);
	}
}