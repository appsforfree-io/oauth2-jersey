package io.appsforfree.oauth2_jersey.domain.request;

public class RefreshTokenRequest extends TokenRequest 
{

	private String refreshToken;
	
	public RefreshTokenRequest(
			String refreshToken, 
			String clientId, 
			String clientSecret, 
			String scope)
	{
		this.refreshToken = refreshToken;
		super.grantType = GrantType.REFRESHTOKEN;
		super.clientId = clientId;
		super.clientSecret = clientSecret;
		super.scope = scope;
	}

	@Override
	public boolean isValid() 
	{
		return 
				refreshToken != null 
				&& !refreshToken.isEmpty();
	}
	
	public String getRefreshToken() { return refreshToken; }

}
