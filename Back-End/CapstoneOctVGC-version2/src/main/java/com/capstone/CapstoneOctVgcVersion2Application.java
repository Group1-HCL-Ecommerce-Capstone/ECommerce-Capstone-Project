package com.capstone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.capstone.model.Role;
import com.capstone.model.User;
import com.capstone.repository.UserRepository;

@SpringBootApplication
public class CapstoneOctVgcVersion2Application {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneOctVgcVersion2Application.class, args);
	}
}
