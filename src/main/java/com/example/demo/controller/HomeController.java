package com.example.demo.controller;

import com.example.demo.service.HomeService;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@RestController(value = "HomeController")
@RequestMapping(path = "/")
public class HomeController {

    private HomeService homeService;
    private SecurityService securityService;

    public HomeController(HomeService homeService, SecurityService securityService) {
        this.homeService = homeService;
        this.securityService = securityService;
    }

    @GetMapping(path = "")
    public Map<String, String> home() {
        return this.homeService.getMessage();
    }

    @GetMapping(path = "/security/generate/token")
    public Map<String, Object> generateToken(@RequestParam() String subject) {
        String token = securityService.createToken(subject, (2 * 1000 * 60));
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", token);
        return map;
    }

    @GetMapping(path = {"/security/get/subject"})
    public Map<String, Object> getSubject(@RequestParam String token) {
        String subject = securityService.getSubject(token);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", subject);
        return map;
    }

    @GetMapping(path = {"/security/parse/token"})
    public Map<String, Object> parseToken(@RequestParam String token) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", securityService.parseToken(token));
        return map;
    }

}
