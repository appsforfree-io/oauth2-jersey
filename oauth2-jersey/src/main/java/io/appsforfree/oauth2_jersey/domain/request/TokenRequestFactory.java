package io.appsforfree.oauth2_jersey.domain.request;

import javax.ws.rs.core.MultivaluedMap;

import io.appsforfree.oauth2_jersey.business.AuthorizationHelper;

public class TokenRequestFactory 
{
	public static TokenRequest createRequest(
			MultivaluedMap<String, String> body, 
			String authorization)
	{
		if (body == null) return null;
		
		String grantTypeString = body.getFirst("grant_type");
		GrantType grantType = GrantType.fromString(grantTypeString);
		if (grantType == null) { return null; }
		
		switch (grantType)
		{
			case PASSWORD:
				return createPasswordRequest(body, authorization);
			case REFRESHTOKEN:
				return createRefreshTokenRequest(body, authorization);
			default:
				return null;
		}
	}
	
	private static PasswordTokenRequest createPasswordRequest(
			MultivaluedMap<String, String> body, 
			String authorization)
	{
		String basicToken = AuthorizationHelper.getToken(authorization);
		if (basicToken == null) return null;
		
		String username = body.getFirst("username");
		String password = body.getFirst("password");
		String clientId = AuthorizationHelper.getClientId(basicToken);
		String clientSecret = AuthorizationHelper.getClientSecret(basicToken);
		String scope = body.getFirst("scope");
		return new PasswordTokenRequest(
				username, 
				password, 
				clientId, 
				clientSecret, 
				scope);
	}
	
	private static RefreshTokenRequest createRefreshTokenRequest(
			MultivaluedMap<String, String> body, 
			String authorization)
	{
		String basicToken = AuthorizationHelper.getToken(authorization);
		if (basicToken == null) return null;
		
		String refreshToken = body.getFirst("refresh_token");
		String clientId = AuthorizationHelper.getClientId(basicToken);
		String clientSecret = AuthorizationHelper.getClientSecret(basicToken);
		String scope = body.getFirst("scope");
		return new RefreshTokenRequest(
				refreshToken, 
				clientId, 
				clientSecret, 
				scope);
	}
}
