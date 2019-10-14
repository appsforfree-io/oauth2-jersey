package io.appsforfree.oauth2_jersey.domain;

import java.time.Instant;

public class AccessToken 
{
	private String accessToken;
	private String clientId;
	private Instant issuedAt;
	private Instant expiresOn;
	private String username;
	private String refreshToken;
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			Instant issuedAt,
			Instant expiresOn)
	{
		this.accessToken = accessToken;
		this.clientId = clientId;
		this.issuedAt = issuedAt;
		this.expiresOn = expiresOn;
	}
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			Instant issuedAt,
			Instant expiresOn, 
			String username)
	{
		this.accessToken = accessToken;
		this.clientId = clientId;
		this.issuedAt = issuedAt;
		this.expiresOn = expiresOn;
		this.username = username;
	}
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			Instant issuedAt,
			Instant expiresOn,
			String username, 
			String refreshToken)
	{
		this.accessToken = accessToken;
		this.clientId = clientId;
		this.issuedAt = issuedAt;
		this.expiresOn = expiresOn;
		this.username = username;
		this.refreshToken = refreshToken;
	}
	
	public String getAccessToken() { return accessToken; }
	
	public String getClientId() { return clientId; }
	
	public Instant getIssuedAt() { return issuedAt; }
	
	public Instant getExpiresOn() { return expiresOn; }
	
	public String getUsername() { return username; }
	
	public String getRefreshToken() { return refreshToken; }
}
