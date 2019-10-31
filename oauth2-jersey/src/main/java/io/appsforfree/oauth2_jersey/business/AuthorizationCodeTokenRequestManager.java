package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.dataaccess.AccessTokenStore;
import io.appsforfree.oauth2_jersey.dataaccess.AuthorizationCodeStore;
import io.appsforfree.oauth2_jersey.dataaccess.RefreshTokenStore;
import io.appsforfree.oauth2_jersey.domain.AccessToken;
import io.appsforfree.oauth2_jersey.domain.AuthorizationCode;
import io.appsforfree.oauth2_jersey.domain.RefreshToken;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidGrantException;
import io.appsforfree.oauth2_jersey.domain.request.AuthorizationCodeTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class AuthorizationCodeTokenRequestManager extends TokenRequestManager 
{
	
	private static AuthorizationCodeTokenRequestManager authorizationCodeTokenRequestManager = new AuthorizationCodeTokenRequestManager();
	
	private AccessTokenStore accessTokenStore = AccessTokenStore.getInstance();
	private RefreshTokenStore refreshTokenStore = RefreshTokenStore.getInstance();
	private AuthorizationCodeStore authorizationCodeStore = AuthorizationCodeStore.getInstance();
	
	public static AuthorizationCodeTokenRequestManager getInstance() { return authorizationCodeTokenRequestManager; }
	
	@Override
	public boolean shouldCheckScopes() { return false; }
	
	@Override
	public AccessToken createAccessToken(TokenRequest tokenRequest) throws ErrorResponseException 
	{
		AuthorizationCodeTokenRequest authorizationCodeRequest = (AuthorizationCodeTokenRequest)tokenRequest;
		AuthorizationCode authorizationCode = authorizationCodeStore.getAuthorizationCode(authorizationCodeRequest.getCode());
		if (authorizationCode == null)
			throw new InvalidGrantException("Invalid code");
		if (!authorizationCode.getRedirectUri().equals(authorizationCodeRequest.getRedirectUri()))
			throw new InvalidGrantException("Invalid redirect URI");
		
		RefreshToken refreshToken = TokenHelper.createRefreshToken(
				authorizationCodeRequest.getClientId(), 
				authorizationCode.getUsername());
		refreshTokenStore.saveRefreshToken(refreshToken);
		
		AccessToken accessToken = TokenHelper.createAccessToken(
				tokenRequest.getClientId(), 
				authorizationCode.getUsername(), 
				refreshToken.getRefreshToken());
		accessTokenStore.saveAccessToken(accessToken);
		return accessToken;
	}

}
