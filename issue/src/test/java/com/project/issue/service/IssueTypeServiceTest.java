package com.project.issue.service;

import com.project.modulecommon.domain.IssueType;
import com.project.modulecommon.dto.IssueTypeModel;
import com.project.modulecommon.repository.IssueTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IssueTypeServiceTest {
	@InjectMocks
	IssueTypeService issueTypeService;
	@Mock IssueTypeRepository issueTypeRepository;

	@Test
	void getIssueTypesTest() {
		IssueType issueType = Mockito.mock(IssueType.class);
		doReturn(List.of(issueType)).when(issueTypeRepository).findAll();
		doReturn(1L).when(issueType).getId();
		doReturn("name").when(issueType).getName();

		IssueTypeModel actual = issueTypeService.getIssueTypes().get(0);
		assertEquals("1", actual.getId());
		assertEquals("name", actual.getName());
	}
}