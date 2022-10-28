package com.capstone.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Products")
public class Product {
	
	@Id
	private String productId;
	private String productName;
	private String productDescription;
	private String productPhoto;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "productsToCategories",
				joinColumns = @JoinColumn(name = "product_id"),
				inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> category = new HashSet<>();
	
	private double productPrice;
	private int productStock;
	
	

}
