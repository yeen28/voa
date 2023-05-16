package com.project.voa.controller;

import com.project.voa.dto.IssueDTO;
import com.project.voa.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
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
	@PostMapping("/issue")
	public ResponseEntity<Object> createIssue(@RequestBody IssueDTO issueDTO) {
		issueService.create(issueDTO);
		return new ResponseEntity<>(HttpStatus.OK);
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
		String title = issueService.getIssue(id).getTitle();
		return new ResponseEntity<>(title, HttpStatus.OK);
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
