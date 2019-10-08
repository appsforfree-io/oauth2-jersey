package io.appsforfree.oauth2_jersey.domain;

public class AccessToken 
{
	private String accessToken;
	private String clientId;
	private String username;
	private String refreshToken;
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			String username)
	{
		this.accessToken = accessToken;
		this.clientId = clientId;
		this.username = username;
	}
	
	public AccessToken(
			String accessToken, 
			String clientId, 
			String username, 
			String refreshToken)
	{
		this.accessToken = accessToken;
		this.clientId = clientId;
		this.username = username;
		this.refreshToken = refreshToken;
	}
	
	public String getAccessToken() { return accessToken; }
	
	public String getClientId() { return clientId; }
	
	public String getUsername() { return username; }
	
	public String getRefreshToken() { return refreshToken; }
}
