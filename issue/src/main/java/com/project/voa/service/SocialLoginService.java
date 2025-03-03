package com.project.voa.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public interface SocialLoginService {
	/**
	 * 소셜 로그인 페이지 URL 조회
	 * @param request
	 * @return
	 */
	default String getRedirectUrl(HttpServletRequest request) {
		String serverName = request.getHeader("X-Forwarded-Host");
		URI uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host(serverName)
				.port("8080")
				.build()
				.toUri();
		
		return "" +
				"?response_type=code" +
				"&client_id=" + "" +
				"&redirect_uri=" + uri +
				"&state=" +
				"&scope=profile email";
	}
}
