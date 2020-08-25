package com.samsungsds.e2e.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsungsds.e2e.stock.domain.Stock;
import com.samsungsds.e2e.stock.repository.StockRepository;
import com.samsungsds.e2e.stock.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
    private StockRepository stockRepository;
  
    @Override
    public Stock find(String productId) {
        return stockRepository.findById(productId).orElse(null);
    }
  
    @Override
    public Stock register(Stock stock) {
        Stock result = find(stock.getProductId());
         
        if(result == null) {
            return stockRepository.save(stock);
        }
  
        return null;
    }
  
    @Override
    public Stock modify(Stock stock) {
        Stock result = find(stock.getProductId());
  
        if(result == null) {
            return null;
        }
  
        return stockRepository.save(stock);
    }
  
    @Override
    public void remove(String productId) {
        stockRepository.deleteById(productId);
    }

}
