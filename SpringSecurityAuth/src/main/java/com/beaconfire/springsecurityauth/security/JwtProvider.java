package com.beaconfire.springsecurityauth.security;

import com.beaconfire.springsecurityauth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtProvider {
    private static final SecretKey secretKey = Keys.hmacShaKeyFor("JavaTrainingJavaTrainingJavaTraining".getBytes(StandardCharsets.UTF_8));
    @Value("${security.jwt.token.key}")
    private String key;

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername()); // user identifier
        claims.put("userId", user.getUserId()); // Optional
        claims.put("role", user.getRole());     // 0 = USER, 1 = ADMIN

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
