package com.project.voa.service;

import com.project.voa.domain.Attachment;
import com.project.voa.dto.AttachmentModel;
import com.project.voa.repository.AttachmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AttachmentService {
	private final AttachmentRepository attachmentRepository;

	/**
	 * 첨부 파일 업로드
	 * @param file
	 * @return
	 */
	public AttachmentModel uploadAttachment(HttpServletRequest request, MultipartFile file) throws IOException {
		String filePath = request.getSession().getServletContext().getRealPath("/attachment/");
		File fileFolder = new File(filePath);
		if (!fileFolder.exists()) {
			fileFolder.mkdir();
		}

		File savefile = new File(filePath + file.getOriginalFilename());
		file.transferTo(savefile);
		return AttachmentModel.of(attachmentRepository.save(new Attachment(filePath, file.getOriginalFilename())));
	}
}