package com.example.porfolio_hisab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.entity.Stock;
import com.example.porfolio_hisab.repository.IStockRepository;
@Service
public class StockServiceImpl implements IStockServiceImpl {
	
	@Autowired
	IStockRepository stockRepo;
	//Add new stock
	@Override
	public String addStock(Stock stock) {
		stockRepo.save(stock);
		return "New Stock added sucessfully";
	}
	//View All stock
	@Override
	public List<Stock> viewAllStock() {
		List<Stock> listOfStock = stockRepo.findAll();
		return listOfStock;
	}
	//Delete Stock
	@Override
	public String deleteStock(long id) {
		Optional<Stock> opt = stockRepo.findById(id);
		if(opt.isPresent()) {
			stockRepo.deleteById(id);
			return "Deleted sucessfully";
		}else {
			return "Stock id not found";
		}
	}
	//View stock by id
	@Override
	public Stock viewStock(long id) {
		Optional<Stock> opt = stockRepo.findById(id);
		Stock stock= null;
		if(opt.isPresent()) {
			stock=opt.get();
			return stock;
		}else {
			return stock;
		}
	}

}
