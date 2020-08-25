package com.samsungsds.e2e.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.samsungsds.e2e.stock.domain.Stock;

public interface StockRepository extends CrudRepository<Stock, String>{
	
	// proxy instance
	
	// persistence context
	
	// ORM relation : oneToOne, oneToMany, manyToMany
	// Optimization: Lazy, Eager
	
	// QueryMethod
	List<Stock> findByAmount(int amount);
	
	// Native Query
	@Query(value="SELECT * FROM stock WHERE product_id=?1", nativeQuery=true)
    Stock getStockUsingSql(String productId);
     
    @Query(value="SELECT * FROM stock WHERE product_id=:productId", nativeQuery=true)
    Stock getStockUsingSqlWithNamedParam(@Param("productId") String productId);
 
    @Query(value="SELECT s FROM Stock s WHERE productId=?1", nativeQuery=false)
    Stock getStockUsingJpql(String productId);
}
