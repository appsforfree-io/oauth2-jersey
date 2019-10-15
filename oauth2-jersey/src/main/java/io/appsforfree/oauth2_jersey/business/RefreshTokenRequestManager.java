package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.dataaccess.AccessTokenStore;
import io.appsforfree.oauth2_jersey.dataaccess.RefreshTokenStore;
import io.appsforfree.oauth2_jersey.domain.AccessToken;
import io.appsforfree.oauth2_jersey.domain.RefreshToken;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidGrantException;
import io.appsforfree.oauth2_jersey.domain.request.RefreshTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class RefreshTokenRequestManager extends TokenRequestManager 
{
	private static RefreshTokenRequestManager refreshTokenRequestManager = new RefreshTokenRequestManager();
	
	private AccessTokenStore accessTokenStore = AccessTokenStore.getInstance();
	private RefreshTokenStore refreshTokenStore = RefreshTokenStore.getInstance();
	
	public static RefreshTokenRequestManager getInstance() { return refreshTokenRequestManager; }
	
	@Override
	public AccessToken createAccessToken(
			TokenRequest tokenRequest) throws ErrorResponseException 
	{
		RefreshTokenRequest refreshTokenRequest = (RefreshTokenRequest)tokenRequest;
		RefreshToken refreshToken = refreshTokenStore.getRefreshToken(refreshTokenRequest.getRefreshToken());
		if (refreshToken == null)
			throw new InvalidGrantException("The refresh token is invalid or has been revoked");
		if (refreshToken.isExpired())
		{
			refreshTokenStore.deleteRefreshToken(refreshToken.getRefreshToken());
			refreshToken = TokenHelper.createRefreshToken(
					refreshTokenRequest.getClientId(), 
					refreshToken.getUsername());
			refreshTokenStore.saveRefreshToken(refreshToken);
		}
		
		AccessToken accessToken = TokenHelper.createAccessToken(
				tokenRequest.getClientId(), 
				refreshToken.getUsername(), 
				refreshToken.getRefreshToken());
		accessTokenStore.saveAccessToken(accessToken);
		return accessToken;
	}

}
