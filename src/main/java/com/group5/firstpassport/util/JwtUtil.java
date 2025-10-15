package com.group5.firstpassport.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
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

  private static String SECRET_KEY;
  private static long TOKEN_EXPIRATION;
  private static long REFRESH_TOKEN_EXPIRATION;

  @Value("${jwt.secret}")
  public void setSecretKey(String secretKey) {
    JwtUtil.SECRET_KEY = secretKey;
  }

  @Value("${jwt.token-expiration-ms}")
  public void setTokenExpiration(long tokenExpiration) {
    JwtUtil.TOKEN_EXPIRATION = tokenExpiration;
  }

  @Value("${jwt.refresh-expiration-ms}")
  public void setRefreshTokenExpiration(long refreshTokenExpiration) {
    JwtUtil.REFRESH_TOKEN_EXPIRATION = refreshTokenExpiration;
  }

  public static String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username, TOKEN_EXPIRATION);
  }

  public static String generateRefreshToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username, REFRESH_TOKEN_EXPIRATION);
  }

  private static String createToken(Map<String, Object> claims, String subject, long expirationTime) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSignKey(), SignatureAlgorithm.HS384)
            .compact();
  }

  private static Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public static String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public static Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private static Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
    return Jwts.parser()
            .verifyWith((SecretKey) getSignKey())   // thay cho setSigningKey
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  private static boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
