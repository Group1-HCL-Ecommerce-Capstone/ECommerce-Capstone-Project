package com.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.capstone.service.EmailService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	//added autowired for EmailService
	//@Autowired
    //private EmailService emailService;
	
	@GetMapping("/all")
		public String welcome() {
		return "Welcome to Spring Security Test HOME";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('User') or hasAuthority('Admin')")
	public String welcomeUser() {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "Hello user";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('Admin')")
	public String welcomeAdmin() {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "Hello admin";
	}
	
	//added mapping for to test sending an email via /test/sendemail
    //@GetMapping(value = "/sendemail")
    //public String sendmail() {

        //emailService.sendMail("test@example.com", "Test Subject", "Test mail body");

        //return "emailsent";
    //}
}