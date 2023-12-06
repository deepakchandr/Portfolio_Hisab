package com.example.porfolio_hisab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.dto.StockInputDto;
import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.entity.Stock;
import com.example.porfolio_hisab.repository.ICategoryRepository;
import com.example.porfolio_hisab.repository.IStockRepository;
@Service
public class StockServiceImpl implements IStockServiceImpl {
	
	@Autowired
	CategoryServiceImpl catServiceImp;
	@Autowired
	IStockRepository stockRepo;
	@Autowired
	ICategoryRepository categoryRepo;
	//Add new stock
	@Override
	public String addStock(StockInputDto stock) {
		Stock stk = new Stock();
		stk.setStockName(stock.getStockName());
		stk.setPrice(stock.getPrice());
		stk.setQuantity(stock.getQuantity());
		stk.setDescription(stock.getDescription());
		long categoryId=categoryRepo.getIdByName(stock.getCategoryName());
		System.out.println("category id"+categoryId);
		Category newCat = new Category();
		newCat.setId(categoryId);
		stk.setCategory(newCat);
		//to calculate category weightage
		calculateAndSetCategoryWeightages();
		
		stockRepo.save(stk);
		calculateAndSetWeightages();
		calculateAndSetWeightages(stk.getCategory().getId());
		return "New Stock added sucessfully";
	}
	//View All stock
	@Override
	public List<Stock> viewAllStock() {
		List<Stock> listOfStock = stockRepo.findAll();
		return listOfStock;
	}
	//Delete Stock
	@Override
	public String deleteStock(long id) {
		Optional<Stock> opt = stockRepo.findById(id);
		if(opt.isPresent()) {
			stockRepo.deleteById(id);
			calculateAndSetWeightages();
			calculateAndSetWeightages(opt.get().getCategory().getId());
			return "Deleted sucessfully";
		}else {
			return "Stock id not found";
		}
	}
	//View stock by id
	@Override
	public Stock viewStock(long id) {
		Optional<Stock> opt = stockRepo.findById(id);
		Stock stock= null;
		if(opt.isPresent()) {
			stock=opt.get();
			return stock;
		}else {
			return stock;
		}
	}
	
	public List<Stock> getStocksByCategoryId(Long categoryId) {
        return stockRepo.findByCategoryId(categoryId);
    }

    public void calculateAndSetWeightages(Long categoryId) {
        List<Stock> stocks = getStocksByCategoryId(categoryId);
        double totalCategoryValue = stocks.stream()
                .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                .sum();

        stocks.forEach(stock -> {
            double individualStockValue = stock.getPrice() * stock.getQuantity();
            double weightage = (individualStockValue / totalCategoryValue) * 100;
            stock.setCatWeightStock(weightage);
        });

        stockRepo.saveAll(stocks);
    }
    public void calculateAndSetWeightages() {
        List<Stock> stocks = viewAllStock();
        double totalStockValue = stocks.stream()
                .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                .sum();

        stocks.forEach(stock -> {
            double individualStockValue = stock.getPrice() * stock.getQuantity();
            double weightage = (individualStockValue / totalStockValue) * 100;
            stock.setWeightStock(weightage);
        });

        stockRepo.saveAll(stocks);
    }
    public void calculateAndSetCategoryWeightages() {
        List<Category> categories = catServiceImp.viewAllCategory();
        List<Stock> stockList = viewAllStock();
        categories.forEach(category -> {
            double totalStockValue = stockList.stream()
                    .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                    .sum();

            double weightCat = category.getStock().isEmpty() ? 0.0 :
                            category.getStock().stream()
                                    .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                                    .sum() / totalStockValue;

            category.setWeightCat(weightCat * 100); // Multiplying by 100 to get percentage
        });

        categoryRepo.saveAll(categories);
    }

    
}
