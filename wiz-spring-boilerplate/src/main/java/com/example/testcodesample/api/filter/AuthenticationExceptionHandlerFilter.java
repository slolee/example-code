package com.example.testcodesample.api.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.testcodesample.api.exception.ErrorResponse;
import com.example.testcodesample.api.exception.UnAuthorizationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Order(0)
@Component
@RequiredArgsConstructor
public class AuthenticationExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(
		HttpServletRequest req,
		HttpServletResponse resp,
		FilterChain chain
	) throws ServletException, IOException {
		try {
			chain.doFilter(req, resp);
		} catch (UnAuthorizationException authException) {
			ErrorResponse errorResponse = ErrorResponse.from(authException.getErrorCode());
			String body = objectMapper.writeValueAsString(errorResponse);

			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
			resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
			resp.getWriter().println(body);
		}
	}
}