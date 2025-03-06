package com.project.issue.service;

import com.project.modulecommon.domain.Label;
import com.project.modulecommon.dto.LabelModel;
import com.project.modulecommon.repository.LabelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {
	@InjectMocks
	LabelService labelService;
	@Mock LabelRepository labelRepository;

	@Test
	void getLabelsTest() {
		Label label = Mockito.mock(Label.class);

		doReturn(List.of(label)).when(labelRepository).findAll();
		doReturn(1L).when(label).getId();
		doReturn("labelName").when(label).getName();

		LabelModel actual = labelService.getLabels().get(0);
		assertEquals("1", actual.getId());
		assertEquals("labelName", actual.getName());
	}

	@Test
	void deleteLabelTest() {
		labelService.deleteLabel(1L);
		verify(labelRepository, times(1)).deleteById(1L);
	}
}