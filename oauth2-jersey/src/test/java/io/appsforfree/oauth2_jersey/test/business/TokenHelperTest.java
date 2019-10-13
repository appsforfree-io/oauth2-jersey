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
		assertNotNull(TokenHelper.createAccessToken("12345", "myUsername"));
	}
	
	@Test
	public void testCreateAccessToken_accessTokenIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getAccessToken());
	}
	
	@Test
	public void testCreateAccessToken_clientIdIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getClientId());
	}
	
	@Test
	public void testCreateAccessToken_clientIdEquals12345()
	{
		assertEquals(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getClientId(), 
				"12345");
	}
	
	@Test
	public void testCreateAccessToken_issuedAtIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getIssuedAt());
	}
	
	@Test
	public void testCreateAccessToken_expiresOnIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getExpiresOn());
	}
	
	@Test
	public void testCreateAccessToken_usernameIsNotNull()
	{
		assertNotNull(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getUsername());
	}
	
	@Test
	public void testCreateAccessToken_usernameEqualsMyUsername()
	{
		assertEquals(TokenHelper
				.createAccessToken("12345", "myUsername")
				.getUsername(), 
				"myUsername");
	}
}
