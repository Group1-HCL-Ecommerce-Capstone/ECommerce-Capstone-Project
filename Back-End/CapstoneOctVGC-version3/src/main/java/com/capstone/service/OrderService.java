package com.capstone.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.model.Order;
import com.capstone.model.OrderItem;
import com.capstone.model.Product;
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
	ProductService prdServ;
	
	@Autowired
	UserService userService;
	
	// this is not working somewhere
	// SOLVED
	public Order createOrder(Integer userId, Integer addressId) {
		CartDto cart = cartService.listCartItems(userId);
		System.out.println(cart.getTotalPrice());
		List<CartItemDto> cartItems = cart.getCartItems();	
		System.out.println(cartItems.get(0));
		
		Order newOrder = new Order();
		newOrder.setDateOrdered(new Date());
		newOrder.setAddress(adrRepo.findById(addressId).get());
		newOrder.setUser(userService.getUserById(userId).get());
		newOrder.setStatus("ordered");
		newOrder.setTotalPrice(cart.getTotalPrice());
		newOrder.setCarts(cartService.findAllByUserId(userId));
		Order savedOrder = orderRepo.save(newOrder);
		System.out.println(newOrder.toString());
		
		
		for (CartItemDto item: cartItems) {
			
			System.out.println(item.getProduct().getId());
			System.out.println(item.getQuantity());
			
			Product inStock = prdServ.decreaseStock(item.getProduct().getId(), item.getQuantity());
			System.out.println(inStock.getName());
			if (inStock !=null) {
				OrderItem orderItems = new OrderItem();
				orderItems.setQuantity(item.getQuantity());
				orderItems.setProductId(item.getProduct().getId());
				orderItems.setOrder(newOrder);
				itemRepo.save(orderItems);
				System.out.println(orderItems.getProductId());
			}			
		}
		
		
		
		//System.out.println(savedOrder.toString());
		cartService.deleteUserCartItemsbyId(userId);
		return savedOrder;
	}
	
	public Order save(Order order) {
		return orderRepo.save(order);
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
