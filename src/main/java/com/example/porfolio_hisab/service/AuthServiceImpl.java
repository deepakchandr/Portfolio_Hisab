package com.example.porfolio_hisab.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.dto.LogoutDto;
import com.example.porfolio_hisab.dto.RegistrationInputDto;
import com.example.porfolio_hisab.dto.loginInputDto;
import com.example.porfolio_hisab.entity.Portfolio;
import com.example.porfolio_hisab.entity.StockHolder;
import com.example.porfolio_hisab.exceptions.InvalidCredentials;
import com.example.porfolio_hisab.exceptions.UserNotFoundException;
import com.example.porfolio_hisab.repository.IStockHolderRepository;

@Service
public class AuthServiceImpl implements IAuthService{
	@Autowired
	IStockHolderRepository stockHolderRepo;

	@Override
	public StockHolder registerNew(RegistrationInputDto regInput) {
		System.out.println("++++++++++INSIDE SERVICE++++++++++++");
		StockHolder newuser = new StockHolder();
		if(stockHolderRepo.findByUserName(regInput.getUserName()).isPresent()) {
			throw new UserNotFoundException("User already Exist try to give different user");
		}
		else {
			newuser.setFirst_Name(regInput.getFirst_Name());
			newuser.setLast_Name(regInput.getLast_Name());
			newuser.setUserName(regInput.getUserName());
			newuser.setPassword(regInput.getPassword());
			newuser.setMobileNo(regInput.getMobileNo());
			newuser.setEmail(regInput.getEmail());
			Portfolio portfolio = new Portfolio();
			portfolio.setBalance(0);
			portfolio.setStockCount(0);
			portfolio.setTotal(0);
			newuser.setPortFolio(portfolio);
			StockHolder newHolder=stockHolderRepo.save(newuser);
//			String str ="registration completed";
			return newHolder;
		}
		
	}

	@Override
	public String login(loginInputDto cred) {
		Optional<StockHolder> opt = stockHolderRepo.findByUserName(cred.getUserName());
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			if(cred.getPassword().equals(user.getPassword())) {
				return "Valid Cred Login Successful";
			}
			else {
				throw new InvalidCredentials("Wrong Password");
			}
		}
		else {
			throw new UserNotFoundException("Invalid User");
		}
	}

	@Override
	public String logout(LogoutDto cred) {
		Optional<StockHolder> opt = stockHolderRepo.findByUserName(cred.getUserName());
		if(opt.isPresent()) {
			return "Logout Successful";
		}
		else {
			throw new UserNotFoundException("Invalid User");
		}
	}

}
