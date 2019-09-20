package io.appsforfree.oauth2_jersey.domain.request;

import javax.ws.rs.core.MultivaluedMap;

public class TokenRequestFactory 
{
	public static TokenRequest createRequest(MultivaluedMap<String, String> body)
	{
		String grantTypeString = body.getFirst("grant_type");
		GrantType grantType = GrantType.fromString(grantTypeString);
		if (grantType == null) { return null; }
		
		String username = body.getFirst("username");
		String password = body.getFirst("password");
		String clientId = body.getFirst("client_id");
		String clientSecret = body.getFirst("client_secret");
		String scope = body.getFirst("scope");
		switch (grantType)
		{
			case PASSWORD:
				return new PasswordTokenRequest(
						username, 
						password, 
						clientId, 
						clientSecret, 
						scope);
			default:
				return null;
		}
	}
}
