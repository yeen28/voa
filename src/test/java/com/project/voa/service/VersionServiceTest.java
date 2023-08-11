package com.project.voa.service;

import com.project.voa.domain.Version;
import com.project.voa.dto.VersionModel;
import com.project.voa.repository.VersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VersionServiceTest {
	@InjectMocks VersionService versionService;
	@Mock VersionRepository versionRepository;

	@Test
	void createVersionTest() {
		versionService.createVersion("version");
		verify(versionRepository, times(1)).save(any(Version.class));
	}

	@Test
	void deleteVersionTest() {
		Version version = mock(Version.class);
		doReturn(Optional.of(version)).when(versionRepository).findById(1L);

		versionService.deleteVersion(1L);
		verify(versionRepository, times(1)).delete(version);
	}

	@Test
	void getVersionsTest() {
		Version version = mock(Version.class);

		doReturn(List.of(version)).when(versionRepository).findAll();
		doReturn(1L).when(version).getId();
		doReturn("versionName").when(version).getName();

		VersionModel actual = versionService.getVersions().get(0);
		assertEquals("1", actual.getId());
		assertEquals("versionName", actual.getName());
	}
}