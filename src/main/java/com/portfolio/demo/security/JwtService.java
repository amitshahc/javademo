package com.portfolio.demo.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// Secret key used to sign and verify JWT
	private static final String SECRET_KEY = "my-very-secret-key-for-signing-jwt-12345678"; // should be 256 bits

	// Expiration in milliseconds (1 day)
	private static final long EXPIRATION_TIME = 86400000;

	private Key getSigningKey() {
		// Generates HMAC-SHA key
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	// Generate a token for username
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username) // subject = username
				.setIssuedAt(new Date()) // when issued
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // expiry
				.signWith(getSigningKey()) // sign with key
				.compact(); // build token
	}

	// Extract username (subject) from token
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	// Validate token
	public boolean isTokenValid(String token, String username) {
		return username.equals(extractUsername(token)) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
				.getExpiration();

		return expiration.before(new Date());
	}
}
