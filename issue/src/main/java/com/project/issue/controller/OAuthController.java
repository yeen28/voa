package com.project.issue.controller;

import com.project.modulecommon.dto.LoginUserInfoDto;
import com.project.issue.jwt.JwtTokenInfo;
import com.project.issue.service.SocialLoginService;
import com.project.issue.service.UserInfoService;
import com.project.issue.type.JwtType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {
	private final UserInfoService userInfoService;
	private final Map<String, SocialLoginService> socialLoginServiceMap;

	@Operation(summary = "로그인 화면")
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("components/login");
	}

	@Operation(summary = "로그인 성공/실패")
	@Parameter(name = "loginUserInfo", example = "{\"email\": voa@voa.com\",\"password\":\"123\"}")
	@PostMapping("/login/user")
	public ResponseEntity<Object> login(HttpServletResponse response, @Valid @RequestBody LoginUserInfoDto dto) {
		try {
			JwtTokenInfo jwtTokenInfo = userInfoService.login(response, dto);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", JwtType.BEARER.getValue() + " " + jwtTokenInfo.getAccessToken());

			return new ResponseEntity<>(jwtTokenInfo, httpHeaders, HttpStatus.OK);
		} catch (BadCredentialsException | UsernameNotFoundException e) {
			log.warn("BadCredentialsException - {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@Operation(summary = "소셜 로그인")
	@Parameter(name = "type", example = "google")
	@GetMapping("/oauth/{type}")
	public RedirectView socialLogin(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("type") final String socialType
	) {
		final SocialLoginService socialLoginService = socialLoginServiceMap.get(socialType);
		String redirectUrl = socialLoginService.getRedirectUrl(request);
		return new RedirectView(redirectUrl);
	}

	@Operation(summary = "소셜 로그인")
	@Parameter(name = "type", example = "google")
	@GetMapping("/oauth/{type}/callback")
	public ResponseEntity<Object> socialLoginCallback(@PathVariable("type") final String socialType) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}