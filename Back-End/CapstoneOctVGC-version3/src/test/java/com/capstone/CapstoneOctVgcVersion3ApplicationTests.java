package com.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.capstone.controller.AuthController;
import com.capstone.controller.UserController;
import com.capstone.model.User;
import com.capstone.payload.LoginRequest;
import com.capstone.payload.RegisterRequest;
import com.capstone.repository.UserRepository;
import com.capstone.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CapstoneOctVgcVersion3ApplicationTests {

	@Autowired
	AuthController auth;
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserService userServ;
	
	@Test
	void registeringAndLoggingIn() {
		//Testing registering a new user
		RegisterRequest newUser = new RegisterRequest();
		newUser.setFirstName("Test");
		newUser.setLastName("Ing");
		newUser.setEmail("test@test.com");
		newUser.setPassword("password");
		//newUser.setRole(null);
		ResponseEntity<?> register = auth.registerUser(newUser);
		assertEquals(HttpStatus.OK, register.getStatusCode(), "Register Unsuccessful");
		
		//Testing logging in the newly created user
		LoginRequest user = new LoginRequest();
		user.setEmail(newUser.getEmail());
		user.setPassword(newUser.getPassword());
		ResponseEntity<?> login = auth.authenticateUser(user);
		assertEquals(HttpStatus.OK, login.getStatusCode(), "Login Unsuccessful");
		
		//Delete Test User
		User test = userRepo.findByEmail(newUser.getEmail()).get();
		userServ.deleteUser(test.getUserId());
	}

}
