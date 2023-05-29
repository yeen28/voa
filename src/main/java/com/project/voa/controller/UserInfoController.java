package com.project.voa.controller;

import com.project.voa.dto.UserInfoDto;
import com.project.voa.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
	private final UserInfoService userInfoService;

	@Operation(summary = "사용자 등록")
	@Parameter(name = "userInfoDto", description = "사용자를 등록합니다.", example = "{\"userName\": \"user\",\"userEmail\": \"email@email.com\",\"password\":\"123\",\"profile\": \"\"}")
	@PostMapping("/user")
	public ResponseEntity<Object> insertUser(@RequestBody UserInfoDto userInfoDto) {
		return new ResponseEntity<>(userInfoService.insertUser(userInfoDto), HttpStatus.OK);
	}

	@Operation(summary = "사용자들 목록 조회")
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers() {
		return new ResponseEntity<>(userInfoService.getUsers(), HttpStatus.OK);
	}
}