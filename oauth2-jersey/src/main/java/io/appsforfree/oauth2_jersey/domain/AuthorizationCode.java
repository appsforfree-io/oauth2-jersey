package io.appsforfree.oauth2_jersey.domain;

import java.time.Instant;

public class AuthorizationCode 
{
	private String code;
	private String clientId;
	private String redirectUri;
	private Instant issuedAt;
	private Instant expiresOn;
	private String username;
	
	public AuthorizationCode(
			String code, 
			String clientId, 
			String redirectUri, 
			Instant issuedAt, 
			Instant expiresOn, 
			String username)
	{
		this.code = code;
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.issuedAt = issuedAt;
		this.expiresOn = expiresOn;
		this.username = username;
	}
	
	public String getCode() { return code; }
	
	public String getClientId() { return clientId; }
	
	public String getRedirectUri() { return redirectUri; }
	
	public Instant getIssuedAt() { return issuedAt; }
	
	public Instant getExpiresOn() { return expiresOn; }
	
	public String getUsername() { return username; }
}
