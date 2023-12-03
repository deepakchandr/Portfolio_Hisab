package com.example.porfolio_hisab.service;

import java.util.List;
import com.example.porfolio_hisab.entity.Stock;

public interface IStockServiceImpl {
	public String addStock(Stock stock);
	public List<Stock> viewAllStock();
	public String deleteStock(long id);
	public Stock viewStock(long id);  
}
