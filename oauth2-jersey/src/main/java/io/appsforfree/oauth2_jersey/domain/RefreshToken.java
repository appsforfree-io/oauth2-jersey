package io.appsforfree.oauth2_jersey.domain;

public class RefreshToken 
{
	private String refreshToken;
	private String clientId;
	private String username;
	
	public RefreshToken(
			String refreshToken, 
			String clientId, 
			String username)
	{
		this.refreshToken = refreshToken;
		this.clientId = clientId;
		this.username = username;
	}
	
	public String getRefreshToken() { return refreshToken; }
	
	public String getClientId() { return clientId; }
	
	public String getUsername() { return username; }
}
