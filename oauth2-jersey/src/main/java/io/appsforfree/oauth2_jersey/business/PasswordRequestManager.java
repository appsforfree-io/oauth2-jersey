package io.appsforfree.oauth2_jersey.business;

import io.appsforfree.oauth2_jersey.dataaccess.AccessTokenStore;
import io.appsforfree.oauth2_jersey.dataaccess.RefreshTokenStore;
import io.appsforfree.oauth2_jersey.dataaccess.UserStore;
import io.appsforfree.oauth2_jersey.domain.AccessToken;
import io.appsforfree.oauth2_jersey.domain.RefreshToken;
import io.appsforfree.oauth2_jersey.domain.User;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidGrantException;
import io.appsforfree.oauth2_jersey.domain.request.PasswordTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class PasswordRequestManager extends TokenRequestManager 
{
	private static PasswordRequestManager passwordRequestManager = new PasswordRequestManager();
	
	private AccessTokenStore accessTokenStore = AccessTokenStore.getInstance();
	private RefreshTokenStore refreshTokenStore = RefreshTokenStore.getInstance();
	private UserStore userStore = UserStore.getInstance();
	
	public static PasswordRequestManager getInstance() { return passwordRequestManager; }

	@Override
	public AccessToken createAccessToken(TokenRequest tokenRequest) throws ErrorResponseException
	{
		PasswordTokenRequest passwordTokenRequest = (PasswordTokenRequest)tokenRequest;
		
		User user = userStore.getUser(passwordTokenRequest.getUsername());
		if (user == null)
			throw new InvalidGrantException("Invalid username");
		if (!user.getPassword().equals(passwordTokenRequest.getPassword()))
			throw new InvalidGrantException("Invalid password");
		
		RefreshToken refreshToken = TokenHelper.createRefreshToken(
				passwordTokenRequest.getClientId(), 
				passwordTokenRequest.getUsername());
		refreshTokenStore.saveRefreshToken(refreshToken);
		
		AccessToken accessToken = TokenHelper.createAccessToken(
				tokenRequest.getClientId(), 
				"myUsername", 
				refreshToken.getRefreshToken());
		accessTokenStore.saveAccessToken(accessToken);
		return accessToken;
	}
}
