package com.example.porfolio_hisab.service;

import java.util.List;

import com.example.porfolio_hisab.dto.BuySellStock;
import com.example.porfolio_hisab.dto.StockInputDto;
import com.example.porfolio_hisab.entity.Stock;

public interface IStockServiceImpl {
	public String addStock(long userId, StockInputDto stock);
	public List<Stock> viewAllStock(long userId);
	public String deleteStock(long userId, long id);
	public Stock viewStock(long id);  
	public List<Stock> getStocksByCategoryId(Long categoryId);
	public void calculateAndSetWeightagesForCategory(long categoryId);
	public void calculateAndSetWeightages(long userId);
	public void calculateAndSetCategoryWeightages(long userId);
	public Stock buyStock(long id, BuySellStock buysellPriceQuantity);
	public Stock sellStock(long id, BuySellStock buysellPriceQuantity);
}
