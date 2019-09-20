package io.appsforfree.oauth2_jersey.domain.request;

public abstract class TokenRequest 
{
	protected GrantType grantType;
	protected String clientId;
	protected String clientSecret;
	protected String scope;
	
	public GrantType getGrantType() { return grantType; }
	public void setGrantType(GrantType grantType) { this.grantType = grantType; }
	
	public String getClientId() { return clientId; }
	public void setClientId(String clientId) { this.clientId = clientId; }
	
	public String getClientSecret() { return clientSecret; }
	public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
	
	public String getScope() { return scope; }
	public void setScope(String scope) { this.scope = scope; }
}
