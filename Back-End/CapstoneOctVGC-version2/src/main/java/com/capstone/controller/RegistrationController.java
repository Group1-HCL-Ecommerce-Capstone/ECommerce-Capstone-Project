package com.capstone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.service.RegistrationService;

import lombok.AllArgsConstructor;

//@RestController
//@RequestMapping("/user")
//@AllArgsConstructor
public class RegistrationController {
	private RegistrationService registerService;
	
	@GetMapping("/index")
	public String home() {
		return "registration index";
	}
	
	/*
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request){
		registerService.register(request);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	*/
}
