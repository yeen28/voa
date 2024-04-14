package com.project.voa.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.core.Authentication;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class JwtTokenProviderTest {
	@Autowired private JwtTokenProvider tokenProvider;
	private static Key key;

	@BeforeAll
	static void before() {
		String secretKey = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		key = Keys.hmacShaKeyFor(keyBytes);
	}

	private String initAccessToken() {
		return Jwts.builder()
				.setSubject("userName")
				.claim("auth", "ROLE_USER")
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}

	@Test
	void generateTokenTest() {
		String accessToken = initAccessToken();

		Authentication authentication = tokenProvider.getAuthentication(accessToken);
		JwtTokenInfo jwtTokenInfo = tokenProvider.generateToken(authentication);

		assertEquals("Bearer", jwtTokenInfo.getGrantType());
	}

	@Test
	void getAuthenticationTest() {
		String accessToken = initAccessToken();

		Authentication authentication = tokenProvider.getAuthentication(accessToken);

		assertTrue(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
		assertEquals("userName", authentication.getName());
	}

	@Test
	void validateTokenTest() {
		String accessToken = initAccessToken();

		assertTrue(tokenProvider.validateToken(accessToken));
	}

	@Test
	void invalidateTokenTest(CapturedOutput capturedOutput) {
		String accessToken = "invalidToken";

		assertFalse(tokenProvider.validateToken(accessToken));
		assertThat(capturedOutput.toString(), containsString("Invalid JWT Token - invalidToken"));
	}
}