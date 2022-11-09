package com.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	//List<Address> findAddressByUserUserId(Integer userID);
}
