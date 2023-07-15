package com.project.voa.service;

import com.project.voa.domain.Attachment;
import com.project.voa.domain.Issue;
import com.project.voa.dto.AttachmentModel;
import com.project.voa.error.ErrorCodes;
import com.project.voa.repository.AttachmentRepository;
import com.project.voa.repository.IssueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
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
	@Transactional
	public AttachmentModel uploadAttachment(MultipartFile uploadFile, final String issueId) throws IOException {
		if (issueRepository.findById(Long.valueOf(issueId)).isEmpty()) {
			throw new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name());
		}

		String uuidFileName = String.valueOf(UUID.randomUUID());
		String uploadFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		Path newFilePath = Files.createDirectories(new File(uploadPath, uploadFolder).toPath()).resolve(uuidFileName);
		uploadFile.transferTo(newFilePath);

		Attachment savedAttachment = attachmentRepository.save(new Attachment(uploadFile.getOriginalFilename(), uuidFileName, uploadFolder));

		// 등록한 첨부파일을 이슈와 연결
		connectIssue(Long.parseLong(issueId), savedAttachment);

		return AttachmentModel.of(savedAttachment);
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

	/**
	 * 파일 다운로드
	 * @param fileId
	 * @param response
	 */
	public void downloadAttachment(final long fileId, final HttpServletResponse response) {
		Attachment attachment = attachmentRepository.findById(fileId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCodes.FILE_NOT_FOUND.name()));
		File downloadFilePath = new File(uploadPath, attachment.getRelativePath());

		String realPath = new File(downloadFilePath, attachment.getUuidName()).getPath();
		try (FileInputStream fis = new FileInputStream(realPath);
		     BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
			String encodedName = URLEncoder.encode(attachment.getName(), StandardCharsets.UTF_8);
			encodedName = encodedName.replaceAll("\\+", "%20"); // 파일 이름에서 '+'된 부분을 다시 공백으로 변경

			response.setHeader("Content-Disposition", "attachment;filename=" + encodedName);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

			byte[] buffer = new byte[4096];
			int length = 0;

			while ((length = fis.read(buffer, 0, buffer.length)) > 0) {
				bos.write(buffer, 0, length); // buffer 공간을 채움
				bos.flush(); // 파일에 전송
			}
		} catch (IOException e) {
			log.warn(e.getMessage());
		}
	}
}