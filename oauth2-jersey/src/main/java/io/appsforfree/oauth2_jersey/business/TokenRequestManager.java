package io.appsforfree.oauth2_jersey.business;

import java.util.List;

import io.appsforfree.oauth2_jersey.dataaccess.ClientStore;
import io.appsforfree.oauth2_jersey.dataaccess.GrantTypeStore;
import io.appsforfree.oauth2_jersey.dataaccess.ScopeStore;
import io.appsforfree.oauth2_jersey.domain.AccessToken;
import io.appsforfree.oauth2_jersey.domain.Client;
import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidScopeException;
import io.appsforfree.oauth2_jersey.domain.exception.UnauthorizedClientException;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public abstract class TokenRequestManager 
{
	private ClientStore clientStore = ClientStore.getInstance();
	private GrantTypeStore grantTypeStore = GrantTypeStore.getInstance();
	private ScopeStore scopeStore = ScopeStore.getInstance();
	
	public TokenResponse handleTokenRequest(TokenRequest tokenRequest) throws ErrorResponseException
	{
		checkRequest(tokenRequest);
		
		checkClient(tokenRequest);
		
		checkGrantType(tokenRequest);
		
		checkValidScopes(tokenRequest);
		
		AccessToken accessToken = createAccessToken(tokenRequest);
		
		return new TokenResponse(accessToken);
	}
	
	public abstract boolean shouldCheckScopes();
	public abstract AccessToken createAccessToken(TokenRequest tokenRequest) throws ErrorResponseException;
	
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
		if (!grantTypes.contains(tokenRequest.getGrantType().getName()))
			throw new UnauthorizedClientException(
					"The client is not authorized to request an access token using this method");
	}
	
	private void checkValidScopes(TokenRequest tokenRequest) throws InvalidScopeException
	{
		if (!shouldCheckScopes()) { return; }
		List<String> validScopes = scopeStore.getValidScopes(tokenRequest.getClientId());
		String[] scopes = tokenRequest.getScope().split(" ");
		for (String requestedScope: scopes)
			if (!validScopes.contains(requestedScope))
				throw new InvalidScopeException(
						"The requested scope is invalid, unknown or malformed");
	}
}
