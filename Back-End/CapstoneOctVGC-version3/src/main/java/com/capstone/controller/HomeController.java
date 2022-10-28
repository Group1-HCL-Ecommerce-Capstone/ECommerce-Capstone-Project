package com.capstone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

	@GetMapping
	public String welcome() {
		return "Welcome to Spring Security Test 1";
	}
	
	@GetMapping("/user")
	public String welcomeUser() {
		return "Hello user";
	}

	@GetMapping("/admin")
	public String welcomeAdmin() {
		return "Hello admin";
	}
}
