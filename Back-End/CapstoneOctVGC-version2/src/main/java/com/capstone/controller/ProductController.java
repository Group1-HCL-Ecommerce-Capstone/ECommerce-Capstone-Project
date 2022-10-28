package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Product;
import com.capstone.model.User;
import com.capstone.service.ProductService;

@RestController
@RequestMapping("/admin/products")
public class ProductController {
	@Autowired
	ProductService prdService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> listOfAllProducts(){
		try {
			List<Product> allProducts = prdService.listAllProducts();
			if(allProducts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(allProducts, HttpStatus.OK);
			}
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Optional<Product>> selectProductById(@PathVariable String productId){
		try {
			Optional<Product> foundProduct = prdService.findProductById(productId);
			return new ResponseEntity<>(foundProduct, HttpStatus.OK);
		} catch(NoSuchElementException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Product> registerProduct(@RequestBody Product prd){
		try {
			Product registeredProduct = prdService.createProduct(prd);
			return new ResponseEntity<>(registeredProduct, HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product prd){
		try {
			Product databaseProduct = prdService.findProductById(productId).get();
			if(Objects.nonNull(prd.getProductName())) {
				databaseProduct.setProductName(prd.getProductName());
			}
			if(Objects.nonNull(prd.getProductStock())) {
				databaseProduct.setProductStock(prd.getProductStock());
			}
			// for now just allowing to change Name and Stock
			
			return new ResponseEntity<>(prdService.save(databaseProduct), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id})")
	public ResponseEntity<HttpStatus> deleteProductById(@PathVariable String productId){
		try {
		 prdService.deleteProductById(productId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
