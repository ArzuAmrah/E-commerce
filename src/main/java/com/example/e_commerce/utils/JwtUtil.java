package com.example.e_commerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468570";

    // İstifadəçi adını çıxarmaq üçün metod
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Tokenin müddətini çıxarmaq üçün metod
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Xüsusi iddia çıxarmaq üçün metod
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Bütün iddiaları çıxarmaq üçün metod
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignKey()) // İmzalama açarı
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Tokenin müddətinin bitdiyini yoxlayan metod
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Tokenin etibarlılığını yoxlayan metod
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Yeni token yaratmaq üçün metod
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Token yaratmaq üçün köməkçi metod
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 dəqiqəlik müddət
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // HS256 ilə imzalanma
                .compact();
    }

    // İmzalama açarını qaytaran metod
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
