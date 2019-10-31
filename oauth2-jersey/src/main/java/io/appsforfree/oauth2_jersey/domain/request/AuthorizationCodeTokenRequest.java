package io.appsforfree.oauth2_jersey.domain.request;

public class AuthorizationCodeTokenRequest extends TokenRequest 
{
	private String code;
	private String redirectUri;
	
	public AuthorizationCodeTokenRequest(
			String code, 
			String redirectUri, 
			String clientId,
			String clientSecret)
	{
		this.code = code;
		this.redirectUri = redirectUri;
		super.grantType = GrantType.AUTHORIZATIONCODE;
		super.clientId = clientId;
		super.clientSecret = clientSecret;
	}
	
	@Override
	public boolean isValid() { return code != null && !code.isEmpty(); }
	
	public String getCode() { return code; }
	
	public String getRedirectUri() { return redirectUri; }

}
