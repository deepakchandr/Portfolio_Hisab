package com.example.porfolio_hisab.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Portfolio {
	@Id
	@GeneratedValue
	private long id;
	private double stockCount;
	private double total;
	private double balance=0;
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="portfolio_stock_fk")
	private List<Stock> stock;
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="portfolio_category_fk")
	private List<Category> category;

}
