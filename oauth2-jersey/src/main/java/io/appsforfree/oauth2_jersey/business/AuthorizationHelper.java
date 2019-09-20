package io.appsforfree.oauth2_jersey.business;

import java.util.Base64;

import io.appsforfree.oauth2_jersey.domain.TokenType;

public class AuthorizationHelper 
{
	public static String getClientId(String basicToken)
	{
		if (basicToken == null) return null;
		
		String decodedToken = decodeToken(basicToken);
		if (!isBasicTokenValid(decodedToken)) return null;
		
		String[] tokenChunks = decodedToken.split(":");
		return tokenChunks[0];
	}
	
	public static String getClientSecret(String basicToken)
	{
		if (basicToken == null) return null;
		
		String decodedToken = decodeToken(basicToken);
		if (!isBasicTokenValid(decodedToken)) return null;
		
		String[] tokenChunks = decodedToken.split(":");
		return tokenChunks[1];
	}
	
	private static String decodeToken(String basicToken)
	{
		return new String(Base64.getDecoder().decode(basicToken));
	}
	
	private static boolean isBasicTokenValid(String decodedToken)
	{
		if (decodedToken == null) return false;
		
		String[] tokenChunks = decodedToken.split(":");
		return tokenChunks.length == 2;
	}
	
	public static TokenType getAuthorizationType(String header)
	{
		if (!isHeaderValid(header)) return null;
		
		String[] authorizationValues = header.split(" ");
		String type = authorizationValues[0];
		if (type.equalsIgnoreCase("basic")) return TokenType.BASIC;
		if (type.equalsIgnoreCase("bearer")) return TokenType.BEARER;
		
		return null;
	}
	
	public static String getToken(String header)
	{
		if (!isHeaderValid(header) || getAuthorizationType(header) == null) 
			return null;
		
		return header.split(" ")[1];
	}
	
	private static boolean isHeaderValid(String header)
	{
		if (header == null || header.isEmpty()) return false;
		
		String[] authorizationValues = header.split(" ");
		if (authorizationValues == null || authorizationValues.length != 2)
			return false;
		
		return true;
	}
}
