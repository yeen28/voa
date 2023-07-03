package com.project.voa.service;

import com.project.voa.domain.Attachment;
import com.project.voa.domain.Issue;
import com.project.voa.dto.AttachmentModel;
import com.project.voa.error.ErrorCodes;
import com.project.voa.repository.AttachmentRepository;
import com.project.voa.repository.IssueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {
	@Value("${spring.servlet.multipart.location}") String uploadPath;
	private final AttachmentRepository attachmentRepository;
	private final IssueRepository issueRepository;

	/**
	 * 첨부 파일 업로드
	 * @param uploadFile
	 * @param issueId
	 * @return
	 */
	public AttachmentModel uploadAttachment(MultipartFile uploadFile, final String issueId) throws IOException {
		if (issueRepository.findById(Long.valueOf(issueId)).isEmpty()) {
			throw new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name());
		}

		String fileName = fileNameSuffix(Objects.requireNonNull(uploadFile.getOriginalFilename()));

		Path newFilePath = Files.createDirectories(new File(uploadPath, issueId).toPath()).resolve(fileName);
		uploadFile.transferTo(newFilePath);

		Attachment savedAttachment = attachmentRepository.save(new Attachment(fileName));

		// 등록한 첨부파일을 이슈와 연결
		connectIssue(Long.parseLong(issueId), savedAttachment);

		return AttachmentModel.of(savedAttachment);
	}

	/**
	 * 중복되는 파일명이 존재하는 경우 파일명에 접미사 추가
	 * ex) upload.txt -> upload(1).txt
	 * @param fileName
	 * @return
	 */
	private String fileNameSuffix(String fileName) {
		String name = fileName.substring(0, fileName.indexOf("."));
		String extension = StringUtils.getFilenameExtension(fileName);

		String saveFileName = fileName;
		Optional<Issue> optName = issueRepository.findAllByAttachments_Name(fileName);

		// 중복되는 파일명이 존재하지 않는 경우
		if (optName.isEmpty()) {
			return fileName;
		}

		int idx = 0;
		while(optName.isPresent()) {
			saveFileName = String.format("%s(%s).%s", name, ++idx, extension);
			optName = issueRepository.findAllByAttachments_Name(saveFileName);
		}

		return saveFileName;
	}

	/**
	 * 업로드한 첨부파일 정보를 이슈와 연결
	 * @param issueId
	 * @param attachment
	 */
	private void connectIssue(final long issueId, Attachment attachment) {
		Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name()));

		List<Attachment> attachments = issue.getAttachments();
		attachments.add(attachment);
		issue.setAttachments(attachments);
		issueRepository.save(issue);
	}
}