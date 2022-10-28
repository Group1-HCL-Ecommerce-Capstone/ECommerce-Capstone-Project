package com.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	Boolean emailExists(String email);
}
