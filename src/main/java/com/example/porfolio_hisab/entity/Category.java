package com.example.porfolio_hisab.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;


@Entity
@Data 	
public class Category {
	@Id
	@GeneratedValue
	private long id;
	private String categoryName;
	private double weightCat;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Stock> stock;

}
