package com.capstone.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.model.Cart;
import com.capstone.model.Product;
import com.capstone.model.User;
import com.capstone.payload.dto.AddToCartDto;
import com.capstone.payload.dto.CartDto;
import com.capstone.payload.dto.CartItemDto;
import com.capstone.repository.CartRepository;
import com.capstone.repository.ProductRepository;

@Service
@Transactional
public class CartService {
	@Autowired
	CartRepository cartRepo;
	
	public Cart save(Cart crt) {
		return cartRepo.save(crt);
	}
	
	public Cart addProductToCart(AddToCartDto cartAdd, Product product, User user){
		Cart cart = new Cart(product, cartAdd.getQuantity(), user);
		return cartRepo.save(cart);
	}
	
	public CartDto listCartItems(Integer userId){
		List<Cart> cartList = cartRepo.findAllByUserUserId(userId);
		List<CartItemDto> cartItems = new ArrayList<>();
		for (Cart c:cartList) {
			CartItemDto req = getFromCart(c);
			cartItems.add(req);
		}
		double totalCost = 0;
		for (CartItemDto req:cartItems) {
			totalCost += (req.getProduct().getPrice()*req.getQuantity());
		}
		CartDto cartWithCost = new CartDto(cartItems, totalCost);
		return cartWithCost;
	}
	
	public static CartItemDto getFromCart(Cart cart) {
		CartItemDto req = new CartItemDto(cart);
		return req;
	}
	
	public void updateCartItem(AddToCartDto cartAdd, User user, Product product) {
		Cart cart = cartRepo.getOne(cartAdd.getId());
		cart.setQuantity(cartAdd.getQuantity());
		cartRepo.save(cart);
	}
	
	public void deleteCartItem(Integer cartId) throws IllegalArgumentException{
		if (!cartRepo.existsById(cartId)) {
			throw new IllegalArgumentException("Cart ID " + cartId + " does not exist");
		}
		cartRepo.deleteById(cartId);
	}
	
	public void deleteAllCartItems(Integer cartId) {
		cartRepo.deleteAll();
	}
	
	public void deleteUserCartItems(User user) {
		cartRepo.deleteByUserUserId(user.getUserId());
	}
	
}
