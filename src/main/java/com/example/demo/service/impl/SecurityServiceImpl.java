package com.example.demo.service.impl;

import com.example.demo.service.SecurityService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    private static String secretKey;

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = Base64.getEncoder().encodeToString(
                    keyGenerator.generateKey().getEncoded()
            );

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createToken(String subject, long ttlMillis) {
        if (ttlMillis <= 0) {
            throw new RuntimeException("Expiry time must be greater than Zero :[" + ttlMillis + "]");
        }

        log.info("secretKey: {}", secretKey);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        long nowMillis = System.currentTimeMillis();
        builder.setExpiration(new Date(nowMillis + ttlMillis));

        return builder.compact();
    }

    @Override
    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    @Override
    public Map<String, Object> parseToken(String token) {
        Map<String, Object> map = new LinkedHashMap<>();

        Jws<Claims> jws;
        try {
            jws = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(token);

            map.put("header", jws.getHeader());
            map.put("body", jws.getBody());
            map.put("signature", jws.getSignature());
        } catch(ExpiredJwtException e) {
            log.error(e.getMessage());
        }

        return map;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(token);
            log.info("{}", jws);
            return true;
        } catch (Throwable e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }
}
