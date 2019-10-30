package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.domain.AccessToken;
import io.appsforfree.oauth2_jersey.domain.RefreshToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;
import java.time.ZonedDateTime;

public class TokenHelper 
{
	public static AccessToken createAccessToken(
			String clientId, 
			String username,
			String refreshToken) 
	{ 
		Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode("77tPG4G6fLDfMHWS9MYVIYixw1t4IWmVI5W8CPp3BVU="));
		Instant issuedAt = Instant.now();
		Instant expiresOn = issuedAt.plusSeconds(3600);
		Map<String, Object> body = new HashMap<>();
		if (username != null)
			body.put("username", username);
		body.put("client_id", clientId);
		String jws = Jwts
				.builder()
				.setIssuedAt(Date.from(issuedAt))
				.setExpiration(Date.from(expiresOn))
				.addClaims(body)
				.signWith(key).compact();
		return new AccessToken(
				jws, 
				clientId, 
				issuedAt, 
				expiresOn, 
				username, 
				refreshToken); 
	}
	
	public static RefreshToken createRefreshToken(
			String clientId, 
			String username) 
	{
		ZonedDateTime issuedAt = ZonedDateTime.now();
		ZonedDateTime expiresOn = issuedAt.plusHours(1);
		return new RefreshToken(
				StringUtils.random(40), 
				clientId, 
				issuedAt.toInstant(), 
				expiresOn.toInstant(), 
				username); 
	}
}
