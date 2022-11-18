package com.capstone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		cartAdd.setProductId(product.getId());
		int quantity = cartAdd.getQuantity();
		int stock = product.getStock();
		if (stock>=quantity) {
			Cart cart = new Cart(product, cartAdd.getQuantity(), user);
			return cartRepo.save(cart);
		} else {
			return null;
		}
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
		int totalQuantity = 0;
		for (CartItemDto req:cartItems) {
			totalQuantity += req.getQuantity();
		}
		CartDto cartWithCost = new CartDto(cartItems, totalCost, totalQuantity);
		return cartWithCost;
	}
	
	public static CartItemDto getFromCart(Cart cart) {
		CartItemDto req = new CartItemDto(cart);
		return req;
	}
	
	/*
	public void updateCartItem(AddToCartDto cartAdd, User user, Product product) {
		Cart cart = cartRepo.getOne(cartAdd.getId());
		cart.setQuantity(cartAdd.getQuantity());
		cartRepo.save(cart);
	}
	*/
	public void deleteCartItem(Integer cartId) throws IllegalArgumentException{
		if (!cartRepo.existsById(cartId)) {
			throw new IllegalArgumentException("Cart ID " + cartId + " does not exist");
		}
		cartRepo.deleteById(cartId);
	}
	
	public void deleteAllCartItems(Integer cartId) {
		cartRepo.deleteAll();
	}
	
	public void deleteUserCartItemsbyId(Integer userId) {
		cartRepo.deleteByUserUserId(userId);
	}
	
	public List<Cart> findAllByUserId(Integer userId) {
		return cartRepo.findAllByUserUserId(userId);
	}
	
	public Optional<Cart> findCartByUserAndProductId(Integer userId, Integer prdId){
		return cartRepo.findCartByUserUserIdAndProductId(userId, prdId);
	}
}
