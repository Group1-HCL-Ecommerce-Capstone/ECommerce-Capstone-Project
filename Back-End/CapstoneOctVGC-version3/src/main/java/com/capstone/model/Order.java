package com.capstone.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_number")
	private int id;
	
	private Date dateOrdered;
	private String status;
	
	@ManyToOne
	@JoinColumn(name ="address_id")
	private Address address;
	
	@ManyToOne
	@JoinColumn(name ="user_id")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<Cart> carts;
	
}
