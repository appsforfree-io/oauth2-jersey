package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.TokenType;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class TokenRequestManager 
{
	public static TokenResponse generateAccessToken(TokenRequest tokenRequest) throws ErrorResponseException
	{
		if (tokenRequest == null || !tokenRequest.isValid())
			throw new InvalidRequestException(
					"The request is missing a required parameter"
					);
		if (!tokenRequest.getClientId().equals("11111"))
			throw new InvalidClientException("Invalid client id");
		
		return new TokenResponse("123456", TokenType.BEARER);
	}
}
