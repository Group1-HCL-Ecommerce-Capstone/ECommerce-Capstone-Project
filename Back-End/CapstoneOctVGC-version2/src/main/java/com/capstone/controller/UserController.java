package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.User;
import com.capstone.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private User usr;
	
	@Autowired
	UserService usrService;
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> listOfAllUsers(){
		try {
			List<User> allUsers = usrService.listAllUsers();
			if(allUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(allUsers, HttpStatus.OK);
			}
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<User>> selectUserById(@PathVariable Integer userId){
		try {
			Optional<User> foundUser = usrService.findUserById(userId);
			return new ResponseEntity<>(foundUser, HttpStatus.OK);
		} catch(NoSuchElementException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User usr){
		try {
			User registeredUser = usrService.registerUser(usr);
			return new ResponseEntity<>(registeredUser, HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
