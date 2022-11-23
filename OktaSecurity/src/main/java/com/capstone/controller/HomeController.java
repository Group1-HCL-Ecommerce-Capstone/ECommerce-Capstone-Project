package com.capstone.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	//Get mapping to home after a user logs in with their correct credentials
	@GetMapping("/")
	public String home(@AuthenticationPrincipal OidcUser user) {
		return "Welcome, " + user.getAuthorities() + "<br/>" + user.getAddress().getCountry() + "!";
	}

}
