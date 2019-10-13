package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.domain.AccessToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.ZonedDateTime;

public class TokenHelper 
{
	public static AccessToken createAccessToken(
			String clientId, 
			String username) 
	{ 
		Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode("77tPG4G6fLDfMHWS9MYVIYixw1t4IWmVI5W8CPp3BVU="));
		ZonedDateTime issuedAtDateTime = ZonedDateTime.now();
		ZonedDateTime expiresOnDateTime = issuedAtDateTime.plusHours(1);
		Date issuedAt = Date.from(issuedAtDateTime.toInstant());
		Date expiresOn = Date.from(expiresOnDateTime.toInstant());
		Map<String, Object> body = new HashMap<>();
		body.put("username", username);
		body.put("client_id", clientId);
		String jws = Jwts
				.builder()
				.setIssuedAt(issuedAt)
				.setExpiration(expiresOn)
				.addClaims(body)
				.signWith(key).compact();
		return new AccessToken(jws, clientId, issuedAt, expiresOn, username); 
	}
}
