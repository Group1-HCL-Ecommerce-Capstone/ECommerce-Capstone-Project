package com.capstone.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@Column(name = "prd_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private String image;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(
			name = "Products_To_Categories",
			joinColumns = @JoinColumn(name = "prd_id"),
			inverseJoinColumns = @JoinColumn(name = "cat_id"))
	private Set<Category> categories = new HashSet<>();
	private double price;
	private int stock;
	
	public Product(String name, String description, String image, Set<Category> categories, double price, int stock) {
		this.name = name;
		this.description = description;
		this.image = image;
		this.categories = categories;
		this.price = price;
		this.stock = stock;
	}
	
	public void addCategory(Category cat) {
		this.categories.add(cat);
		cat.getProducts().add(this);
	}
	
	public void removeCategory(int catId) {
		Category cat = this.categories.stream().filter(c -> c.getId() == catId).findFirst().orElse(null);
				if (cat != null) {
					this.categories.remove(cat);
					cat.getProducts().remove(this);
				}
	}

}
