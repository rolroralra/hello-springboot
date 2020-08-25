package com.samsungsds.e2e.stock.domain;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Result<T> extends ResourceSupport {
	private int errorCode = 200;
	private String errorMessage = "Success";
	private T result;
}
