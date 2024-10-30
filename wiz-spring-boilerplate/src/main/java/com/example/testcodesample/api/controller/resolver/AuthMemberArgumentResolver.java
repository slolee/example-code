package com.example.testcodesample.api.controller.resolver;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.testcodesample.api.controller.dto.AuthMember;
import com.example.testcodesample.common.JwtHelper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtHelper jwtHelper;

	@Override
	public AuthMember resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		String accessToken = Objects.requireNonNull(webRequest.getHeader("Authorization"))
			.substring("Bearer ".length());
		Long memberId = jwtHelper.extractMemberId(accessToken);
		return new AuthMember(memberId);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(AuthMember.class);
	}
}
