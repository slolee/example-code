package com.example.wizshopjava.domain.member.entity;

import com.example.wizshopjava.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String email;
	private String password;
	private String nickname;

	@Builder
	public Member(Long id, String email, String password, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}
