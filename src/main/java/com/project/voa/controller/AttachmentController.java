package com.project.voa.controller;

import com.project.voa.error.ErrorCodes;
import com.project.voa.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AttachmentController {
	private final AttachmentService attachmentService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "첨부파일 업로드")
	@PostMapping(value = "/issue/{issueId}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadAttachment(
			@RequestPart(value = "file", required = false) MultipartFile file,
			@PathVariable("issueId") long issueId) throws IOException {
		if (file == null) {
			return new ResponseEntity<>(ErrorCodes.FILE_IS_NULL, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(attachmentService.uploadAttachment(file, String.valueOf(issueId)), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "첨부파일 다운로드")
	@GetMapping(value = "/issue/{issueId}/attachment/{attachmentId}")
	public ResponseEntity<Object> downloadAttachment(
			@PathVariable("attachmentId") long fileId,
			@PathVariable("issueId") long issueId,
			final HttpServletResponse response) throws IOException {
		attachmentService.downloadAttachment(fileId, issueId, response);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}