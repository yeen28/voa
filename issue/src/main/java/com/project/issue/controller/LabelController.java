package com.project.issue.controller;

import com.project.issue.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LabelController {
	private final LabelService labelService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "label들 조회")
	@GetMapping("/labels")
	public ResponseEntity<Object> getLabels() {
		return new ResponseEntity<>(labelService.getLabels(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "label id로 삭제")
	@DeleteMapping("/label/{id}")
	public ResponseEntity<Object> deleteLabel(@PathVariable("id") long id) {
		labelService.deleteLabel(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
