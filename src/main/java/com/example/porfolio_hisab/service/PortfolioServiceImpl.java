package com.example.porfolio_hisab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.entity.Stock;
import com.example.porfolio_hisab.entity.StockHolder;
import com.example.porfolio_hisab.exceptions.UserNotFoundException;
import com.example.porfolio_hisab.repository.IStockHolderRepository;

@Service
public class PortfolioServiceImpl implements IPortfolioService {

	@Autowired
	IStockHolderRepository stockHolderRepo;
	
	@Override
	public double addfunds(long id, long fund) {
		Optional<StockHolder> opt = stockHolderRepo.findById(id);
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			user.getPortFolio().setBalance((user.getPortFolio().getBalance())+fund);
			stockHolderRepo.save(user);
			return user.getPortFolio().getBalance();
		}else {
			throw new UserNotFoundException("Invalid User");
		}
	}

}
