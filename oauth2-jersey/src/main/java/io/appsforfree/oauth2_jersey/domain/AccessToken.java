package io.appsforfree.oauth2_jersey.domain;

import java.util.Date;

public class AccessToken 
{
	private String accessToken;
	private String clientId;
	private Date issuedAt;
	private Date expiresOn;
	private String username;
	private String refreshToken;
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			Date issuedAt,
			Date expiresOn)
	{
		this.accessToken = accessToken;
		this.clientId = clientId;
		this.issuedAt = issuedAt;
		this.expiresOn = expiresOn;
	}
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			Date issuedAt,
			Date expiresOn, 
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
			Date issuedAt,
			Date expiresOn,
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
	
	public Date getIssuedAt() { return issuedAt; }
	
	public Date getExpiresOn() { return expiresOn; }
	
	public String getUsername() { return username; }
	
	public String getRefreshToken() { return refreshToken; }
}
