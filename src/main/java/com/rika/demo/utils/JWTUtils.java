package com.rika.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 */
@Component
public class JWTUtils {

    // 密钥和过期时间, 从配置中读取注入
    private static String SECRET_KEY;
     private static long expiration;

    @Value("${Jwt.token.secret-key}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Value("${Jwt.token.expiration}")
    public void setExpiration(long exp) {
        expiration = exp;
    }

    public static String createToken(String uuid, Map<String, Object> claims) {
        return JWT
                .create()
                .withSubject(uuid)
                .withPayload(claims)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static String getUUID(String token) {
        try {
            return JWT
                    .require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Claim> getClaim(String token) {
        try {
            return JWT
                    .require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getClaims();
        } catch (Exception e) {
            return null;
        }
    }

}
