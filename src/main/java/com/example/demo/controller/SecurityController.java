package com.example.demo.controller;

import com.example.demo.annotation.TokenGenerated;
import com.example.demo.annotation.TokenRequired;
import com.example.demo.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/security")
@Slf4j
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @TokenGenerated
    @GetMapping(path = "/generate/token")
    public Map<String, Object> generateToken(@RequestParam String subject, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = securityService.createToken(subject);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", token);


        Cookie cookie = new Cookie("token", token);
        cookie.setDomain(httpServletRequest.getServerName());
        cookie.setMaxAge(20 * 1000 * 60);
        cookie.setPath("/");

        httpServletResponse.addCookie(cookie);
        httpServletResponse.setHeader("token", token);
        return map;
    }

    @TokenRequired
    @GetMapping(path = {"/get/subject"})
    public Map<String, Object> getSubject(HttpServletRequest httpServletRequest/*@RequestParam String token*/) throws Exception {
        String token = Arrays.stream(httpServletRequest.getCookies())
                .filter(c -> c.getName().equalsIgnoreCase("token"))
                .findFirst()
                .orElseThrow(Exception::new)
                .getValue();

        log.info("token : {}", token);

        String subject = securityService.getSubject(token);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", subject);

        return map;
    }

    @TokenRequired
    @GetMapping(path = {"/parse/token"})
    public Map<String, Object> parseToken(/*@RequestParam String token,*/ HttpServletRequest httpServletRequest) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        String token = Arrays.stream(httpServletRequest.getCookies())
                .filter(c -> c.getName().equalsIgnoreCase("token"))
                .findFirst()
                .orElseThrow(Exception::new)
                .getValue();

        log.info("token : {}", token);

        map.put("result", securityService.parseToken(token));

        return map;
    }
}
