package com.example.testcodesample.api.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.testcodesample.api.controller.dto.LoginRequest;
import com.example.testcodesample.api.controller.dto.LoginResponse;
import com.example.testcodesample.api.exception.BusinessException;
import com.example.testcodesample.common.JwtHelper;
import com.example.testcodesample.domain.member.Member;
import com.example.testcodesample.domain.member.MemberRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock private MemberRepository memberRepository;
	@Mock private PasswordEncoder passwordEncoder;
	@Mock private JwtHelper jwtHelper;

	@InjectMocks private AuthService authService;

	@Test
	@DisplayName("login - 정상적으로 로그인이 되는 케이스")
	void loginSuccessTest() {
		// given
		var req = new LoginRequest("slolee@naver.com", "1234");
		var savedMember = Member.builder()
			.id(1L)
			.email("slolee@naver.com")
			.password("1234")
			.build();
		/// Stubbing : Mock 객체에게 행위를 정해주는 것!
		when(memberRepository.findByEmail("slolee@naver.com")).thenReturn(Optional.of(savedMember));
		when(passwordEncoder.matches("1234", "1234")).thenReturn(true);
		when(jwtHelper.generateAccessToken(any())).thenReturn("Sample Token");

		// when
		LoginResponse resp = authService.login(req);

		// then
		assertThat(resp).isNotNull();
		assertThat(resp.accessToken()).isEqualTo("Sample Token");
		/// Spy
		verify(jwtHelper, times(1)).generateAccessToken(any());
	}

	@Test
	@DisplayName("login - 이메일이 잘못된 케이스")
	void loginInvalidEmailTest() {
		// given
		var req = new LoginRequest("slolee@naver.com", "1234");
		when(memberRepository.findByEmail("slolee@naver.com")).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> authService.login(req))
			.isInstanceOf(BusinessException.class);
	}

	@Test
	@DisplayName("login - 패스워드가 잘못된 케이스")
	void loginInvalidPasswordTest() {
		// given
		var req = new LoginRequest("slolee@naver.com", "ABCD");
		var savedMember = Member.builder()
			.id(1L)
			.email("slolee@naver.com")
			.password("1234")
			.build();
		when(memberRepository.findByEmail("slolee@naver.com")).thenReturn(Optional.of(savedMember));
		when(passwordEncoder.matches("ABCD", "1234")).thenReturn(false);

		// when & then
		assertThatThrownBy(() -> authService.login(req))
			.isInstanceOf(BusinessException.class);
	}
}