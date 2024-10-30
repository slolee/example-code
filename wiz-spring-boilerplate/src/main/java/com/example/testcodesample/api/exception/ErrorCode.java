package com.example.testcodesample.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

	DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다!"),
	LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 혹은 패스워드가 잘못되었습니다."),
	NOT_FOUND_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "접근할 수 없습니다. 로그인을 해주세요!"),
	INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "접근할 수 없습니다. 로그인을 해주세요!"),
	RESOURCE_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.")
	;

	private final HttpStatus status;
	private final String message;

}
