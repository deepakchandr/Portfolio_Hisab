package com.example.porfolio_hisab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.porfolio_hisab.dto.StockInputDto;
import com.example.porfolio_hisab.entity.Stock;
import com.example.porfolio_hisab.service.IStockServiceImpl;

@RestController
@CrossOrigin
public class StockController {
	@Autowired
	IStockServiceImpl stockService;
	
	//Add Stock
	@PostMapping("/stock/add")
		ResponseEntity<String> addStock(@RequestBody StockInputDto stock){
		String msg=stockService.addStock(stock);
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	//View All
	@GetMapping("/stock/viewAll")
	ResponseEntity<List<Stock>> viewAll(){
		List<Stock> list = stockService.viewAllStock();
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
	//Delete Stock by id
	@DeleteMapping("/stock/delete")
	ResponseEntity<String> deleteStockById(long id){
		String str = stockService.deleteStock(id);
		return new ResponseEntity<>(str, HttpStatus.CREATED);
	}
	//View Stock by Id
	@GetMapping("/stock/viewById")
	ResponseEntity<Stock> viewStockById(long id){
		Stock cat=stockService.viewStock(id);
		return new ResponseEntity<>(cat, HttpStatus.CREATED);
	}
	
}
