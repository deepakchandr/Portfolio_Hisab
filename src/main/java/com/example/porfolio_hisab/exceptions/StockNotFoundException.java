package com.example.porfolio_hisab.exceptions;

public class StockNotFoundException extends RuntimeException {
	public StockNotFoundException(String message) {
		super(message);
	}
}
