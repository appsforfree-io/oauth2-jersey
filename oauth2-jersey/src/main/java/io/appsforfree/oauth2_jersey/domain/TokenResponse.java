package io.appsforfree.oauth2_jersey.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse 
{
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("expires_in")
	@JsonInclude(Include.NON_EMPTY)
	private long expiresIn;
	@JsonProperty("refresh_token")
	@JsonInclude(Include.NON_EMPTY)
	private String refreshToken;
	@JsonInclude(Include.NON_EMPTY)
	private String scope;
	
	public TokenResponse() {}
	
	public TokenResponse(
			String accessToken, 
			TokenType tokenType)
	{
		this.accessToken = accessToken;
		this.tokenType = getTokenType(tokenType);
	}
	
	public TokenResponse(
			String accessToken, 
			TokenType tokenType, 
			long expiresIn, 
			String refreshToken, 
			String scope)
	{
		this.accessToken = accessToken;
		this.tokenType = getTokenType(tokenType);
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.scope = scope;
	}
	
	private String getTokenType(TokenType tokenType)
	{
		switch (tokenType)
		{
			case BASIC:
				return "Basic";
			case BEARER:
				return "Bearer";
			default:
				return null;
		}
	}
	
	public String getAccessToken() { return accessToken; }
	public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
	
	public String getTokenType() { return tokenType;}
	public void setTokenType(String tokenType) { this.tokenType = tokenType; }
	
	public long getExpiresIn() { return expiresIn; }
	public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
	
	public String getRefreshToken() { return refreshToken; }
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
	
	public String getScope() { return scope; }
	public void setScope(String scope) { this.scope = scope; }
}
