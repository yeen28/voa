package com.project.voa.controller;

import com.project.voa.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TeamController {
	private final TeamService teamService;

	@Operation(summary = "팀 생성")
	@PostMapping("/team")
	public ResponseEntity<Object> createTeam(String teamName) {
		return new ResponseEntity<>(teamService.createTeam(teamName), HttpStatus.OK);
	}

	@Operation(summary = "모든 팀 조회")
	@GetMapping("/teams")
	public ResponseEntity<Object> getTeams() {
		return new ResponseEntity<>(teamService.getTeams(), HttpStatus.OK);
	}

	@Operation(summary = "팀 단 건 조회")
	@GetMapping("/team/{id}")
	public ResponseEntity<Object> getTeam(@PathVariable long id) {
		return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
	}

	@Operation(summary = "팀 수정")
	@PutMapping("/team/{id}")
	public ResponseEntity<Object> updateTeam(@PathVariable long id, String name) {
		return new ResponseEntity<>(teamService.updateTeam(id, name), HttpStatus.OK);
	}

	@Operation(summary = "팀 삭제")
	@DeleteMapping("/team/{id}")
	public ResponseEntity<Object> deleteTeam(@PathVariable long id) {
		teamService.deleteTeam(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}