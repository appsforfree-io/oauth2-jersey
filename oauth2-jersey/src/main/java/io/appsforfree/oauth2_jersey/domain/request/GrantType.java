package io.appsforfree.oauth2_jersey.domain.request;

public enum GrantType 
{
	PASSWORD("password"),
	REFRESHTOKEN("refresh_token"),
	CLIENTCREDENTIALS("client_credentials");
	
	private String name;
	
	GrantType(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static GrantType fromString(String name)
	{
		for (GrantType type: GrantType.values())
			if (type.name.equals(name))
				return type;
		return null;
	}
}
