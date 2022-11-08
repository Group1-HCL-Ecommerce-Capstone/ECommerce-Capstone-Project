package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Cart;
import com.capstone.model.Product;
import com.capstone.model.User;
import com.capstone.payload.dto.AddToCartDto;
import com.capstone.payload.dto.CartDto;
import com.capstone.payload.dto.CartItemDto;
import com.capstone.repository.CartRepository;
import com.capstone.service.CartService;
import com.capstone.service.ProductService;
import com.capstone.service.UserService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartServ;
	
	@Autowired
	ProductService prdServ;
	
	@Autowired
	UserService usrServ;
	
	@GetMapping("/list/{userId}")
	public ResponseEntity<CartDto> showCart(@PathVariable Integer userId) {
		try {
			CartDto currentCart = cartServ.listCartItems(userId);
			return new ResponseEntity<>(currentCart, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/add/{userId}")
	public ResponseEntity<AddToCartDto> addToCart(@RequestBody AddToCartDto cartAdd, @PathVariable Integer userId){
		try {
			User user = usrServ.getUserById(userId).get();
			Product prd = prdServ.findByProductId(cartAdd.getProductId()).get();
			cartServ.addProductToCart(cartAdd, prd, user);
			return new ResponseEntity<>(cartAdd, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{userId}/{cartItemId}")
	public ResponseEntity<AddToCartDto> updateCartItem(@RequestBody AddToCartDto cartAdd, @PathVariable Integer userId, @PathVariable Integer cartItemId){
		try {
			User user = usrServ.getUserById(userId).get();
			Product prd = prdServ.findByProductId(cartAdd.getProductId()).get();
			cartServ.updateCartItem(cartAdd, user, prd);
			return new ResponseEntity<>(cartAdd, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable Integer cartItemId) {
		try {
			cartServ.deleteCartItem(cartItemId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}