package com.example.demo.aspect;

import ch.qos.logback.core.LogbackException;
import com.example.demo.annotation.TokenRequired;
import com.example.demo.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class SecurityAspect {
    SecurityService securityService;

    @Autowired
    public SecurityAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Before(value = "@annotation(tokenRequired)")
    public void tokenRequiredWithAnnotation(TokenRequired tokenRequired) {
        log.info("Before {}", tokenRequired);

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // Check for JWT Token in request header
//        String tokenInHeader = request.getHeader("token");
        if (request.getCookies() == null || request.getCookies().length == 0) {
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
    }

}
