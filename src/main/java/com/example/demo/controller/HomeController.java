package com.example.demo.controller;

import com.example.demo.model.DataVO;
import com.example.demo.model.OwnershipVO;
import com.example.demo.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController(value = "HomeController")
@RequestMapping(path = "/")
@Slf4j
public class HomeController {

    private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(path = "")
    public Map<String, String> home() {
        return this.homeService.getMessage();
    }

    @GetMapping("/test")
    public DataVO test() {
        return new DataVO(
                "id",
                new OwnershipVO("rolroralra", OwnershipVO.Permission.CREATE),
                System.currentTimeMillis()
        );
    }



}
