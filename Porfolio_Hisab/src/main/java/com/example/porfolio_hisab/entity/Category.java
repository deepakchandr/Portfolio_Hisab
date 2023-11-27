package com.example.porfolio_hisab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;


@Entity
@Data 	
public class Category {
	@Id
	@GeneratedValue
	private long id;
	private String categoryName;
	private String weightCat;

}
