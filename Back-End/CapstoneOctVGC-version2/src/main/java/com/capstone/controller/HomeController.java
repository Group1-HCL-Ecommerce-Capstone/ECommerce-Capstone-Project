package com.capstone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "Welcome to sprind security";
	}
	
	@GetMapping("/adminTest")
	public String admin() {
		return "Welcome admin";
	}
}
