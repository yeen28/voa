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
	@Mock IssueRepository issueRepository;
	@InjectMocks IssueService issueService;

	@Test
	void createIssueTest() {
		// given
		IssueType issueType = new IssueType();
		Version version = new Version();
		Label label = new Label();
		UserInfo userInfo = new UserInfo();

		IssueDTO issueDTO = new IssueDTO();
		issueDTO.setIssueTypeId(1L);
		issueDTO.setVersionNames(List.of("version"));
		issueDTO.setOwnerId(1L);
		issueDTO.setReporterId(1L);
		issueDTO.setLabelNames(List.of("label"));

		when(issueTypeRepository.findById(any())).thenReturn(Optional.of(issueType));
		when(userInfoRepository.findById(any())).thenReturn(Optional.of(userInfo));
		when(versionRepository.findByName(anyString())).thenReturn(Optional.of(version));
		when(labelRepository.findByName(anyString())).thenReturn(Optional.of(label));

		//when
		issueService.createIssue(issueDTO);

		//then
		verify(issueRepository, atLeastOnce()).save(any(Issue.class));
	}
}