package com.lu.mallbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {
    // 密钥（长度必须大于等于 256 位，即 32 个字符）
    private static final String SECRET = "MyShoppingSystemSecretKeyForJWT2025";
    // 过期时间：24 小时
    private static final long EXPIRATION = 24 * 60 * 60 * 1000;

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 生成 Token
    public static String generateToken(Long userId, String username) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析 Token (如果 Token 伪造或过期，这里会抛出异常)
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}