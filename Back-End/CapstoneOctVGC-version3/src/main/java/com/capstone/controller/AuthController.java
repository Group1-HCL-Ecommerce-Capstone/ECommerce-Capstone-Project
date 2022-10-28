package com.capstone.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Role;
import com.capstone.model.RoleEnum;
import com.capstone.model.User;
import com.capstone.payload.JwtResponse;
import com.capstone.payload.LoginRequest;
import com.capstone.payload.MessageResponse;
import com.capstone.payload.RegisterRequest;
import com.capstone.repository.RoleRepository;
import com.capstone.repository.UserRepository;
import com.capstone.security.JwtUtils;
import com.capstone.service.UserDetailsImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest login) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateJwtToken(auth);
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getEmail(),
				userDetails.getFirstName(), userDetails.getLastName(), roles));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest register) {
		if (userRepo.emailExists(register.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use"));
		}

		User user = new User(register.getEmail(), 
				encoder.encode(register.getPassword()), 
				register.getFirstName(), 
				register.getLastName());
		Set<String> rolesString = register.getRole();
		Set<Role> roles = new HashSet<>();
		if(rolesString == null) {
			Role userRole = roleRepo.findByRoleType(RoleEnum.ROLE_USER)
					.orElseThrow(()-> new RuntimeException("Error: Role not found"));
			roles.add(userRole);
		} else {
			rolesString.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepo.findByRoleType(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role not found"));
					roles.add(adminRole);
					break;
				case "manager":
					Role managerRole = roleRepo.findByRoleType(RoleEnum.ROLE_MANAGER)
					.orElseThrow(()-> new RuntimeException("Error: Role not found"));
					roles.add(managerRole);
					break;
				default:
					Role userRole = roleRepo.findByRoleType(RoleEnum.ROLE_USER)
					.orElseThrow(()-> new RuntimeException("Error: Role not found"));
					roles.add(userRole);
					break;

				}
			});
		}
		
		user.setRoles(roles);
		userRepo.save(user);
		return ResponseEntity.ok(new MessageResponse("Register Success!"));
}

}
