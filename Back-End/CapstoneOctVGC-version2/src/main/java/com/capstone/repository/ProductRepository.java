package com.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

}
