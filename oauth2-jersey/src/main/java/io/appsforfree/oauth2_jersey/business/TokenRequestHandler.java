package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.UnsupportedGrantTypeException;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class TokenRequestHandler 
{
	public static TokenResponse handleTokenRequest(
			TokenRequest tokenRequest) throws ErrorResponseException
	{
		if (tokenRequest == null)
			throw new InvalidRequestException(
					"The request is missing a required parameter"
					);
		switch (tokenRequest.getGrantType())
		{
			case PASSWORD:
				return PasswordRequestManager.getInstance().handleTokenRequest(tokenRequest);
			default:
				throw new UnsupportedGrantTypeException("The authorization grant type is not supported");
		}
	}
}
