package com.project.voa.handler;

import com.project.voa.jwt.JwtSecurityConfig;
import com.project.voa.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import java.io.IOException;

/**
 * Spring Security 환경 설정을 구성하기 위한 클래스입니다.
 * 웹 서비스가 로드 될때 Spring Container 의해 관리가 되는 클래스이며 사용자에 대한 ‘인증’과 ‘인가’에 대한 구성을 Bean 메서드로 주입을 한다.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * HTTP에 대해서 ‘인증’과 ‘인가’를 담당하는 메서드이며 필터를 통해 인증 방식과 인증 절차에 대해서 등록하며 설정을 담당하는 메서드이다.
	 * @param httpSecurity HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// token을 사용하는 방식이기 때문에 csrf를 disable합니다.
				.csrf(AbstractHttpConfigurer::disable)

				// 인증 실패와 권한 실패 예외 핸들링
				.exceptionHandling(exception -> {
					exception.authenticationEntryPoint(new Http403ForbiddenEntryPoint() {
						@Override
						public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
							response.sendRedirect("/login");
						}
					});
					exception.accessDeniedHandler((request, response, accessDeniedException) -> log.warn("권한이 없습니다"));
				})

				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers("/**").permitAll()
						.anyRequest().authenticated()
				)

				// 세션을 사용하지 않기 때문에 STATELESS로 설정
				.sessionManagement(sessionManagement ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)

				.logout(httpSecurityLogoutConfigurer -> {
					httpSecurityLogoutConfigurer.logoutUrl("/logout");
					// 쿠키 제거
					httpSecurityLogoutConfigurer.deleteCookies("token");
				})

				.with(new JwtSecurityConfig(jwtTokenProvider), customizer -> {});
		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}