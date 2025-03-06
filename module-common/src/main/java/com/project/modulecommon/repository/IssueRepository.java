package com.project.modulecommon.repository;

import com.project.modulecommon.domain.Issue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends CrudRepository<Issue, Long> {
	List<Issue> findByOwnerId(long ownerId);
	Optional<Issue> findAllByAttachments_Name(String fileName);
}
