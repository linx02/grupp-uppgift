package com.caw24g.grupp_uppgift.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 86400000; // 24h i millisekunder hur länge ett token är giltigt


    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME); // När token går ut nu + 24h

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("email", email)
                .signWith(SECRET_KEY)
                .compact();

    }

    public String validateToken(String token) {
        try {
            // Parsa och validera token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Returnera användarens email från token
            return claims.getSubject();
        } catch (Exception e) {
            // Token är ogiltig
            throw new RuntimeException("Ogiltig token: " + e.getMessage());
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            // Parsa token för att få claims
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());

        } catch (Exception e) {
            return true;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();

        } catch (Exception e) {
            return null;
        }
    }

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }
}
