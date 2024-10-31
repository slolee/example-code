package com.example.testcodesample.api.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.example.testcodesample.api.config.SecurityConfig;
import com.example.testcodesample.api.config.WebConfig;
import com.example.testcodesample.api.controller.dto.MemberResponse;
import com.example.testcodesample.api.controller.resolver.AuthMemberArgumentResolver;
import com.example.testcodesample.api.service.MemberService;
import com.example.testcodesample.common.JwtHelper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@Import(value = {AuthMemberArgumentResolver.class, WebConfig.class, SecurityConfig.class})
class MemberControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean // Mock 객체를 만들어서 Bean 으로 등록해준다!
	private MemberService memberService;

	@MockBean
	private JwtHelper jwtHelper;

	@Captor
	ArgumentCaptor<Long> idCaptor;

	@Test
	@DisplayName("토큰인증 & AuthMember 리졸버 & MemberController 통합 테스트")
	void integrationTest() throws Exception {
		// given
		doNothing().when(jwtHelper).validate("ABCD");
		when(jwtHelper.extractMemberId("ABCD")).thenReturn(123L);
		when(memberService.retrieve(123L)).thenReturn(new MemberResponse("slolee@naver.com", "wiz"));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer ABCD");

		// when & then
		mockMvc.perform(get("/api/members/me").headers(headers))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"email\": \"slolee@naver.com\", \"nickname\": \"wiz\"}"));
		/// Spy
		verify(memberService, times(1)).retrieve(idCaptor.capture());
		Long id = idCaptor.getValue();
		assertThat(id).isEqualTo(123L);
	}

	// 또 다른 통합테스트 케이스 : Service + Repository + DB -> @DataJpaTest
}