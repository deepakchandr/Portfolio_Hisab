package com.example.porfolio_hisab.exceptions;

public class InsufficentStockQuantity extends RuntimeException {
	public InsufficentStockQuantity(String message) {
		super(message);
	}
}
