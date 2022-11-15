package com.capstone.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Address;
import com.capstone.model.Order;
import com.capstone.model.User;
//added pay-load LoginRequest to get email address
import com.capstone.payload.LoginRequest;
import com.capstone.repository.AddressRepository;
import com.capstone.repository.UserRepository;
import com.capstone.service.EmailService;
import com.capstone.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderServ;
	
	//added auto-wired for EmailService
	@Autowired
	private EmailService emailService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	AddressRepository addressRepo;
	
	
	// i need to put in a check to make sure the address is associated with the user
	//added LoginRequest login and Integer orderId objects to get order number confirmation and email sent to the right email address
	@PostMapping("/add/{userId}/{addressId}")
	public ResponseEntity<Order> placeOrder(@PathVariable Integer userId, @PathVariable Integer addressId, LoginRequest login){
		try {
			Order placedOrder = orderServ.createOrder(userId, addressId);
			String userEmail = userRepo.getById(userId).getEmail();
			String userStreet = addressRepo.getById(addressId).getStreet();
			String userState = addressRepo.getById(addressId).getCity();
			String userCountry = addressRepo.getById(addressId).getCountry();
			String userZip = addressRepo.getById(addressId).getZipcode();
			
			//adding email confirmation and order number for confirmation
			emailService.sendMail(userEmail , "Order placed! Order number: "+ placedOrder.getId(), "Thank you for placing an order with us! Here's your order details: " 
			+ "\n"  + "Order Status: " + placedOrder.getStatus() + "\n" + "Shipping Address: " + userStreet + ", " + userState + " " + userZip + " " + userCountry +
			"\n" + "Total Order Price: $" + placedOrder.getTotalPrice());
			return new ResponseEntity<>(placedOrder, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all/{userId}")
	public ResponseEntity<List<Order>> getAllUserOrders(@PathVariable Integer userId){
		try {
			List<Order> allOrders = orderServ.listOrders(userId);
			return new ResponseEntity<>(allOrders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/find/{orderId}")
	public ResponseEntity<Order> findOrderById(@PathVariable Integer orderId){
		try {
			Order order = orderServ.findOrder(orderId);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// there is a better way to do this using the enum stuff like in roles but maybe it can be implemented later
	@PatchMapping("/updatestatus/{orderId}")
	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer orderId, @RequestBody Order order){
		try {
			Order databaseOrder = orderServ.findOrder(orderId);
			String status = order.getStatus();		
			
			if (Objects.nonNull(status)&&status.equalsIgnoreCase("shipped")) {
				databaseOrder.setStatus(status);
			} else if(Objects.nonNull(status)&&status.equalsIgnoreCase("delivered")) {
				databaseOrder.setStatus(status);
			}
			int userId = databaseOrder.getUser().getUserId();
			
			//push email status of order update
			User user = userRepo.findById(userId).get();
			String email = user.getEmail();
			Address address = databaseOrder.getAddress();
			String street = address.getStreet();
			String city = address.getCity();
			String state = address.getState();
			String zip = address.getZipcode();
			String country = address.getCountry();
			emailService.sendMail(email , "Order Update! Order number: "+ orderId, "Thank you for your patience! Here's your updated order details: "
			+ "\n" + "Order Status: " + status + "\n" + "Shipping Address: " + street + ", " + city + " " + state + " " 
					+ zip + " "+ country + "\n" + "Total Order Price: $"+ databaseOrder.getTotalPrice());
			 
			return new ResponseEntity<>(orderServ.save(databaseOrder), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}