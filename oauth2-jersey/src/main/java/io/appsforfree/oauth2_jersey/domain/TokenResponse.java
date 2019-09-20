package io.appsforfree.oauth2_jersey.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse 
{
	private String accessToken;
	private String tokenType;
	private long expiresIn;
	private String refreshToken;
	private String scope;
	
	public TokenResponse(
			String accessToken, 
			String tokenType)
	{
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}
	
	public TokenResponse(
			String accessToken, 
			String tokenType, 
			long expiresIn, 
			String refreshToken, 
			String scope)
	{
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.scope = scope;
	}
	
	@JsonProperty("access_token")
	public String getAccessToken() { return accessToken; }
	
	@JsonProperty("token_type")
	public String getTokenType() { return tokenType; }
	
	@JsonProperty("expires_in")
	@JsonInclude(Include.NON_EMPTY)
	public long getExpiresIn() { return expiresIn; }
	
	@JsonProperty("refresh_token")
	@JsonInclude(Include.NON_EMPTY)
	public String getRefreshToken() { return refreshToken; }
	
	@JsonInclude(Include.NON_EMPTY)
	public String getScope() { return scope; }
}
