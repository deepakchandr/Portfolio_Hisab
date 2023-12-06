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

import com.example.porfolio_hisab.dto.CategoryInputDto;
import com.example.porfolio_hisab.dto.InputCategoryDto;
import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.service.ICategoryService;

@RestController
@CrossOrigin
public class CategoryController {
	@Autowired
	ICategoryService categoryService;
	
	//Add Category
	@PostMapping("/category/add")
		ResponseEntity<String> addCategory( @RequestBody CategoryInputDto catName){
		String msg=categoryService.addCategory(catName);
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	//View All
	@GetMapping("/category/viewAll")
	ResponseEntity<List<Category>> viewAll(){
		List<Category> list = categoryService.viewAllCategory();
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
	//Delete Category by id
	@DeleteMapping("/category/delete")
	ResponseEntity<String> deleteCategoryById(long id){
		String str = categoryService.deleteCategory(id);
		return new ResponseEntity<>(str, HttpStatus.CREATED);
	}
	//View Category by Id
	@GetMapping("/category/viewById")
	ResponseEntity<Category> viewCategoryById(long id){
		Category cat=categoryService.viewCategory(id);
		return new ResponseEntity<>(cat, HttpStatus.CREATED);
	}
	
}
