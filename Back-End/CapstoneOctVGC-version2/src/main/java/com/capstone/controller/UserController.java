package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.User;
import com.capstone.service.UserService;

@RestController
@RequestMapping("/admin")
public class UserController {	
	
	@Autowired
	UserService usrService;
	
	public UserController(UserService usrService) {
		this.usrService = usrService;
	}
	
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
	
	@GetMapping("/find/{id}")
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
	
	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUserDetails(@PathVariable Integer userId, @RequestBody User usr){
		try {
			User databaseUser = usrService.findUserById(userId).get();
			if(Objects.nonNull(usr.getFirstName())) {
				databaseUser.setFirstName(usr.getFirstName());
			}
			if(Objects.nonNull(usr.getLastName())) {
				databaseUser.setLastName(usr.getLastName());
			}
			// for now just allowing to change first and last name will later find out
			// how to change password and role type in a better way
			return new ResponseEntity<>(usrService.save(databaseUser), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id})")
	public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Integer userId){
		try {
			usrService.deleteUserById(userId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}