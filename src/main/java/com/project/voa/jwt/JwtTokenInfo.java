package com.project.voa.jwt;

import lombok.Builder;
import lombok.Getter;

/**
 * JWT를 이용해서 발급받은 토큰의 정보
 */
@Builder
@Getter
public class JwtTokenInfo {
	private String grantType;
	private String accessToken;
	private String refreshToken;
}
