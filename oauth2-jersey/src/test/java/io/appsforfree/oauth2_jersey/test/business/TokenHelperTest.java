package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.business.TokenHelper;

public class TokenHelperTest 
{
	@Test
	public void testCreateAccessToken_returnsNotNull()
	{
		assertNotNull(TokenHelper.createAccessToken(
				"12345", 
				"myUsername", 
				"AbCdEf01"));
	}
	
	@Test
	public void testCreateAccessToken_accessTokenIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getAccessToken());
	}
	
	@Test
	public void testCreateAccessToken_clientIdIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getClientId());
	}
	
	@Test
	public void testCreateAccessToken_clientIdEquals12345()
	{
		assertEquals(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getClientId(), 
				"12345");
	}
	
	@Test
	public void testCreateAccessToken_issuedAtIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getIssuedAt());
	}
	
	@Test
	public void testCreateAccessToken_expiresOnIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getExpiresOn());
	}
	
	@Test
	public void testCreateAccessToken_usernameIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getUsername());
	}
	
	@Test
	public void testCreateAccessToken_usernameEqualsMyUsername()
	{
		assertEquals(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getUsername(), 
				"myUsername");
	}
	
	public void testCreateAccessToken_refreshTokenIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getRefreshToken());
	}
	
	@Test
	public void testCreateAccessToken_refreshTokenEqualsAbCdEf01()
	{
		assertEquals(TokenHelper
				.createAccessToken(
						"12345", 
						"myUsername", 
						"AbCdEf01")
				.getRefreshToken(), 
				"AbCdEf01");
	}
	
	@Test
	public void testCreateRefreshToken_returnsNotNull()
	{
		assertNotNull(TokenHelper.createRefreshToken("12345", "myUsername"));
	}
	
	@Test
	public void testCreateRefreshToken_refreshTokenIsNotNull()
	{
		assertNotNull(
				TokenHelper.createRefreshToken(
						"12345", 
						"myUsername").getRefreshToken());
	}
	
	@Test
	public void testCreateRefreshToken_clientIdIsNotNull()
	{
		assertNotNull(
				TokenHelper.createRefreshToken(
						"12345", 
						"myUsername").getClientId());
	}
	
	@Test
	public void testCreateRefreshToken_clientIdEquals12345()
	{
		assertEquals(
				TokenHelper.createRefreshToken(
						"12345", 
						"myUsername").getClientId(), 
				"12345");
	}
	
	@Test
	public void testCreateRefreshToken_usernameIsNotNull()
	{
		assertNotNull(
				TokenHelper.createRefreshToken(
						"12345", 
						"myUsername").getUsername());
	}
	
	@Test
	public void testCreateRefreshToken_usernameEqualsMyUsername()
	{
		assertEquals(
				TokenHelper.createRefreshToken(
						"12345", 
						"myUsername").getUsername(), 
				"myUsername");
	}
}
