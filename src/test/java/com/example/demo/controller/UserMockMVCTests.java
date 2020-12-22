package com.example.demo.controller;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMockMVCTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpBeforeClass() {

    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @After
    public void tearDown() {

    }

    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Test
    public void testBasicMVC() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("Hello SpringBoot!"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testSingleUser() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/users/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(1001))
                .andExpect(jsonPath("userName").value("rolroralra"))
                .andReturn();

        result.getResponse().getHeaderNames().forEach(name -> System.out.println(result.getResponse().getHeader(name)));
        System.out.println(result.getResponse().getContentAsString());
    }


}
