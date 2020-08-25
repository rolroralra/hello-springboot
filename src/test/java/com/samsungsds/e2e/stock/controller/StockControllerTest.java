package com.samsungsds.e2e.stock.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.samsungsds.e2e.stock.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {

	@Autowired
    MockMvc mockMvc;
	
	@MockBean
	StockService stockSErvice;
	
	@Test
	public void test() throws Exception {

//		Stock newEmployee = new Stock();
//		newEmployee.setEmpNo("1");
//		newEmployee.setEmpName("mock");
//		newEmployee.setSal(10000);
//		when(stockSErvice.findById("1")).thenReturn(newEmployee );
//
//		
//		mockMvc.perform(get("/api/employees/result/1"))
//			.andExpect(status().isOk())
//            .andExpect(jsonPath("$.result.empNo").value("1"))
//			;
	}

}
