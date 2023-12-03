package com.example.porfolio_hisab.service;





import java.util.List;
import java.util.Optional;

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
	//View all Category
	@Override
	public List<Category> viewAllCategory() {
		List<Category> listOfCategory = categoryRepo.findAll();
		return listOfCategory;
	}
	//Delete category by id
	@Override
	public String deleteCategory(long id) {
		Optional<Category> opt = categoryRepo.findById(id);
		if(opt.isPresent()) {
			categoryRepo.deleteById(id);
			return "Deleted sucessfully";
		}else {
			return "Category id not found";
		}
	}
	//View category by id
	@Override
	public Category viewCategory(long id) {
		Optional<Category> opt = categoryRepo.findById(id);
		Category cat= null;
		if(opt.isPresent()) {
			cat=opt.get();
			return cat;
		}else {
			return cat;
		}
	}
}
