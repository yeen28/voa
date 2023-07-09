package com.project.voa.controller;

import com.project.voa.dto.LoginUserInfoDto;
import com.project.voa.dto.UserInfoDto;
import com.project.voa.jwt.JwtTokenInfo;
import com.project.voa.service.UserInfoService;
import com.project.voa.type.JwtType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
	private final UserInfoService userInfoService;

	@Operation(summary = "사용자 등록")
	@Parameter(name = "userInfoDto", description = "사용자를 등록합니다.", example = "{\"userName\": \"user\",\"userEmail\": \"email@email.com\",\"password\":\"123\",\"profile\": \"\",\"teamName\": \"WE\"}")
	@PostMapping("/user")
	public ResponseEntity<Object> insertUser(@RequestBody UserInfoDto userInfoDto) {
		return new ResponseEntity<>(userInfoService.insertUser(userInfoDto), HttpStatus.OK);
	}

	@Operation(summary = "사용자들 목록 조회")
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers() {
		return new ResponseEntity<>(userInfoService.getUsers(), HttpStatus.OK);
	}

	@Operation(summary = "로그인 화면")
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("components/login");
	}

	@Operation(summary = "로그인 성공/실패")
	@Parameter(name = "loginUserInfo", example = "{\"email\": voa@voa.com\",\"password\":\"123\"}")
	@PostMapping("/login/user")
	public ResponseEntity<Object> login(HttpServletResponse response, @Valid @RequestBody LoginUserInfoDto dto) {
		JwtTokenInfo jwtTokenInfo = userInfoService.login(response, dto);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", JwtType.BEARER.getValue() + " " + jwtTokenInfo.getAccessToken());

		return new ResponseEntity<>(jwtTokenInfo, httpHeaders, HttpStatus.OK);
	}
}