package com.project.voa.controller;

import com.project.voa.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
	private final UserInfoService userInfoService;

	@Operation(summary = "사용자 등록", description = "사용자를 등록합니다.")
	@PostMapping("/user")
	public ResponseEntity<Object> createIssue() {
		return new ResponseEntity<>(userInfoService.insertUser(), HttpStatus.OK);
	}
}