package com.project.issue.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtType {
	BEARER("Bearer");

	private final String value;
}
