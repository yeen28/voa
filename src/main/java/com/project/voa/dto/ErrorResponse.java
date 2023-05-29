package com.project.voa.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(String errorCode) {
	public static ResponseEntity<ErrorResponse> toResponseEntity(String errorCode, HttpStatus httpStatus) {
		return ResponseEntity
				.status(httpStatus)
				.body(new ErrorResponse(errorCode));
	}
}