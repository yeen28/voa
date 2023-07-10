package com.project.voa.controller;

import com.project.voa.dto.ErrorResponse;
import com.project.voa.error.ErrorCodes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

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

	@ExceptionHandler(value = IOException.class)
	public ResponseEntity<ErrorResponse> ioException(IOException e) throws IOException {
		String errorCode = e.getMessage();
		if (ErrorCodes.FILE_NOT_FOUND.name().equals(errorCode)) {
			return ErrorResponse.toResponseEntity(errorCode, HttpStatus.NOT_FOUND);
		}
		throw new IOException();
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException e) {
		return ErrorResponse.toResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
}
