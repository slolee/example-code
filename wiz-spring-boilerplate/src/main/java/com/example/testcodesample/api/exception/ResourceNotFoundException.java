package com.example.testcodesample.api.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends BusinessException {

	private final Class<?> tClazz;
	private final Long resourceId;

	public ResourceNotFoundException(Class<?> tClazz, Long resourceId) {
		super(ErrorCode.RESOURCE_NOT_FOUND);
		this.tClazz = tClazz;
		this.resourceId = resourceId;
	}

	@Override
	public String getMessage() {
		return String.format("%s(%d) 리소스가 존재하지 않습니다.", tClazz.getSimpleName(), resourceId);
	}
}
