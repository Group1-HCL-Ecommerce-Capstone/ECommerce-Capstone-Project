package com.capstone.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Product;
import com.capstone.service.ProductService;

@RestController
@RequestMapping("/admin/products")
public class ProductController {
	@Autowired
	ProductService prdService;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Product>> listOfAllProducts(){
		try {
			List<Product> allProducts = prdService.listAllPrds();
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Optional<Product>> selectProductById(@PathVariable Integer id){
		try {
			Optional<Product> foundProduct = prdService.findByProductId(id);
			return new ResponseEntity<>(foundProduct, HttpStatus.OK);
		} catch(NoSuchElementException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> registerProduct(@RequestBody Product prd){
		try {
			Product registeredProduct = prdService.addProduct(prd);
			return new ResponseEntity<>(registeredProduct, HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product prd){
		try {
			Product databaseProduct = prdService.findByProductId(id).get();
			if(Objects.nonNull(prd.getName())) {
				databaseProduct.setName(prd.getName());
			}
			if(Objects.nonNull(prd.getDescription())) {
				databaseProduct.setDescription(prd.getDescription());
			}
			if(Objects.nonNull(prd.getImage())) {
				databaseProduct.setImage(prd.getImage());
			}
			if(Objects.nonNull(prd.getPrice())&&prd.getStock()>0.00) {
				databaseProduct.setPrice(prd.getPrice());
			}
			if(Objects.nonNull(prd.getStock())&&prd.getStock()>0) {
				databaseProduct.setStock(prd.getStock());
			}
			//need to implement category updating
			if(Objects.nonNull(prd.getCategories())&&!prd.getCategories().isEmpty()) {
				databaseProduct.setCategories(prd.getCategories());
			}
			return new ResponseEntity<>(prdService.save(databaseProduct), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteProductById(@PathVariable Integer id){
		try {
		 prdService.deleteProductById(id);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
