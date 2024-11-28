package com.example.wizshopjava.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
