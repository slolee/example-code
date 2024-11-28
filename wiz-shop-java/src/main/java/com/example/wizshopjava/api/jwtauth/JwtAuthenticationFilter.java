package com.example.wizshopjava.api.jwtauth;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.wizshopjava.common.helper.JwtHelper;

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
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		if (!isApplicable(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Optional<String> accessTokenOptional = Optional.ofNullable(request.getHeader("Authorization"))
				.map(header -> header.substring("Bearer ".length()));
			if (accessTokenOptional.isEmpty()) {
				handleFailedAAuthentication(response, "AccessToken 을 찾을 수 없습니다.");
				return;
			}
			jwtHelper.validate(accessTokenOptional.get());
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			handleFailedAAuthentication(response, ex.getMessage());
		}
	}

	private void handleFailedAAuthentication(HttpServletResponse response, String message) {
		try {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().println(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isApplicable(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/api");
	}
}
