package com.project.voa.repository;

import com.project.voa.domain.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<Label, Long> {
	Label findByName(String name);
}
