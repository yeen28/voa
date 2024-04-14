package com.project.voa.jwt;

import com.project.voa.error.ErrorCodes;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 관련 메서드를 제공하는 클래스
 */
@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {
	@Value("${jwt.secret}")
	private String secretKey;
	private static final long ACCESS_TOKEN_EXP_TIME = Duration.ofDays(30).toMillis();
	private static final String AUTHORITIES_KEY = "auth";
	private static final String GRANT_TYPE_BEARER = "Bearer";
	private Key key;

	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Access Token 생성
	 */
	public JwtTokenInfo generateToken(Authentication authentication) {
		// 권한 가져오기
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + ACCESS_TOKEN_EXP_TIME);

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
				.setExpiration(validity)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();

		// Access Token 생성
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();

		return JwtTokenInfo.builder()
				.grantType(GRANT_TYPE_BEARER) // 단순하고 직관적이며 널리 사용되는 "Bearer" 인증 방식 사용
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	/**
	 * JWT token을 복호화하여 token에 들어있는 정보를 꺼냄.
	 * @param accessToken
	 * @return
	 */
	public Authentication getAuthentication(String accessToken) {
		// Jwt token 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			throw new RuntimeException(ErrorCodes.NOT_AUTHORIZATION_TOKEN.name());
		}

		// claims에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		// UserDetails 객체를 만들어서 Authentication return
		// UserDetails: interface, User: UserDetails를 구현한 클래스
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	/**
	 * token 정보 검증
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token - {}", token, e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token - {}", token, e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token - {}", token, e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty - {}", token, e);
		}

		return false;
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}