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

	private Issue issueSetup() {
		IssueType issueType = mock(IssueType.class);
		issueType.setName("issueType");
		Version version = mock(Version.class);
		Label label = mock(Label.class);
		UserInfo userInfo = mock(UserInfo.class);
		Attachment attachment = mock(Attachment.class);

		return Issue.builder()
				.id(1L)
				.title("title")
				.issueType(issueType)
				.rank(1L)
				.versions(List.of(version))
				.owner(userInfo)
				.reporter(userInfo)
				.env("environment")
				.description("description")
				.labels(List.of(label))
				.issueLinkType(1L)
				.issueLink("issueLink")
				.issueStatus(IssueStatus.TO_DO)
				.attachments(List.of(attachment))
				.build();
	}

	private IssueDTO issueDtoSetup() {
		IssueDTO issueDTO = new IssueDTO();
		issueDTO.setTitle("issue title");
		issueDTO.setTypeId(1L);
		issueDTO.setVersionNames(List.of("version"));
		issueDTO.setOwnerId(1L);
		issueDTO.setReporterId(1L);
		issueDTO.setLabelNames(List.of("label"));

		return issueDTO;
	}

	@Test
	void createIssueTest() {
		IssueType issueType = new IssueType();
		Version version = new Version();
		Label label = new Label();
		UserInfo userInfo = new UserInfo();
		IssueDTO issueDTO = issueDtoSetup();

		when(issueTypeRepository.findById(any())).thenReturn(Optional.of(issueType));
		when(userInfoRepository.findById(any())).thenReturn(Optional.of(userInfo));
		when(versionRepository.findByName(anyString())).thenReturn(Optional.of(version));
		when(labelRepository.findByName(anyString())).thenReturn(Optional.of(label));

		issueService.createIssue(userInfo, issueDTO);

		verify(issueRepository, times(1)).save(any(Issue.class));
	}

	@Test
	void getIssueTest() {
		Issue issue = issueSetup();

		doReturn(Optional.of(issue)).when(issueRepository).findById(anyLong());

		issueService.getIssue(1L);

		verify(issueRepository, times(1)).findById(1L);
	}

	@Test
	void updateIssueStatusTest() {
		Issue issue = issueSetup();
		doReturn(Optional.of(issue)).when(issueRepository).findById(anyLong());
		doReturn(issue).when(issueRepository).save(issue);

		issueService.updateIssueStatus(1L, IssueStatus.TO_DO);

		verify(issueRepository, times(1)).save(issue);
	}

	@Test
	void updateIssueTest() {
		Issue issue = issueSetup();
		IssueType issueType = issue.getIssueType();
		UserInfo userInfo = issue.getOwner();
		IssueDTO issueDTO = issueDtoSetup();

		doReturn(Optional.of(issue)).when(issueRepository).findById(anyLong());
		doReturn(Optional.of(issueType)).when(issueTypeRepository).findById(anyLong());
		doReturn(Optional.of(userInfo)).when(userInfoRepository).findById(anyLong());
		doReturn(issue).when(issueRepository).save(issue);

		issueService.updateIssue(1L, issueDTO);

		verify(issueRepository, times(1)).save(issue);
	}

	@Test
	void deleteIssueTest() {
		Issue issue = issueSetup();
		doReturn(Optional.of(issue)).when(issueRepository).findById(anyLong());

		issueService.deleteIssue(1L);

		verify(issueRepository, times(1)).delete(issue);
	}
}