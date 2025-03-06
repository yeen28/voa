package com.project.modulecommon.repository;

import com.project.modulecommon.domain.Version;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VersionRepository extends CrudRepository<Version, Long> {
	Optional<Version> findByName(String name);
}
