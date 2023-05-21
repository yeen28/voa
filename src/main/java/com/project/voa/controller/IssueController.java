package com.project.voa.controller;

import com.project.voa.domain.IssueStatus;
import com.project.voa.dto.IssueDTO;
import com.project.voa.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IssueController {
	private final IssueService issueService;

	@Operation(summary = "이슈 생성")
	@Parameter(name = "issueDTO", description = "생성할 이슈 정보입니다.", example = "{\"issueTypeId\": 1,\"title\": \"voa issue\",\"rank\": 1,\"versionNames\": [\"2305\"],\"ownerId\": 1,\"reporterId\": 1,\"env\": \"Windows\",\"description\": \"이슈생성합니다.\",\"labelNames\": [\"라벨\"],\"issueLinkType\": 1,\"issueLink\": \"ISSUE-01\"}")
	@PostMapping("/issue")
	public ResponseEntity<Object> createIssue(@RequestBody IssueDTO issueDTO) {
		return new ResponseEntity<>(issueService.createIssue(issueDTO), HttpStatus.OK);
	}

	@Operation(summary = "사용자에게 할당된 이슈 조회")
	@GetMapping("/issues")
	public ResponseEntity<Object> getIssues(long ownerId) {
		return new ResponseEntity<>(issueService.getIssues(ownerId), HttpStatus.OK);
	}

	@Operation(summary = "이슈 조회", description = "이슈를 조회합니다.")
	@ApiResponse(responseCode = "404", description = "이슈 id가 존재하지 않는 경우")
	@GetMapping("/issue/{id}")
	public ResponseEntity<Object> getIssue(@PathVariable("id") long id) {
		return new ResponseEntity<>(issueService.getIssue(id), HttpStatus.OK);
	}

	@Operation(summary = "이슈 상태 업데이트")
	@PutMapping("/issue/{id}/status")
	public ResponseEntity<Object> updateIssueStatus(
			@PathVariable("id") long id,
			@RequestParam IssueStatus issueStatus) { // TODO 받는 타입이 IssueStatus인지 확인 필요
		try {
			issueService.updateIssueStatus(id, issueStatus);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "이슈 상태 업데이트")
	@PutMapping("/issue/status/{id}")
	public ResponseEntity<Object> updateIssueStatus(
			@PathVariable("id") long id,
			@RequestParam IssueStatus issueStatus) { // TODO 받는 타입이 IssueStatus인지 확인 필요
		try {
			issueService.updateIssueStatus(id, issueStatus);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "이슈 내용 수정", description = "이슈 내용을 수정합니다.")
	@ApiResponse(responseCode = "404", description = "이슈 id가 존재하지 않는 경우")
	@PutMapping("/issue/{id}")
	public ResponseEntity<Object> updateIssue(@PathVariable("id") long id) {
		try {
			issueService.update(id);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "이슈 삭제", description = "이슈를 삭제합니다.")
	@DeleteMapping("/issue/{id}")
	public ResponseEntity<Object> deleteIssue(@PathVariable("id") long id) {
		issueService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
