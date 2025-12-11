package com.gibesystems.ecommerce.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.val;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String generateToken(UserDetails userDetails, String fullName, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", fullName);
        claims.put("email", email);
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", java.util.UUID.randomUUID().toString()); // unique ID
        claims.put("type", "refresh");
        return buildToken(claims, userDetails, refreshTokenExpiration);
        // return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    private String buildToken(Map<String, Object> claims,
            UserDetails userDetails,
            long jwtExpiration) {
        /*
         * var authorities = userDetails.getAuthorities().stream()
         * .map(GrantedAuthority::getAuthority)
         * .toList();
         */
        var authorities = userDetails.getAuthorities().stream()
                .map(auth -> {
                    if (auth.getAuthority().startsWith("ROLE_"))
                        return auth.getAuthority();
                    return "ROLE_" + auth.getAuthority();
                })
                .toList();

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace(); // Logs the exact cause
            throw e; // Re-throw if you want calling code to handle it
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Validate token (checks signature and expiration)
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
          //  e.printStackTrace(); // Add this for debugging
            return false;
        }
    }

    // Extract user id from token (assumes subject is user id/email)
    public String getUserIdFromToken(String token) {
        return extractUsername(token);
    }

}