package com.project.voa.controller;

import com.project.voa.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class IssueController {
	private final IssueService issueService;

	@PostMapping("/issue")
	public ResponseEntity<Object> createIssue() {
		issueService.create();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/issue/{id}")
	public ResponseEntity<Object> getIssue(@PathVariable("id") long id) {
		String title = issueService.getIssue(id).getTitle();
		return new ResponseEntity<>(title, HttpStatus.OK);
	}

	@PutMapping("/issue/{id}")
	public ResponseEntity<Object> updateIssue(@PathVariable("id") long id) {
		issueService.update(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/issue/{id}")
	public ResponseEntity<Object> deleteIssue(@PathVariable("id") long id) {
		issueService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
