package io.appsforfree.oauth2_jersey.domain.request;

public class ClientCredentialsTokenRequest extends TokenRequest 
{
	public ClientCredentialsTokenRequest(
			String clientId, 
			String clientSecret, 
			String scope)
	{
		super.clientId = clientId;
		super.clientSecret = clientSecret;
		super.grantType = GrantType.CLIENTCREDENTIALS;
		super.scope = scope;
	}
	
	@Override
	public boolean isValid() 
	{
		return 
				clientId != null 
				&& !clientId.isEmpty() 
				&& clientSecret != null 
				&& !clientSecret.isEmpty();
	}

}
