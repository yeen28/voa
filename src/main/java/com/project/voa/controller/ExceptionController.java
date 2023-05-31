package com.project.voa.controller;

import com.project.voa.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerEntityNotFoundException(EntityNotFoundException e) {
		return ErrorResponse.toResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = DuplicateKeyException.class)
	public ResponseEntity<ErrorResponse> handlerDuplicateKeyException(DuplicateKeyException e) {
		return ErrorResponse.toResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
