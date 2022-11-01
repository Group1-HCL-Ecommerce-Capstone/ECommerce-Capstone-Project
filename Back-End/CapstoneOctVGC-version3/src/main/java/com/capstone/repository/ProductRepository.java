package com.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
