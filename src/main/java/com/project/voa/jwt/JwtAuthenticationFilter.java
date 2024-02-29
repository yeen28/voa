package com.project.voa.jwt;

import com.project.voa.type.JwtType;
import com.project.voa.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	private final JwtTokenProvider jwtTokenProvider;
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// 1. Request Header에서 JWT token 추출
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestURI = httpServletRequest.getRequestURI();
		String token = resolveToken(httpServletRequest);

		// 2. validateToken으로 token 유효성 검사
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			LOGGER.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
		} else {
			LOGGER.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Request Header에서 token 정보 추출.
	 * Request Header에 token 정보가 없다면 Cookie에서 정보 추출.
	 */
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (!StringUtils.hasText(bearerToken)) {
			bearerToken = String.format("%s %s", JwtType.BEARER.getValue(), CookieUtils.getCookie(request, "token"));
		}

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtType.BEARER.getValue())) {
			return bearerToken.substring(7);
		}

		return "";
	}
}
