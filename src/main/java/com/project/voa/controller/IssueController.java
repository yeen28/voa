package com.project.voa.controller;

import com.project.voa.domain.IssueStatus;
import com.project.voa.domain.UserInfo;
import com.project.voa.dto.IssueDTO;
import com.project.voa.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IssueController {
	private final IssueService issueService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "이슈 생성")
	@Parameter(name = "issueDTO", description = "생성할 이슈 정보입니다.", example = "{\"issueTypeId\": 1,\"title\": \"voa issue\",\"rank\": 1,\"versionNames\": [\"2305\"],\"ownerId\": 1,\"reporterId\": 1,\"env\": \"Windows\",\"description\": \"이슈생성합니다.\",\"labelNames\": [\"라벨\"],\"issueLinkType\": 1,\"issueLink\": \"ISSUE-01\"}")
	@PostMapping("/issue")
	public ResponseEntity<Object> createIssue(
			@RequestBody IssueDTO issueDTO,
			final UserInfo userInfo) {
		return new ResponseEntity<>(issueService.createIssue(userInfo, issueDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "사용자에게 할당된 이슈 조회")
	@GetMapping("/issues")
	public ResponseEntity<Object> getIssues(final UserInfo userInfo) {
		return new ResponseEntity<>(issueService.getIssues(userInfo.getId()), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "이슈 조회", description = "이슈를 조회합니다.")
	@ApiResponse(responseCode = "404", description = "존재하지 않는 이슈")
	@GetMapping("/issue/{id}")
	public ResponseEntity<Object> getIssue(@PathVariable("id") long id) {
		return new ResponseEntity<>(issueService.getIssue(id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "이슈 상태 업데이트")
	@PutMapping("/issue/{id}/status/{status}")
	public ResponseEntity<Object> updateIssueStatus(
			@PathVariable("id") long id,
			@PathVariable("status") IssueStatus status) {
		return new ResponseEntity<>(issueService.updateIssueStatus(id, status), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "이슈 내용 수정", description = "이슈 내용을 수정합니다.")
	@Parameter(name = "issueDTO", description = "수정할 이슈 정보입니다.", example = "{\"issueTypeId\": 2,\"title\": \"update title\",\"rank\": 3,\"versionNames\": [\"2305\"],\"ownerId\": 1,\"reporterId\": 1,\"env\": \"\",\"description\": \"이슈 수정합니다.\",\"labelNames\": [\"라벨\", \"라벨2\"],\"issueLinkType\": 1,\"issueLink\": \"ISSUE-02\", \"issueStatus\": \"DONE\"}")
	@ApiResponse(responseCode = "404", description = "존재하지 않는 이슈")
	@PutMapping("/issue/{id}")
	public ResponseEntity<Object> updateIssue(
			@PathVariable("id") long id,
			@RequestBody IssueDTO issueDTO) {
		return new ResponseEntity<>(issueService.updateIssue(id, issueDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "이슈 삭제", description = "이슈를 삭제합니다.")
	@ApiResponse(responseCode = "404", description = "존재하지 않는 이슈")
	@DeleteMapping("/issue/{id}")
	public ResponseEntity<Object> deleteIssue(@PathVariable("id") long id) {
		issueService.deleteIssue(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
