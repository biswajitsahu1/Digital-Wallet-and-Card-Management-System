package com.bank.user.user_service.util;

import com.bank.user.user_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey; // Base64-encoded string, at least 512 bits when decoded

    @Value("${jwt.expiration-ms:86400000}")
    private long expirationMs; // Default to 1 day (86400000 ms) if not specified


    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey));
    }

    /**
     * Generate a JWT token from a given user.
     *
     * @param user the user to generate a token for
     * @return a JWT token containing the user's username, valid for {@code expirationMs} milliseconds
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Returns the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    /**
     * Validate a given token.
     *
     * @param token the token to validate
     * @return true if the token is valid, false otherwise
     */
  public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}