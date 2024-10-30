package com.example.testcodesample.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
		log.error("서버 내부오류 발생! 삐옹삐옹!", ex);
		ErrorResponse errorResponse = ErrorResponse.from(ex.getErrorCode());
		return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
	}
}
