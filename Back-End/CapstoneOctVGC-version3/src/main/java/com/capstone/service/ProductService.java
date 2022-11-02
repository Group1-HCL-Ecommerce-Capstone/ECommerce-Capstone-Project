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
	private ProductRepository repo;
	
	public Product save(Product prd) {
		return repo.save(prd);
	}
	
	public List<Product> listAllPrds(){
		return repo.findAll();
	}
	
	public Product addProduct(Product prd) {
		return repo.save(prd);
	}
	
	public Optional<Product> findByProductId(Integer id){
		return repo.findById(id);
	}
	
	public void deleteProductById(Integer id) {
		repo.deleteById(id);
	}
	
	public boolean productExistsById(Integer id) {
		return repo.existsById(id);
	}
}
