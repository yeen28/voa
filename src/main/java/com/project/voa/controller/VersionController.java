package com.project.voa.controller;

import com.project.voa.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VersionController {
	private final VersionService versionService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "버전 추가")
	@PostMapping("/version")
	public ResponseEntity<Object> createVersion(@RequestParam String name) {
		return new ResponseEntity<>(versionService.createVersion(name), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "버전 삭제")
	@ApiResponse(responseCode = "404", description = "버전 id가 존재하지 않는 경우")
	@DeleteMapping("/version/{id}")
	public ResponseEntity<Object> deleteVersion(@PathVariable("id") long id) {
		versionService.deleteVersion(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "버전들 조회")
	@GetMapping("/versions")
	public ResponseEntity<Object> getVersions() {
		return new ResponseEntity<>(versionService.getVersions(), HttpStatus.OK);
	}
}
