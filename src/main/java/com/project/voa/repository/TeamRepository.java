package com.project.voa.repository;

import com.project.voa.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {
	Optional<Team> findByName(String name);
}