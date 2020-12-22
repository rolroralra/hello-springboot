package com.example.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityServiceImplTests {

    @Autowired
    SecurityService securityService;

    @Test
    public void test() {
        String token = securityService.createToken("test", 2*1000*60);

        assertNotNull(token);
    }
}
