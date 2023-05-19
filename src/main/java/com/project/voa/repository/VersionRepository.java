package com.project.voa.repository;

import com.project.voa.domain.Version;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VersionRepository extends CrudRepository<Version, Long> {
	Optional<Version> findByName(String name);
}
