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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.porfolio_hisab.dto.CategoryInputDto;
import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.service.ICategoryService;

@RestController
@CrossOrigin
public class CategoryController {
	@Autowired
	ICategoryService categoryService;
	
	//Add Category
	@PostMapping("/category/add/{userid}")
		ResponseEntity<String> addCategory(@PathVariable long userid, @RequestBody CategoryInputDto catName){
		String msg=categoryService.addCategory(userid,catName);
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	//View All
	@GetMapping("/category/viewAll/{userid}")
	ResponseEntity<List<Category>> viewAll(@PathVariable long userid){
		List<Category> list = categoryService.viewAllCategory(userid);
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
	//Delete Category by id
	@DeleteMapping("/category/delete/{userid}")
	ResponseEntity<String> deleteCategoryById(@PathVariable long userid, long id){
		String str = categoryService.deleteCategory(userid, id);
		return new ResponseEntity<>(str, HttpStatus.CREATED);
	}
	//View Category by Id
	@GetMapping("/category/viewById/{userid}/{id}")
	ResponseEntity<Category> viewCategoryById(@PathVariable long userid, long id){
		Category cat=categoryService.viewCategory(userid, id);
		return new ResponseEntity<>(cat, HttpStatus.CREATED);
	}
	
}
