package com.example.porfolio_hisab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.porfolio_hisab.service.IPortfolioService;

@RestController
@CrossOrigin
public class PortfolioController {
	@Autowired
	IPortfolioService portfolioService;
	
	//Add fund
	@PutMapping("portfolio/addFunds/{userId}")
	ResponseEntity<Double> addFunds(@PathVariable long userId, @RequestBody long fund){
		double updatedfund = portfolioService.addfunds(userId, fund);
		return new ResponseEntity<>(updatedfund, HttpStatus.OK);
	}
}
