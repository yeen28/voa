package com.project.voa.service;

import org.springframework.stereotype.Component;

@Component("naver")
public class SocialNaverService implements SocialLoginService {
	public void redirectSocial() {
		System.out.println("naver");
	}
}
