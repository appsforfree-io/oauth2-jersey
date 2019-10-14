package io.appsforfree.oauth2_jersey.domain;

public class Client 
{
	private String clientId;
	private String clientSecret;
	private String clientType;
	
	public Client(
			String clientId, 
			String clientSecret, 
			String clientType)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.clientType = clientType;
	}
	
	public String getClientId() { return clientId; }
	
	public String getClientSecret() { return clientSecret; }
	
	public String getClientType() { return clientType; }
}
