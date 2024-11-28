package com.example.wizshopjava.common.helper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wizshopjava.domain.member.entity.Member;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtHelper {

	@Value("${jwt.access-token-secret-key}")
	private String accessTokenSecretKey;

	@Value("${jwt.access-token-expires-in}")
	private long accessTokenExpiresIn;

	private Key key;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(Member member) {
		return Jwts.builder()
			.setSubject(member.getId().toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiresIn))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public void validate(String accessToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getSubject();
		} catch (JwtException e) {
			throw new RuntimeException("잘못된 AccessToken 입니다.", e);
		}
	}

	public Long extractMemberId(String accessToken) {
		try {
			String subject = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getSubject();
			return Long.valueOf(subject);
		} catch (Exception e) {
			throw new RuntimeException("잘못된 AccessToken 입니다.", e);
		}
	}
}