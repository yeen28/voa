package com.project.voa.repository;

import com.project.voa.domain.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LabelRepository extends CrudRepository<Label, Long> {
	List<Label> findByIdIn(List<Long> idList);
}
