package com.project.voa.controller;

import com.project.voa.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LabelController {
	private final LabelService labelService;

	@Operation(summary = "labels 조회")
	@GetMapping("/labels")
	public ResponseEntity<Object> getLabels() {
		return new ResponseEntity<>(labelService.getLabels(), HttpStatus.OK);
	}
}
