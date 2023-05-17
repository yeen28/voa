package com.project.voa.controller;

import com.project.voa.service.IssueTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IssueTypeController {
	private final IssueTypeService issueTypeService;

	@Operation(summary = "이슈유형 생성", description = "이슈유형을 생성합니다.")
	@PostMapping("/issueType")
	public ResponseEntity<Object> createIssue() {
		issueTypeService.create();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
