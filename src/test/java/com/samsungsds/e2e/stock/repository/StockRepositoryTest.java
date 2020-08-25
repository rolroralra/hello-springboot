package com.samsungsds.e2e.stock.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samsungsds.e2e.stock.domain.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StockRepositoryTest {
	@Resource
    StockRepository stockRepository;
	
	@BeforeClass
	public static void setUpEnvironment() {
	    System.out.println("BeforeClass");
	}
	@Before
	public void setUp() {
	    System.out.println("- Before");
	}

	@After
	public void tearDown() {
	    System.out.println("- After");
	}
	@AfterClass
	public static void tearDownEnvironment() {
	    System.out.println("AfterClass");
	}
	
	@Test
    public void save() {
         
        Stock newStock = new Stock();
        newStock.setProductId("1");
        newStock.setAmount(10);
         
        Stock stock = stockRepository.save(newStock);
         
        assertEquals(newStock, stock);
    }
 
    @Test
    public void findById() {
        Stock stock = stockRepository.findById("1").orElse(null);
         
        assertEquals(10, stock.getAmount());
    }
     
    @Test
    public void findByAmount() {
//        Stock stock = stockRepository.findByAmount(10);
    	List<Stock> list = stockRepository.findByAmount(10);
    	
    	assertEquals(1L, (long) list.size());
    	assertEquals("1", list.get(0).getProductId());
         
//        assertEquals("1", stock.getProductId());
    }
     
    @Test
    public void getStockUsingSql() {
        Stock stock = stockRepository.getStockUsingSql("1");
         
        assertEquals(10, stock.getAmount());
    }
 
    @Test
    public void getStockUsingSqlWithNamedParam() {
        Stock stock = stockRepository.getStockUsingSqlWithNamedParam("1");
         
        assertEquals(10, stock.getAmount());
    }
     
    @Test
    public void getStockUsingJpql() {
        Stock stock = stockRepository.getStockUsingJpql("1");
         
        assertEquals(10, stock.getAmount());
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
