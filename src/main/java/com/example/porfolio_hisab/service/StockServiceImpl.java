package com.example.porfolio_hisab.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.porfolio_hisab.dto.BuySellStock;
import com.example.porfolio_hisab.dto.StockInputDto;
import com.example.porfolio_hisab.entity.Category;
import com.example.porfolio_hisab.entity.Portfolio;
import com.example.porfolio_hisab.entity.Stock;
import com.example.porfolio_hisab.entity.StockHolder;
import com.example.porfolio_hisab.exceptions.CategoryNotFoundException;
import com.example.porfolio_hisab.exceptions.DuplicateStockFound;
import com.example.porfolio_hisab.exceptions.InsufficentBalanceException;
import com.example.porfolio_hisab.exceptions.InsufficentStockQuantity;
import com.example.porfolio_hisab.exceptions.StockNotFoundException;
import com.example.porfolio_hisab.exceptions.UserNotFoundException;
import com.example.porfolio_hisab.repository.ICategoryRepository;
import com.example.porfolio_hisab.repository.IPortfolioRepository;
import com.example.porfolio_hisab.repository.IStockHolderRepository;
import com.example.porfolio_hisab.repository.IStockRepository;
@Service
public class StockServiceImpl implements IStockServiceImpl {
	
	@Autowired
	CategoryServiceImpl catServiceImp;
	@Autowired
	IStockRepository stockRepo;
	@Autowired
	ICategoryRepository categoryRepo;
	@Autowired
	IStockHolderRepository stockHolderRepo;
	@Autowired
	IPortfolioRepository stockPortfolioRepo;
	//Add new stock
	@Override
	public String addStock(long userId, StockInputDto stock) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userId);
		if(opt.isPresent()) {
			StockHolder user = opt.get();
			Portfolio port=user.getPortFolio();
			List<Stock> listStock = port.getStock();
			List<String> stockNames = listStock.stream().map(Stock::getStockName).collect(Collectors.toList());
			if(stockNames.contains(stock.getStockName())) {
				throw new DuplicateStockFound("Stock already Exists for the user");
			}
			List<Category> catList =port.getCategory();
//			long categoryId=categoryRepo.getIdByName(stock.getCategoryName());
			List<String> categoryNames = catList.stream().map(Category::getCategoryName).collect(Collectors.toList());
			if(categoryNames.contains(stock.getCategoryName())) {
				
				Stock stk = new Stock();
				stk.setStockName(stock.getStockName());
				stk.setPrice(stock.getPrice());
				stk.setQuantity(stock.getQuantity());
				stk.setDescription(stock.getDescription());
				
				Optional<Category> optCat = catList.stream().filter(cat->cat.getCategoryName().equals(stock.getCategoryName())).findFirst();
				Category newCat = optCat.get();
				stk.setCategory(newCat);				
				listStock.add(stk);				
				port.setStock(listStock);
				port.setStockCount(port.getStockCount()+1);
				port.setTotal(port.getTotal()+(stock.getPrice()*stock.getQuantity()));
				if(port.getBalance()>=(stock.getPrice()*stock.getQuantity())) {
					port.setBalance(port.getBalance()-(stock.getPrice()*stock.getQuantity()));
				}else {
					throw new InsufficentBalanceException("Insufficent Balance, please add more funds");
				}
				user.setPortFolio(port);
				stockHolderRepo.save(user);
				//to calculate category weightage
				calculateAndSetCategoryWeightages(userId);
				calculateAndSetWeightages(userId);
				calculateAndSetWeightagesForCategory(stk.getCategory().getId());
				return "New Stock added sucessfully";
				
			}else {
				throw new CategoryNotFoundException("Category doesnot exist for this user");
			}
			
			
		}else {
			throw new UserNotFoundException("Invalid User");
		}
		
	}
	//View All stock
	@Override
	public List<Stock> viewAllStock(long userId) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userId);
		if(opt.isPresent()) {
			List<Stock> listOfStock =opt.get().getPortFolio().getStock();
			return listOfStock;
		}else {
			throw new UserNotFoundException("Invalid User");
		}
		
	}
	//Delete Stock
	@Override
	public String deleteStock(long userId, long id) {
		Optional<StockHolder> opt = stockHolderRepo.findById(userId);
		Optional<Stock> optStk = stockRepo.findById(id);
		if(!opt.isPresent()) {
			throw new UserNotFoundException("Invalid User");
		}
		else if(!optStk.isPresent()) {
			throw new StockNotFoundException("Stock is not found");
		}else {
			StockHolder user = opt.get();
			Portfolio port = user.getPortFolio();
			Stock stk = stockRepo.findById(id).get();
			List<Stock> listOfStock =port.getStock();
			if(listOfStock.contains(stk)) {
				Category cat = (stockRepo.findById(id).get()).getCategory();
				listOfStock.remove(stk);
				port.setStock(listOfStock);
				user.setPortFolio(port);
				port.setBalance(port.getBalance()+(stk.getPrice()*stk.getQuantity()));
				stockRepo.deleteById(id);
				calculateAndSetCategoryWeightages(userId);
				calculateAndSetWeightages(userId);
				calculateAndSetWeightagesForCategory(cat.getId());
				return "Deleted sucessfully";
			}else {
				throw new StockNotFoundException("Stock is not present in your Portfolio");
			}
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
			throw new StockNotFoundException("invalid stock id");
		}
	}
	
	public List<Stock> getStocksByCategoryId(Long categoryId) {
        return stockRepo.findByCategoryId(categoryId);
    }

    public void calculateAndSetWeightagesForCategory(long categoryId) {
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
    public void calculateAndSetWeightages(long userId) {
        List<Stock> stocks = viewAllStock(userId);
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
    public void calculateAndSetCategoryWeightages(long userId) {
        List<Category> categories = catServiceImp.viewAllCategory(userId);
        List<Stock> stockList = viewAllStock(userId);
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
	@Override
	public Stock buyStock(long id, BuySellStock buysellPriceQuantity) {
		Optional<Stock> opt = stockRepo.findById(id);
		Stock stock= null;
		if(opt.isPresent()) {
			stock=opt.get();
			long userId = stockHolderRepo.findStockHolderIdByStockId(id);
			long portId = stockHolderRepo.findPortfolioByUserId(userId);
			Portfolio port = stockPortfolioRepo.findById(portId).get();
			if(port.getBalance()>=(buysellPriceQuantity.getPrice()*buysellPriceQuantity.getQuantity())) {
				port.setBalance(port.getBalance()-(buysellPriceQuantity.getPrice()*buysellPriceQuantity.getQuantity()));
				stockPortfolioRepo.save(port);
			}else {
				throw new InsufficentBalanceException("Insufficent Balance, please add more funds");
			}
			double newAveragePrice= ((stock.getPrice()*stock.getQuantity())+(buysellPriceQuantity.getPrice()*buysellPriceQuantity.getQuantity()))/(stock.getQuantity()+buysellPriceQuantity.getQuantity());
			stock.setPrice(newAveragePrice);
			stock.setQuantity(stock.getQuantity()+buysellPriceQuantity.getQuantity());
			stockRepo.save(stock);
			
			
			calculateAndSetCategoryWeightages(userId);
			calculateAndSetWeightages(userId);
			calculateAndSetWeightagesForCategory(stock.getCategory().getId());
			return stock;
		}else {
			throw new StockNotFoundException("Stock is not bought");
		}
	}
	@Override
	public Stock sellStock(long id, BuySellStock buysellPriceQuantity) {
		Optional<Stock> opt = stockRepo.findById(id);
		Stock stock= null;
		if(opt.isPresent()) {
			stock=opt.get();
			double qty =stock.getQuantity();
			if(stock.getQuantity()>=buysellPriceQuantity.getQuantity()) {
				long userId = stockHolderRepo.findStockHolderIdByStockId(id);
				long portId = stockHolderRepo.findPortfolioByUserId(userId);
				Portfolio port = stockPortfolioRepo.findById(portId).get();
				port.setBalance(port.getBalance()+(buysellPriceQuantity.getPrice()*buysellPriceQuantity.getQuantity()));
				stockPortfolioRepo.save(port);
				stock.setQuantity(stock.getQuantity()-buysellPriceQuantity.getQuantity());
				stockRepo.save(stock);
				
				
				calculateAndSetCategoryWeightages(userId);
				calculateAndSetWeightages(userId);
				calculateAndSetWeightagesForCategory(stock.getCategory().getId());
				return stock;
			}else {
				throw new InsufficentStockQuantity("You have only "+qty+" stock in you portfolio");
			}
			
		}else {
			throw new StockNotFoundException("Stock is not bought");
		}
	}

    
}
