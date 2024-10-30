package com.example.testcodesample.api.exception;

public class DuplicateEmailException extends BusinessException {

	public DuplicateEmailException() {
		super(ErrorCode.DUPLICATE_EMAIL);
	}
}
