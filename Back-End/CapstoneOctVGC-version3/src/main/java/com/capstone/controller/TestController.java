package com.capstone.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	@GetMapping("/all")
	public String welcome() {
		return "Welcome to Spring Security Test 1";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public String welcomeUser() {
		return "Hello user";
	}
	
	@GetMapping("/manag")
	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public String welcomeManager() {
		return "Hello manager";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String welcomeAdmin() {
		return "Hello admin";
	}
}
