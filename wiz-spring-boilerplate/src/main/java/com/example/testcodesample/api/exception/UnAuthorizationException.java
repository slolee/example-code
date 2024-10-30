package com.example.testcodesample.api.exception;

import lombok.Getter;

@Getter
public class UnAuthorizationException extends RuntimeException {

	private final ErrorCode errorCode;

	public UnAuthorizationException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public UnAuthorizationException(ErrorCode errorCode, Exception cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}
}
