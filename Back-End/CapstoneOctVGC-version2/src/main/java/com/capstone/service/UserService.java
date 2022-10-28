package com.capstone.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.dto.UserDTO;
import com.capstone.model.Role;
import com.capstone.model.User;
import com.capstone.repository.RoleRepository;
import com.capstone.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder pwdEncoder;
	/*
	public User saveAdmin(UserDTO userDto) {
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(pwdEncoder.encode(userDto.getPassword()));
		
		Role role = roleRepo.findByRoleType("ADMIN");
		if (role == null) {
			role = roleExists("ADMIN");
		}
		user.setRoles(Arrays.asList(role));
		return userRepo.save(user);
		
	}
	*/
	public User saveUser(UserDTO userDto) {
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(pwdEncoder.encode(userDto.getPassword()));
		
		Role role = roleRepo.findByRoleType("USER");
		if (role == null) {
			role = roleExists("USER");
		}
		user.setRoles(Arrays.asList(role));
		return userRepo.save(user);
		
	}
	
	private Role roleExists(String type) {
		Role role = new Role();
		if (type.equalsIgnoreCase("admin")) {
			role.setRoleType("ADMIN");
		} else if (type.equalsIgnoreCase("user")) {
			role.setRoleType("USER");
		}
		return roleRepo.save(role);
	}
	/*
	public List<UserDTO> listAllUsers(){
		List<User> users = userRepo.findAll();
		return users.stream()
				.map((user)-> mapToUserDto(user)
				.collect(Collectors.toList()));
	}
	
	private UserDTO mapToUserDto(User user) {
		UserDTO userDto = new UserDTO();
		String[] str = user.get
	}
	*/
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
	
	public Optional<User> findUserByEmail(String email){
		return userRepo.findByEmail(email);
	}
	
	public boolean userExistsByEmail(String email) {
		return userRepo.findByEmail(email).isPresent();
	}
	
	public void deleteUserById(Integer userId) {
		userRepo.deleteById(userId);
	}
}
