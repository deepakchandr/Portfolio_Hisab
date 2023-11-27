package com.example.porfolio_hisab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.service.ICategoryService;

@RestController
@CrossOrigin
public class CategoryController {
	@Autowired
	ICategoryService categoryService;
	
	//Add Category
	@PostMapping("/category/add")
		ResponseEntity<String> addCategory( @RequestBody Category cat){
		String msg=categoryService.addCategory(cat);
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	
}
