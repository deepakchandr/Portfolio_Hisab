package com.example.porfolio_hisab.service;

import java.util.List;


import com.example.porfolio_hisab.dto.CategoryInputDto;
import com.example.porfolio_hisab.entity.Category;

public interface ICategoryService {
	public String addCategory(long userid, CategoryInputDto catName);
	public List<Category> viewAllCategory(long userid);
	public String deleteCategory(long userid, long id);
	public Category viewCategory(long userid, long id); 
}
