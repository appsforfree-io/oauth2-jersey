package io.appsforfree.oauth2_jersey.domain.request;

public abstract class TokenRequest 
{
	protected GrantType grantType;
	protected String clientId;
	protected String clientSecret;
	protected String scope;
	
	public abstract boolean isValid();
}
