package com.example.porfolio_hisab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class Stock {
	@Id
	@GeneratedValue
	private long id;
	private String stockName;
	private double price;
	private double quantity;
	private String description;
	private double weightStock;
	private double catWeightStock;
	@ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
