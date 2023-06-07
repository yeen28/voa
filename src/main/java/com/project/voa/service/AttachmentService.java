package com.project.voa.service;

import com.project.voa.domain.Attachment;
import com.project.voa.dto.AttachmentModel;
import com.project.voa.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {
	@Value("${spring.servlet.multipart.location}") String filePath;
	private final AttachmentRepository attachmentRepository;

	/**
	 * 첨부 파일 업로드
	 * @param uploadFile
	 * @param issueId
	 * @return
	 */
	public AttachmentModel uploadAttachment(MultipartFile uploadFile, final String issueId) throws IOException {
		String fileName = fileNameSuffix(
				String.valueOf(Paths.get(filePath, issueId)),
				Objects.requireNonNull(uploadFile.getOriginalFilename()));

		Path newFilePath = Files.createDirectories(Paths.get(filePath, issueId)).resolve(fileName);
		uploadFile.transferTo(newFilePath);

		return AttachmentModel.of(
				attachmentRepository.save(new Attachment(
						String.valueOf(Path.of(filePath, issueId)),
						fileName)
				));
	}

	/**
	 * 중복되는 파일명이 존재하는 경우 파일명에 접미사 추가
	 * ex) upload.txt -> upload(1).txt
	 * @param uploadFilePath
	 * @param fileName
	 * @return
	 */
	private String fileNameSuffix(String uploadFilePath, String fileName) {
		String name = fileName.substring(0, fileName.indexOf("."));
		String extension = StringUtils.getFilenameExtension(fileName);

		String saveFileName = fileName;
		Optional<Attachment> optName = attachmentRepository.findByFilePathAndName(uploadFilePath, fileName);

		// 중복되는 파일명이 존재하지 않는 경우
		if (optName.isEmpty()) {
			return fileName;
		}

		int idx = 0;
		while(optName.isPresent()) {
			saveFileName = String.format("%s(%s).%s", name, ++idx, extension);
			optName = attachmentRepository.findByFilePathAndName(uploadFilePath, saveFileName);
		}

		return saveFileName;
	}
}