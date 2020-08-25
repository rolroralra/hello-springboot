package com.samsungsds.e2e.stock.controller;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsungsds.e2e.stock.domain.Result;
import com.samsungsds.e2e.stock.domain.Stock;
import com.samsungsds.e2e.stock.service.StockService;


@RestController
@CrossOrigin()
@RequestMapping("/api/v1/stock")
public class StockController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockService stockService;
	
	@GetMapping("/alt1/{productId}")
	public ResponseEntity<Result<Stock>> findWithResponseEntity(@PathVariable String productId) {
		Result<Stock> result = new Result<Stock>();
		result.add(linkTo(methodOn(StockController.class).find(productId)).withRel("find"));
		result.add(linkTo(methodOn(StockController.class).register(result.getResult())).withRel("register"));
		result.add(linkTo(methodOn(StockController.class).modify(result.getResult())).withRel("modify"));
		result.add(linkTo(methodOn(StockController.class).remove(productId)).withRel("remove"));
		return new ResponseEntity<Result<Stock>>(result, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{productId}")
	public Result<Stock> find(@PathVariable String productId) {
		LOGGER.info("find {}", productId);
		
		Result<Stock> result = new Result<>();
		 
        Stock stock = stockService.find(productId);
        if (stock == null) {
            result.setErrorCode(HttpStatus.NOT_FOUND.value());
            result.setErrorMessage("Not found.");
        } else {
            result.setResult(stock);
        }
 
        return result;
	}
	
	@PostMapping()
	public Result<String> register(@RequestBody Stock newStock) {
		LOGGER.info("register {}", newStock);
		
		Result<String> result = new Result<>();
		 
        Stock resultStock = stockService.register(newStock);
        if (resultStock == null) {
            result.setErrorCode(HttpStatus.BAD_REQUEST.value());
            result.setErrorMessage("Already Exists.");
        } else {
            result.setResult(resultStock.getProductId());
        }
 
        return result;
	}
	
	@PutMapping()
	public Result<String> modify(@RequestBody Stock newStock) {
		LOGGER.info("modify {}", newStock);
		
		Result<String> result = new Result<>();
		 
        Stock resultStock = stockService.modify(newStock);
        if (resultStock == null) {
            result.setErrorCode(HttpStatus.NOT_FOUND.value());
            result.setErrorMessage("Not found.");
        } else {
            result.setResult(resultStock.getProductId());
        }
 
        return result;
	}
	

	@DeleteMapping("/{productId}")
	public Result<Stock> remove(@PathVariable String productId) {
		LOGGER.info("remove {}", productId);
		
		stockService.remove(productId);
        return new Result<Stock>();
	}
}
