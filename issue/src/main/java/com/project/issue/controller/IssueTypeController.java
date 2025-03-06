package com.project.issue.controller;

import com.project.issue.service.IssueTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IssueTypeController {
	private final IssueTypeService issueTypeService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "이슈유형들 조회", description = "이슈유형들을 조회합니다.")
	@GetMapping("/issueTypes")
	public ResponseEntity<Object> getIssueTypes() {
		return new ResponseEntity<>(issueTypeService.getIssueTypes(), HttpStatus.OK);
	}
}
