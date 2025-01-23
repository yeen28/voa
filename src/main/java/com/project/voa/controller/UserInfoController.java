package com.project.voa.controller;

import com.project.voa.dto.UserInfoDto;
import com.project.voa.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserInfoController {
	private final UserInfoService userInfoService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "사용자 등록")
	@Parameter(name = "userInfoDto", description = "사용자를 등록합니다.", example = "{\"userName\": \"user\",\"userEmail\": \"email@email.com\",\"password\":\"123\",\"profile\": \"\",\"teamId\": \"1\"}")
	@PostMapping("/user")
	public ResponseEntity<Object> insertUser(@RequestBody UserInfoDto userInfoDto) {
		return new ResponseEntity<>(userInfoService.insertUser(userInfoDto), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "사용자들 목록 조회")
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers() {
		return new ResponseEntity<>(userInfoService.getUsers(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(summary = "마이페이지")
	@GetMapping("/profile")
	public ModelAndView profile() {
		return new ModelAndView("components/profile");
	}
}