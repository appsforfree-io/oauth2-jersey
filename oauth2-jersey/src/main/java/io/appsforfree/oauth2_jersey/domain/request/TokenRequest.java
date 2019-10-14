package io.appsforfree.oauth2_jersey.domain.request;

public abstract class TokenRequest 
{
	protected GrantType grantType;
	protected String clientId;
	protected String clientSecret;
	protected String scope;
	
	public abstract boolean isValid();
	
	public GrantType getGrantType() { return grantType; }
	
	public String getClientId() { return clientId; }
	
	public String getClientSecret() { return clientSecret; }
	
	public String getScope() { return scope; }
}
