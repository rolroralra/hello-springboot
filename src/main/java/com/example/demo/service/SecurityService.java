package com.example.demo.service;

import java.util.Map;

public interface SecurityService {
    String createToken(String subject);

    String createToken(String subject, long ttlMillis);

    String getSubject(String token);

    Map<String, Object> parseToken(String token);

    boolean validateToken(String token);

    String getSecretKey();

}
