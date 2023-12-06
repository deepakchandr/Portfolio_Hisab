package com.example.porfolio_hisab.dto;

import lombok.Data;

@Data
public class StockInputDto {
	private String stockName;
	private double price;
	private double quantity;
	private String description;
	private String categoryName;
}
