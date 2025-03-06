package com.project.issue.service;

import org.springframework.stereotype.Component;

@Component("google")
public class SocialGoogleService implements SocialLoginService {
	public void redirectSocial() {
		System.out.println("google");
	}
}
