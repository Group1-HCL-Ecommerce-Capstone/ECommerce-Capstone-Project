package com.capstone;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
//@EnableOAuth2Client
@ComponentScan(basePackages = "com.capstone")
public class CapstoneOctVgcVersion3Application {
	//@GetMapping("/")
	//public String welcome2User(Principal principal) {
	//return "Hi " + principal.getName() + " Welcome to your homepage";
	//}

	public static void main(String[] args) {
		SpringApplication.run(CapstoneOctVgcVersion3Application.class, args);
	}

}
