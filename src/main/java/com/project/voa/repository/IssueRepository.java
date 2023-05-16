package com.project.voa.repository;

import com.project.voa.domain.Issue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long> {
	List<Issue> findByOwnerId(long ownerId);
}
