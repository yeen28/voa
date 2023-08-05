package com.project.voa.service;

import com.project.voa.domain.Attachment;
import com.project.voa.domain.Issue;
import com.project.voa.repository.AttachmentRepository;
import com.project.voa.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttachmentServiceTest {
	@InjectMocks AttachmentService attachmentService;
	@Mock IssueRepository issueRepository;
	@Mock AttachmentRepository attachmentRepository;

	@Test
	void uploadAttachment() throws IOException {
		//given
		Issue issue = mock(Issue.class);
		Attachment attachment = mock(Attachment.class);
		MockMultipartFile uploadFile = new MockMultipartFile("file",
				"test.csv",
				"text/plain",
				new FileInputStream("src/test/resources/test.csv"));;

		doReturn(Optional.of(issue)).when(issueRepository).findById(anyLong());
		doReturn(1L).when(issue).getId();
		doReturn(LocalDateTime.of(2023, 8, 1, 1, 0))
			.when(issue).getCreatedAt();
		doReturn(attachment).when(attachmentRepository).save(any(Attachment.class));

		//when
		attachmentService.uploadAttachment(uploadFile, String.valueOf(issue.getId()));

		//then
		verify(attachmentRepository, atLeastOnce()).save(any(Attachment.class));
	}
}