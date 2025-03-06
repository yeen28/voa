package com.project.modulecommon.repository;

import com.project.modulecommon.domain.IssueType;
import org.springframework.data.repository.CrudRepository;

public interface IssueTypeRepository extends CrudRepository<IssueType, Long> {
}
