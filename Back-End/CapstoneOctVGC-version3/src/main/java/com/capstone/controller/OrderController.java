package com.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
}
