package com.example.porfolio_hisab.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.porfolio_hisab.dto.LogoutDto;
import com.example.porfolio_hisab.dto.RegistrationInputDto;
import com.example.porfolio_hisab.dto.loginInputDto;
import com.example.porfolio_hisab.entity.StockHolder;
import com.example.porfolio_hisab.service.IAuthService;

@RestController
@CrossOrigin
public class AuthenticationController {
	@Autowired
	IAuthService authService;
	
	//New User Registration
	@PostMapping("/stockHolder/registration")
	ResponseEntity<StockHolder> register(@Valid @RequestBody RegistrationInputDto regInput){
		System.out.println("+++++INSIDE CONTROLLER+++++++++");
		StockHolder reg = authService.registerNew(regInput);
		return new ResponseEntity<>(reg,HttpStatus.OK);
	}
	//Login
	@PostMapping("/stockHolder/login")
	ResponseEntity<String> login(@Valid @RequestBody loginInputDto cred){
		String outCred = authService.login(cred);
		return new ResponseEntity<String>(outCred,HttpStatus.OK);
	}
	//Logout
		@PostMapping("/stockHolder/logout")
		ResponseEntity<String> logout(@Valid @RequestBody LogoutDto cred){
			String outCred = authService.logout(cred);
			return new ResponseEntity<String>(outCred,HttpStatus.OK);
		}
}
