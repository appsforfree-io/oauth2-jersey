package io.appsforfree.oauth2_jersey.domain;

import java.time.Instant;

public class RefreshToken 
{
	private String refreshToken;
	private String clientId;
	private Instant issuedAt;
	private Instant expiresOn;
	private String username;
	
	public RefreshToken(
			String refreshToken, 
			String clientId, 
			Instant issuedAt,
			Instant expiresOn,
			String username)
	{
		this.refreshToken = refreshToken;
		this.clientId = clientId;
		this.issuedAt = issuedAt;
		this.expiresOn = expiresOn;
		this.username = username;
	}
	
	public boolean isExpired() { return Instant.now().isAfter(expiresOn); }
	
	public String getRefreshToken() { return refreshToken; }
	
	public String getClientId() { return clientId; }
	
	public Instant getIssuedAt() { return issuedAt; }
	
	public Instant getExpiresOn() { return expiresOn; }
	
	public String getUsername() { return username; }
}
