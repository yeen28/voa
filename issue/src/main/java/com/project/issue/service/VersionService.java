package com.project.issue.service;

import com.project.modulecommon.domain.Version;
import com.project.modulecommon.dto.VersionModel;
import com.project.issue.error.ErrorCodes;
import com.project.modulecommon.repository.VersionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionService {
	private final VersionRepository versionRepository;

	/**
	 * 버전 추가
	 * @param name
	 * @return
	 */
	@Transactional
	public Version createVersion(String name) {
		return versionRepository.save(new Version(name));
	}

	/**
	 * 버전 삭제
	 * @param id
	 */
	public void deleteVersion(final long id) {
		Version version = versionRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.VERSION_NOT_FOUND.name()));
		versionRepository.delete(version);
	}

	/**
	 * 버전들 조회
	 * @return
	 */
	public List<VersionModel> getVersions() {
		return VersionModel.versionsToVersionModels((List<Version>) versionRepository.findAll());
	}
}
