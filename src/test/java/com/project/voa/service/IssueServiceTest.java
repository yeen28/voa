package com.project.voa.service;

import com.project.voa.domain.*;
import com.project.voa.dto.IssueDTO;
import com.project.voa.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {
	@Mock IssueTypeRepository issueTypeRepository;
	@Mock UserInfoRepository userInfoRepository;
	@Mock VersionRepository versionRepository;
	@Mock LabelRepository labelRepository;
	@Mock IssueDTO issueDTO;
	@Mock IssueRepository issueRepository;
	@InjectMocks IssueService issueService;

	@Test
	void createIssueTest() {
		// given
		IssueType issueType = new IssueType();
		Version version = new Version();
		Label label = new Label();
		UserInfo userInfo = new UserInfo();

		//when
		when(issueTypeRepository.findById(any()))
				.thenReturn(Optional.of(issueType));
		when(userInfoRepository.findById(any()))
				.thenReturn(Optional.of(userInfo));
		when(issueDTO.getIssueTypeId()).thenReturn(1L);
		when(issueDTO.getVersionNames()).thenReturn(List.of("version"));
		when(issueDTO.getOwnerId()).thenReturn(1L);
		when(issueDTO.getReporterId()).thenReturn(1L);
		when(issueDTO.getLabelNames()).thenReturn(List.of("label"));
		when(versionRepository.findByName(anyString())).thenReturn(Optional.of(version));
		when(labelRepository.findByName(anyString())).thenReturn(Optional.of(label));

		issueService.createIssue(issueDTO);

		//then
		verify(issueRepository, atLeastOnce()).save(any(Issue.class));
	}
}