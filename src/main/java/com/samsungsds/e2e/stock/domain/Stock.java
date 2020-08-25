package com.samsungsds.e2e.stock.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "STOCK")
@Data
public class Stock {

	@Id
	private String productId;
	private int amount;
}
