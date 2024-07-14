package com.example.porfolio_hisab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.porfolio_hisab.dto.BuySellStock;
import com.example.porfolio_hisab.dto.StockInputDto;
import com.example.porfolio_hisab.entity.Stock;
import com.example.porfolio_hisab.service.IStockServiceImpl;

@RestController
@CrossOrigin
public class StockController {
	@Autowired
	IStockServiceImpl stockService;
	
	//Add Stock
	@PostMapping("/stock/add/{userId}")
		ResponseEntity<String> addStock(@PathVariable long userId, @RequestBody StockInputDto stock){
		System.out.println("--------"+stock+"--------");
		String msg=stockService.addStock(userId, stock);
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	//View All
	@GetMapping("/stock/viewAll/{userId}")
	ResponseEntity<List<Stock>> viewAll(@PathVariable long userId){
		List<Stock> list = stockService.viewAllStock(userId);
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
	//Delete Stock by id
	@DeleteMapping("/stock/delete/{userId}/{id}")
	ResponseEntity<String> deleteStockById(@PathVariable long userId,@PathVariable long id){
		System.out.println("-----------Inside delete controller--------");
		String str = stockService.deleteStock(userId, id);
		return new ResponseEntity<>(str, HttpStatus.OK);
	}
	//View Stock by Id
	@GetMapping("/stock/viewById/{id}")
	ResponseEntity<Stock> viewStockById(@PathVariable long id){
		System.out.println("-----------View Stock By ID--------");
		Stock cat=stockService.viewStock(id);
		return new ResponseEntity<>(cat, HttpStatus.OK);
	}
	//Buy stock by ID
	@PutMapping("/stock/buy/{id}")
	ResponseEntity<Stock> buyStockById(@PathVariable long id, @RequestBody BuySellStock buysellPriceQuantity){
		Stock stk= stockService.buyStock(id,buysellPriceQuantity);
		return new ResponseEntity<>(stk, HttpStatus.OK);
	}
	
	//Sell Stock by ID
	@PutMapping("/stock/sell/{id}")
	ResponseEntity<Stock> sellStockById(@PathVariable long id, @RequestBody BuySellStock buysellPriceQuantity){
		Stock stk= stockService.sellStock(id,buysellPriceQuantity);
		return new ResponseEntity<>(stk, HttpStatus.OK);
	}
}
