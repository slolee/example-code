package com.example.wizeventmalljava.api.jwtauth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.wizeventmalljava.api.jwtauth.dto.RequestMember;
import com.example.wizeventmalljava.common.helper.JwtHelper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestMemberResolver implements HandlerMethodArgumentResolver {

	private final JwtHelper jwtHelper;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(RequestMember.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String accessToken = request.getHeader("Authorization").substring("Bearer ".length());
		Long memberId = jwtHelper.extractMemberId(accessToken);
		return new RequestMember(memberId);
	}
}
