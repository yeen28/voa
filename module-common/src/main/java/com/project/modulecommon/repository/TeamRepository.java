package com.project.modulecommon.repository;

import com.project.modulecommon.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {
	Optional<Team> findByName(String name);
}