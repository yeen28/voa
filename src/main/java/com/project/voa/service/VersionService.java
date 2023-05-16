package com.project.voa.service;

import com.project.voa.domain.Version;
import com.project.voa.repository.VersionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {
	private final VersionRepository versionRepository;

	/**
	 * 버전 추가
	 */
	public void createVersion(String name) {
		versionRepository.save(new Version(name));
	}

	/**
	 * 버전 삭제
	 * @param id
	 */
	public void deleteVersion(final long id) {
		Version version = versionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		versionRepository.delete(version);
	}
}
