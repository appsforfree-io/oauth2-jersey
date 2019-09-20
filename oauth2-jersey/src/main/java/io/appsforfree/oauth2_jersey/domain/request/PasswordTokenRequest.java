package io.appsforfree.oauth2_jersey.domain.request;

public class PasswordTokenRequest extends TokenRequest 
{
	private String username;
	private String password;
	
	public PasswordTokenRequest(
			String username, 
			String password, 
			String clientId, 
			String clientSecret, 
			String scope)
	{
		this.username = username;
		this.password = password;
		super.grantType = GrantType.PASSWORD;
		super.clientId = clientId;
		super.clientSecret = clientSecret;
		super.scope = scope;
	}
}
