package com.group5.firstpassport.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

  private static JwtUtil instance;

  private final long tokenExpirationMs;
  private final long refreshTokenExpirationMs;
  private final SecretKey signingKey;

  public JwtUtil(
          @Value("${jwt.secret}") String secretKey,
          @Value("${jwt.token-expiration-ms}") long tokenExpirationMs,
          @Value("${jwt.refresh-expiration-ms}") long refreshTokenExpirationMs
  ) {
    this.tokenExpirationMs = tokenExpirationMs;
    this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

    instance = this;
  }

  public static String generateToken(String username) {
    ensureInitialized();
    return instance.createToken(Map.of(), username, instance.tokenExpirationMs);
  }

  public static String generateRefreshToken(String username) {
    ensureInitialized();
    return instance.createToken(Map.of(), username, instance.refreshTokenExpirationMs);
  }

  public static String extractUsername(String token) {
    ensureInitialized();
    return instance.extractClaim(token, Claims::getSubject);
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    ensureInitialized();
    try {
      String username = instance.extractUsername(token);
      return username.equals(userDetails.getUsername()) && !instance.isTokenExpired(token);
    } catch (JwtException e) {
      return false;
    }
  }

  private static void ensureInitialized() {
    if (instance == null) {
      throw new IllegalStateException("JwtUtil has not been initialized by Spring yet.");
    }
  }

  private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + expirationTime);

    return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(signingKey, Jwts.SIG.HS384)
            .compact();
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parser()
              .verifyWith(signingKey)
              .build()
              .parseSignedClaims(token)
              .getPayload();
    } catch (ExpiredJwtException e) {
      throw new JwtException("Token has expired", e);
    } catch (UnsupportedJwtException e) {
      throw new JwtException("Unsupported JWT token", e);
    } catch (MalformedJwtException e) {
      throw new JwtException("Malformed JWT token", e);
    } catch (SecurityException e) {
      throw new JwtException("Invalid JWT signature", e);
    } catch (IllegalArgumentException e) {
      throw new JwtException("Token is empty or null", e);
    }
  }

  private <T> T extractClaim(String token, Function<Claims, T> resolver) {
    final Claims claims = extractAllClaims(token);
    return resolver.apply(claims);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}