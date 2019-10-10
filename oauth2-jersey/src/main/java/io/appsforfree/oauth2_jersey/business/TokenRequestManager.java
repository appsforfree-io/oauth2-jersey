package io.appsforfree.oauth2_jersey.business;

import java.util.List;

import io.appsforfree.oauth2_jersey.dataaccess.ClientStore;
import io.appsforfree.oauth2_jersey.dataaccess.GrantTypeStore;
import io.appsforfree.oauth2_jersey.domain.Client;
import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.TokenType;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.UnauthorizedClientException;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class TokenRequestManager 
{
	private static TokenRequestManager tokenRequestManager = new TokenRequestManager();
	
	private ClientStore clientStore = ClientStore.getInstance();
	private GrantTypeStore grantTypeStore = GrantTypeStore.getInstance();
	
	public static TokenRequestManager getInstance() { return tokenRequestManager; }
	
	public TokenResponse generateAccessToken(TokenRequest tokenRequest) throws ErrorResponseException
	{
		checkRequest(tokenRequest);
		
		checkClient(tokenRequest);
		
		checkGrantType(tokenRequest);
		
		return new TokenResponse("123456", TokenType.BEARER);
	}
	
	private void checkRequest(TokenRequest tokenRequest) throws InvalidRequestException
	{
		if (tokenRequest == null || !tokenRequest.isValid())
			throw new InvalidRequestException(
					"The request is missing a required parameter"
					);
	}
	
	private void checkClient(TokenRequest tokenRequest) throws InvalidClientException
	{
		Client client = clientStore.getClient(tokenRequest.getClientId());
		if (client == null)
			throw new InvalidClientException("Invalid client id");
		if (!client.getClientSecret().equals(tokenRequest.getClientSecret()))
			throw new InvalidClientException("Invalid client secret");
	}
	
	private void checkGrantType(TokenRequest tokenRequest) throws UnauthorizedClientException
	{
		List<String> grantTypes = grantTypeStore.getSupportedGrantTypes(tokenRequest.getClientId());
		if (!grantTypes.contains("password"))
			throw new UnauthorizedClientException("The client is not authorized to request an access token using this method");
	}
}
