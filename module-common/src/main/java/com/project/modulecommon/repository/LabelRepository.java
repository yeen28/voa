package com.project.modulecommon.repository;

import com.project.modulecommon.domain.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LabelRepository extends CrudRepository<Label, Long> {
	Optional<Label> findByName(String name);
}
