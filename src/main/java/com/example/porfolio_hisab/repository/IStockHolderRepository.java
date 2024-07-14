package com.example.porfolio_hisab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.porfolio_hisab.entity.Portfolio;
import com.example.porfolio_hisab.entity.StockHolder;

@Repository
public interface IStockHolderRepository extends JpaRepository<StockHolder, Long>{
	//find the stock holder by user id
	Optional<StockHolder> findByUserName(String username);
	//Find the user id of stock holder by stock id;
	@Query(value = "SELECT stock_holder.id FROM stock JOIN stock_holder ON stock.portfolio_stock_fk = stock_holder.user_porfolio_fk WHERE stock.id = :stockId", nativeQuery = true)
    Long findStockHolderIdByStockId(@Param("stockId") Long stockId);
	//find portfolio by user id
	@Query(value="SELECT portfolio.id FROM stock_holder JOIN portfolio ON stock_holder.user_porfolio_fk=portfolio.id where stock_holder.id=:userId", nativeQuery=true)
    long findPortfolioByUserId(@Param("userId") Long userId);
	

	
}
