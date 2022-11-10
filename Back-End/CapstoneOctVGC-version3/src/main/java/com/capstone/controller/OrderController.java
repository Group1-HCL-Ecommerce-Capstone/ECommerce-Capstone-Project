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

import com.capstone.model.Order;
import com.capstone.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderServ;
	
	// i need to put in a check to make sure the address is associated with the user
	@PostMapping("/add/{userId}/{addressId}")
	public ResponseEntity<Order> placeOrder(@PathVariable Integer userId, @PathVariable Integer addressId){
		try {
			Order placedOrder = orderServ.createOrder(userId, addressId);
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
			return new ResponseEntity<>(orderServ.save(databaseOrder), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
