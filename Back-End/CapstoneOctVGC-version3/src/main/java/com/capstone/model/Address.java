package com.capstone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "Addresses")
public class Address {
	//@Id
	//@Column(name = "address_id")
	private int addressId;
	private String street;
	private String city;
	
	@Size(max = 6)
	private String state;
	
	@Size(max=3)
	private String country;
	
	@Size(max=15)
	private String zipcode;
	
	@Size(max=13)
	private int phone;
	/*
	@OneToOne
	@MapsId
	@JoinColumn(name="user_id")
	private User user;
	*/
}
