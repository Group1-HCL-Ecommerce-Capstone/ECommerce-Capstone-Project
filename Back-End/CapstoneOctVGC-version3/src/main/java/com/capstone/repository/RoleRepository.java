package com.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.model.Role;
import com.capstone.model.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByRoleType(RoleEnum type);

}
