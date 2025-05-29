package com.caw24g.grupp_uppgift.securityTests;

import com.caw24g.grupp_uppgift.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {
    private JwtUtil jwtUtil;
    // Skapa en instans av JwtUtil innan varje test körs
    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    //Testar lyckad token-generering och validering
    @Test
    public void generateTokenTest() {

        // Skapar en e-postadress för testet
        String email = "max@cloud.se";

        // Genererar en token baserat på e-postadressen
        String token = jwtUtil.generateToken(email);
        String extractedEmail = jwtUtil.getEmailFromToken(token);

        // Verifierar att den hämtade e-postadressen matchar den ursprungliga
        assertEquals(email, extractedEmail);
    }

    //Testar att token valideras korrekt
    @Test
    public void validateTokenTest() {

        // Skapar en e-postadress för testet
        String email = "tony@cloud.se";

        // Genererar en token baserat på e-postadressen
        String token = jwtUtil.generateToken(email);
        String result = jwtUtil.validateToken(token);

        // Verifierar att den hämtade e-postadressen matchar den ursprungliga
        assertEquals(email, result);
    }
    //Testar att ogiltiga tokens kastar ett undantag
    @Test
    public void validateTokenFailTest() {

        // Skapar en ogiltig token för testet
        String invalidToken = "invalid.token";

        // Förväntar oss att ett undantag kastas när vi försöker validera den ogiltiga token
        Exception exception = assertThrows(RuntimeException.class, () -> jwtUtil.validateToken(invalidToken));

        // Verifierar att undantaget innehåller rätt felmeddelande
        assertTrue(exception.getMessage().contains("Ogiltig token"));
    }
    // Testar att token inte är utgången
    @Test
    public void tokenNotExpired() {

        // Skapar en JWTUtil-instans
        String token = jwtUtil.generateToken("linus@cloud.se");

        // Verifierar att token inte är utgången
        assertFalse(jwtUtil.isTokenExpired(token));
    }
    // Testar att token är utgången
    @Test
    public void tokenExpiredTest() throws InterruptedException {

        // Skapar en kortlivad hemlig nyckel för testet
        SecretKey shortLivedKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String email = "expired@example.com";
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 100);
        // Genererar en token som går ut snabbt
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("email", email)
                .signWith(shortLivedKey)
                .compact();

        Thread.sleep(150);

        Thread.sleep(200);

        // verifierar att token är utgången
        assertThrows(ExpiredJwtException.class, () -> {
            Jwts.parserBuilder()
                    .setSigningKey(shortLivedKey)
                    .build()
                    .parseClaimsJws(token);
        });
    }
    // Testar att ogiltiga tokens returnerar null
    @Test
    public void invalidTokenTest() {

        // Skapar en ogiltig token för testet
        String invalidToken = "invalid.token.value";

        // Försöker hämta e-post från den ogiltiga token
        String result = jwtUtil.getEmailFromToken(invalidToken);

        // Verifierar att resultatet är null eftersom token är ogiltig
        assertNull(result);
    }
    // Testar att expirationTime returnerar korrekt värde
    @Test
    public void expirationTimeTest() {

        long expected = 24 * 60 * 60 * 1000;
        
        assertEquals(expected, jwtUtil.getExpirationTime());
    }
}

