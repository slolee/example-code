package com.example.testcodesample.api.filter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.example.testcodesample.api.exception.UnAuthorizationException;
import com.example.testcodesample.common.JwtHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

	@Mock private JwtHelper jwtHelper;
	@InjectMocks private JwtAuthenticationFilter jwtAuthenticationFilter;

	// 시나리오
	@Test
	@DisplayName("필터를 타는데, 정상적으로 처리되는 케이스")
	void applicableSuccessTest() throws ServletException, IOException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/members/me");
		request.addHeader("Authorization", "Bearer SampleAccessToken");
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = new MockFilterChain();

		// when
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// then
		verify(jwtHelper, times(1)).validate("SampleAccessToken");
	}

	@Test
	@DisplayName("필터를 안타는 케이스")
	void notApplicableSuccessTest() throws ServletException, IOException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/auth/login");
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = new MockFilterChain();

		// when
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// then
		verify(jwtHelper, times(0)).validate(any());
	}

	@Test
	@DisplayName("필터를 타는데, Authorization 헤더가 없는 케이스")
	void notFoundAuthorizationHeaderTest() throws ServletException, IOException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/members/me");
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = new MockFilterChain();

		// when & then
		assertThatThrownBy(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain))
			.isInstanceOf(UnAuthorizationException.class);
		verify(jwtHelper, times(0)).validate(any());
	}

	// 4. 필터를 타는데, AccessToken 이 잘못된 케이스
}