package com.superdupermart.shopping_app.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtProvider {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("JavaTrainingJavaTrainingJavaTraining".getBytes(StandardCharsets.UTF_8));

    public Optional<AuthUserDetail> resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // remove "Bearer "

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Integer roleInt = claims.get("role", Integer.class);
            String role = (roleInt == 1) ? "ADMIN" : "USER";

            return Optional.of(new AuthUserDetail(
                    username,
                    null, // password
                    true,  // accountNonExpired
                    true,  // accountNonLocked
                    true,  // credentialsNonExpired
                    true,  // enabled
                    Collections.singletonList(new SimpleGrantedAuthority(role))
            ));

        }

        return Optional.empty();
    }


    public String extractUserFromRequest(HttpServletRequest request) {
        return resolveToken(request)
                .map(AuthUserDetail::getUsername)  // in your logic, username is the email
                .orElseThrow(() -> new RuntimeException("Invalid or missing JWT token"));
    }





}
