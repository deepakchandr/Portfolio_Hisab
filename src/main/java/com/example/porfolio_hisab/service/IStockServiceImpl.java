package com.example.porfolio_hisab.service;

import java.util.List;

import com.example.porfolio_hisab.dto.StockInputDto;
import com.example.porfolio_hisab.entity.Stock;

public interface IStockServiceImpl {
	public String addStock(StockInputDto stock);
	public List<Stock> viewAllStock();
	public String deleteStock(long id);
	public Stock viewStock(long id);  
	public List<Stock> getStocksByCategoryId(Long categoryId);
	public void calculateAndSetWeightages(Long categoryId);
	public void calculateAndSetWeightages();
	public void calculateAndSetCategoryWeightages();
}
