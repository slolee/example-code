package com.example.testcodesample.api.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.testcodesample.api.exception.ErrorCode;
import com.example.testcodesample.api.exception.UnAuthorizationException;
import com.example.testcodesample.common.JwtHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtHelper jwtHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
		if (!this.isApplicable(req)) {
			chain.doFilter(req, resp);
			return;
		}
		String accessToken = Optional.ofNullable(req.getHeader("Authorization"))
			.map(header -> header.substring("Bearer ".length()))
			.orElseThrow(() -> new UnAuthorizationException(ErrorCode.NOT_FOUND_ACCESS_TOKEN));
		jwtHelper.validate(accessToken);
		chain.doFilter(req, resp);
	}

	public boolean isApplicable(HttpServletRequest req) {
		return req.getRequestURI().startsWith("/api");
	}
}
