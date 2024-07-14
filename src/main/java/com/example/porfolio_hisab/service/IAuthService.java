package com.example.porfolio_hisab.service;

import com.example.porfolio_hisab.dto.LogoutDto;
import com.example.porfolio_hisab.dto.RegistrationInputDto;
import com.example.porfolio_hisab.dto.loginInputDto;
import com.example.porfolio_hisab.entity.StockHolder;

public interface IAuthService {
	public StockHolder registerNew(RegistrationInputDto regInput);
	public String login(loginInputDto cred);
	public String logout(LogoutDto cred);

}
