package com.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.model.User;
import com.capstone.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	public User save(User usr) {
		return userRepo.save(usr);
	}
	
	public List<User> listAllUsers(){
		return userRepo.findAll();
	}
	
	public User registerUser(User usr) {
		return userRepo.save(usr);
	}
	
	public Optional<User> findUserById(Integer userId){
		return userRepo.findById(userId);
	}
	
	public void deleteUserById(Integer userId) {
		userRepo.deleteById(userId);
	}
}
