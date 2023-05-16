package com.project.voa.repository;

import com.project.voa.domain.Version;
import org.springframework.data.repository.CrudRepository;

public interface VersionRepository extends CrudRepository<Version, Long> {
}
