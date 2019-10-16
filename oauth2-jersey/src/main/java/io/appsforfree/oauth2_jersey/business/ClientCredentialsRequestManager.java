package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.dataaccess.AccessTokenStore;
import io.appsforfree.oauth2_jersey.dataaccess.RefreshTokenStore;
import io.appsforfree.oauth2_jersey.domain.AccessToken;
import io.appsforfree.oauth2_jersey.domain.RefreshToken;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class ClientCredentialsRequestManager extends TokenRequestManager 
{
	private static ClientCredentialsRequestManager clientCredentialsRequestManager = new ClientCredentialsRequestManager();
	
	private RefreshTokenStore refreshTokenStore = RefreshTokenStore.getInstance();
	private AccessTokenStore accessTokenStore = AccessTokenStore.getInstance();
	
	public static ClientCredentialsRequestManager getInstance() { return clientCredentialsRequestManager; }
	
	@Override
	public AccessToken createAccessToken(
			TokenRequest tokenRequest) throws ErrorResponseException 
	{
		RefreshToken refreshToken = TokenHelper.createRefreshToken(
				tokenRequest.getClientId(), 
				null);
		refreshTokenStore.saveRefreshToken(refreshToken);
		
		AccessToken accessToken = TokenHelper.createAccessToken(
				tokenRequest.getClientId(), 
				null, 
				refreshToken.getRefreshToken());
		accessTokenStore.saveAccessToken(accessToken);
		return accessToken;
	}

}
