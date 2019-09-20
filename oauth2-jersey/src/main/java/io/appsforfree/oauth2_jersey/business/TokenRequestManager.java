package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.TokenType;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class TokenRequestManager 
{
	public static TokenResponse generateAccessToken(TokenRequest tokenRequest)
	{
		if (tokenRequest == null || !tokenRequest.isValid())
		{
			return null;
		}
		return new TokenResponse("123456", TokenType.BEARER);
	}
}
