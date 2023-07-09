package com.project.voa.service;

import com.project.voa.domain.Team;
import com.project.voa.error.ErrorCodes;
import com.project.voa.repository.TeamRepository;
import com.project.voa.dto.TeamModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
	private final TeamRepository teamRepository;

	public TeamModel createTeam(final String name) {
		Team team = new Team();
		team.setName(name);
		Team savedTeam = teamRepository.save(team);
		return new TeamModel(String.valueOf(savedTeam.getId()), name);
	}

	public TeamModel getTeamById(final long id) {
		Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCodes.TEAM_NOT_FOUND.name()));
		return new TeamModel(String.valueOf(id), team.getName());
	}

	public List<TeamModel> getTeams() {
		Iterable<Team> teams = teamRepository.findAll();
		List<TeamModel> teamModels = new ArrayList<>();
		teams.forEach(team -> teamModels.add(new TeamModel(String.valueOf(team.getId()), team.getName())));
		return teamModels;
	}

	public TeamModel updateTeam(final long id, final String name) {
		Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCodes.TEAM_NOT_FOUND.name()));
		team.setName(name);
		teamRepository.save(team);
		return new TeamModel(String.valueOf(id), team.getName());
	}

	public void deleteTeam(long id) {
		teamRepository.deleteById(id);
	}
}