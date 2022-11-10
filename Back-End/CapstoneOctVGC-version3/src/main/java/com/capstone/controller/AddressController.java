package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Address;
import com.capstone.model.User;
import com.capstone.payload.dto.AddressDto;
import com.capstone.service.AddressService;
import com.capstone.service.UserService;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	AddressService adrService;
	
	@Autowired
	UserService usrServ;
	
	@GetMapping("/all/{userId}")
	public ResponseEntity<List<Address>> findAddressesByUserId(@PathVariable Integer userId) {
		try {
			List<Address> adrs = adrService.listAllAddressesByUser(userId);
			if (adrs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(adrs, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add/{userId}")
	public ResponseEntity<Address> addAddress(@PathVariable Integer userId, @RequestBody AddressDto adr) {
		try {
			User user = usrServ.getUserById(userId).get();
			System.out.println(user.getFirstName());
			Address adrAdded = adrService.addAddress(adr, user);
			return new ResponseEntity<>(adrAdded, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/update/{addressId}")
	public ResponseEntity<Address> updateAddress(@PathVariable Integer addressId, @RequestBody Address adr) {
		try {
			Address databaseAdr = adrService.findByAddressId(addressId).get();

			if (Objects.nonNull(adr.getStreet())) {
				databaseAdr.setStreet(adr.getStreet());
			}
			if (Objects.nonNull(adr.getCity())) {
				databaseAdr.setCity(adr.getCity());
			}
			if (Objects.nonNull(adr.getState())) {
				databaseAdr.setState(adr.getState());
			}
			if (Objects.nonNull(adr.getCountry())) {
				databaseAdr.setCountry(adr.getCountry());
			}
			if (Objects.nonNull(adr.getZipcode())) {
				databaseAdr.setZipcode(adr.getZipcode());
			}
			if (Objects.nonNull(adr.getPhone())) {
				databaseAdr.setPhone(adr.getPhone());
			}
			return new ResponseEntity<>(adrService.save(databaseAdr), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<HttpStatus> deleteAddress(@PathVariable Integer addressId) {
		try {
			adrService.deleteAddressById(addressId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
