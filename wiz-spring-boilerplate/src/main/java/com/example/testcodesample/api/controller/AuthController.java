package com.example.testcodesample.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testcodesample.api.controller.dto.LoginRequest;
import com.example.testcodesample.api.controller.dto.LoginResponse;
import com.example.testcodesample.api.controller.dto.RegisterRequest;
import com.example.testcodesample.api.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequest req) {
		authService.register(req);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
		LoginResponse resp = authService.login(req);
		return ResponseEntity.ok(resp);
	}
}
