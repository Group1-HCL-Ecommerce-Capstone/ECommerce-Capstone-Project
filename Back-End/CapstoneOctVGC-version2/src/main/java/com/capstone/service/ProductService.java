package com.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.model.Product;
import com.capstone.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	
	public Product save(Product prd) {
		return productRepo.save(prd);
	}
	
	public List<Product> listAllProducts(){
		return productRepo.findAll();
	}
	
	public Product createProduct(Product usr) {
		return productRepo.save(usr);
	}
	
	public Optional<Product> findProductById(String prdId){
		return productRepo.findById(prdId);
	}
	
	public void deleteProductById(String prdId) {
		productRepo.deleteById(prdId);
	}
		
}
