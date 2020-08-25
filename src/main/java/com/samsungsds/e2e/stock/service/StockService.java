package com.samsungsds.e2e.stock.service;

import com.samsungsds.e2e.stock.domain.Stock;

public interface StockService {
	Stock find(String productId);

	Stock register(Stock stock);

	Stock modify(Stock stock);

	void remove(String productId);
}
