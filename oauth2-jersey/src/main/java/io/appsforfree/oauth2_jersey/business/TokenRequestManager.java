package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class TokenRequestManager 
{
	public static TokenResponse generateAccessToken(TokenRequest tokenRequest)
	{
		if (tokenRequest.isValid())
		{
			return new TokenResponse("123456", "Bearer");
		}
		return null;
	}
}
