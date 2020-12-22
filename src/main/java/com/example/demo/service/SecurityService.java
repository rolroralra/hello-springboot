package com.example.demo.service;

import java.util.Map;

public interface SecurityService {
    String createToken(String subject, long ttlMillis);

    String getSubject(String token);

    Map<String, Object> parseToken(String token);
}
