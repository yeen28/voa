package com.project.issue.service;

import com.project.modulecommon.domain.Team;
import com.project.modulecommon.domain.UserInfo;
import com.project.modulecommon.dto.TeamModel;
import com.project.issue.error.ErrorCodes;
import com.project.modulecommon.repository.TeamRepository;
import com.project.modulecommon.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TeamService {
	private final TeamRepository teamRepository;
	private final UserInfoRepository userInfoRepository;

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

	/**
	 * 삭제할 team에 user가 있는 경우, 에러코드 내려줌.
	 * @param id
	 */
	public void deleteTeam(final long id) {
		List<UserInfo> users = StreamSupport.stream(userInfoRepository.findAll().spliterator(), false).toList()
				.stream()
				.filter(user -> user.getTeam().getId() == id)
				.toList();

		if (!users.isEmpty()) {
			throw new DataIntegrityViolationException(ErrorCodes.DO_NOT_DELETE.name());
		}

		teamRepository.deleteById(id);
	}
}