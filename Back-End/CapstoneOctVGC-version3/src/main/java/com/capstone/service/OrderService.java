package com.capstone.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.model.Order;
import com.capstone.model.OrderItem;
import com.capstone.payload.dto.CartDto;
import com.capstone.payload.dto.CartItemDto;
import com.capstone.repository.AddressRepository;
import com.capstone.repository.OrderItemRepository;
import com.capstone.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	AddressRepository adrRepo;
	
	@Autowired
	OrderItemRepository itemRepo;
	
	@Autowired
	UserService userService;
	
	public Order createOrder(Integer userId, Integer addressId) {
		CartDto cart = cartService.listCartItems(userId);
		List<CartItemDto> cartItems = cart.getCartItems();
		
		Order newOrder = new Order();
		newOrder.setDateOrdered(new Date());
		newOrder.setAddress(adrRepo.findById(addressId).get());
		newOrder.setUser(userService.getUserById(userId).get());
		newOrder.setStatus("ordered");
		newOrder.setTotalPrice(cart.getTotalPrice());
		newOrder.setCarts(cartService.findAllByUserId(userId));
		
		for (CartItemDto item: cartItems) {
			OrderItem orderItems = new OrderItem();
			orderItems.setQuantity(item.getQuantity());
			orderItems.setProductId(item.getProduct().getId());
			orderItems.setOrder(newOrder);
			
			itemRepo.save(orderItems);
		}
		
		Order savedOrder = orderRepo.save(newOrder);
		cartService.deleteUserCartItemsbyId(userId);
		return savedOrder;
	}
	
	public List<Order> listOrders(Integer userId){
		return orderRepo.findAllByUserUserIdOrderByDateOrderedDesc(userId);
	}
	
	public Order findOrder(Integer orderId) throws IllegalArgumentException {
		Optional<Order> order = orderRepo.findById(orderId);
		if (order.isPresent()) {
			return order.get();
		}
		throw new IllegalArgumentException("Order not found");
	}
}
