package org.mainfest.devSquare.DevSqaure.utils;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private String SECRET_KEY="TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(username,claims);
    }

    public String createToken(String subject, Map<String,Object> claims){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT").and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +1000 *60*60))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUserName(String jwt) {
        Claims claims = extractAllClaims(jwt);
        return claims.getSubject();
    }

    public Claims extractAllClaims(String token){
        return  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isExpired(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date()); // return true if expiration date is before current Date
    }

    // username matches and date of token is not expired
    public boolean validateToken(String jwt, String userName) {
        String extractedUserName = extractUserName(jwt);
        return extractedUserName.equals(userName) && !isExpired(jwt);
    }
}
