package com.example.porfolio_hisab.service;





import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.dto.CategoryInputDto;
import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.entity.Portfolio;
import com.example.porfolio_hisab.entity.StockHolder;
import com.example.porfolio_hisab.exceptions.CategoryNotFoundException;
import com.example.porfolio_hisab.exceptions.UserNotFoundException;
import com.example.porfolio_hisab.repository.ICategoryRepository;
import com.example.porfolio_hisab.repository.IStockHolderRepository;
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	ICategoryRepository categoryRepo;
	@Autowired
	IStockHolderRepository stockHolderRepo;
	//Adding new category
	@Override
	public String addCategory(long userid, CategoryInputDto catName) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userid);
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			Portfolio protfolio=user.getPortFolio();
			List<Category> catList=protfolio.getCategory();
			Category category = new Category();
			category.setCategoryName(catName.getCategoryName());
			catList.add(category);
			protfolio.setCategory(catList);
			user.setPortFolio(protfolio);
			stockHolderRepo.save(user);
			
			return "New Category added sucessfully";
		}else {
			throw new UserNotFoundException("Invalid User");
		}
		
	}
	//View all Category
	@Override
	public List<Category> viewAllCategory(long userid) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userid);
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			List<Category> listOfCategory = user.getPortFolio().getCategory();
			return listOfCategory;
		}else {
			throw new UserNotFoundException("Invalid User");
		}
		
	}
	//Delete category by id
	@Override
	public String deleteCategory(long userid, long id) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userid);
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			Optional<Category> optcat = categoryRepo.findById(id);
			if(optcat.isPresent()) {
				List<Category> listOfCategory = user.getPortFolio().getCategory();
				if(listOfCategory.contains(optcat.get())) {
					categoryRepo.deleteById(id);
					return "Deleted sucessfully";
				}else {
					throw new CategoryNotFoundException("Category does not exists for this user");
				}
			}else {
				throw new CategoryNotFoundException("Category does not exists");
			}
		}else {
			throw new UserNotFoundException("Invalid User");
		}
		
	}
	//View category by id
	@Override
	public Category viewCategory(long userid, long id) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userid);
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			Optional<Category> optcat = categoryRepo.findById(id);
			if(optcat.isPresent()) {
				List<Category> listOfCategory = user.getPortFolio().getCategory();
				if(listOfCategory.contains(optcat.get())) {
					return optcat.get();
				}else {
					throw new CategoryNotFoundException("Category does not exists for this user");
				}
			}else {
				throw new CategoryNotFoundException("Category does not exists");
			}
		}else {
			throw new UserNotFoundException("Invalid User");
		}
	}
	
	
}
