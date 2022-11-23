
package com.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.UserList;

@RestController
@RequestMapping("/admin")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
public class AdminController {

	@Autowired
	public Client client;

	@GetMapping("/users")
	@PreAuthorize("hasAuthority('Admin')")
	public UserList getUsers() {
		return client.listUsers();
	}

	//user?query=user@email.com
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('Admin')")
	public UserList searchUserByEmail(@RequestParam String query) {
		return client.listUsers(query, null, null, null, null);
	}

}
 