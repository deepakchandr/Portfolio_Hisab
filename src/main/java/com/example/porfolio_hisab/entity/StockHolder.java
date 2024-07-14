package com.example.porfolio_hisab.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import lombok.Data;

@Entity
@Data
public class StockHolder {
	
	@Id
	@GeneratedValue
	private long id;
	private String first_Name;
	private String last_Name;
	private String userName;
	private String password;
	private long mobileNo;
	@Email(message = "Invalid Email Address")
	private String email;
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_porfolio_fk")
	private Portfolio portFolio;
	

}
