package com.example.testcodesample.api.service;

import org.springframework.stereotype.Service;

import com.example.testcodesample.api.controller.dto.MemberResponse;
import com.example.testcodesample.api.exception.ResourceNotFoundException;
import com.example.testcodesample.domain.member.Member;
import com.example.testcodesample.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberResponse retrieve(Long id) {
		return memberRepository.findById(id)
			.map(MemberResponse::from)
			.orElseThrow(() -> new ResourceNotFoundException(Member.class, id));
	}
}
