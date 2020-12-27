package com.example.demo.interceptor;

import com.example.demo.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

@Component
@Slf4j
public class SecurityInterceptor implements AsyncHandlerInterceptor {

    SecurityService securityService;

    @Autowired
    public SecurityInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Check for JWT Token in request header
//        String tokenInHeader = request.getHeader("token");

        if (Objects.isEmpty(request.getCookies())) {
            throw new IllegalArgumentException("Empty Cookie");
        }

        // Check for JWT Token in request cookie
        String token = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equalsIgnoreCase("token"))
                .findFirst()
                .orElseThrow()
                .getValue();

        if (Strings.isEmpty(token)) {
            throw new IllegalArgumentException("Empty Token");
        }

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(securityService.getSecretKey()))
                .parseClaimsJws(token)
                .getBody();

        log.info("{}", claims);

        if (claims == null || claims.getSubject() == null) {
            throw new IllegalArgumentException("Token Error : Claim is null");
        }

        return true;
    }
}
