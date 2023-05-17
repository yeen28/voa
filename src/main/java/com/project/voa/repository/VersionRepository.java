package com.project.voa.repository;

import com.project.voa.domain.Version;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VersionRepository extends CrudRepository<Version, Long> {
	List<Version> findByIdIn(List<Long> idList);
}
