package com.samsungsds.e2e.stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
	
	@GetMapping("/")
	public String index() {
		LOGGER.trace("trace Hello");
		LOGGER.info("info Hello");
		LOGGER.debug("debug Hello");
		LOGGER.error("error Hello");
		LOGGER.warn("warn Hello");
		
		return "Hello World!";
	}
	
}
