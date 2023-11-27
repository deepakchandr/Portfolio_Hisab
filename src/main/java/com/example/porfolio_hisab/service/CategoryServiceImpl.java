package com.example.porfolio_hisab.service;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.repository.ICategoryRepository;
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	ICategoryRepository categoryRepo;
	//Adding new category
	@Override
	public String addCategory(Category cat) {
		categoryRepo.save(cat);
		return "New Category added sucessfully";
	}
}
