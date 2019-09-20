package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.business.TokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.request.PasswordTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class TokenRequestManagerTest 
{
	@Test
	public void test_generateAccessToken_tokenRequestIsNull_returnNull()
	{
		assertNull(TokenRequestManager.generateAccessToken(null));
	}
	
	@Test
	public void test_generateAccessToken_invalidTokenRequest_returnNull()
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				null, 
				null, 
				null, 
				null, 
				null);
		assertNull(TokenRequestManager.generateAccessToken(passwordTokenRequest));
	}
	
	@Test
	public void test_generateAccessToken_validTokenRequest_returnNotNull()
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				"123456", 
				"654321", 
				"11111", 
				"22222", 
				"profile");
		assertNotNull(TokenRequestManager.generateAccessToken(passwordTokenRequest));
	}
	
	@Test
	public void test_generateAccessToken_validTokenRequest_accessTokenEquals123456()
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				"123456", 
				"654321", 
				"11111", 
				"22222", 
				"profile");
		assertEquals(
				TokenRequestManager.generateAccessToken(passwordTokenRequest).getAccessToken(),
				"123456");
	}
	
	@Test
	public void test_generateAccessToken_validTokenRequest_tokenTypeEqualsBearer()
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				"123456", 
				"654321", 
				"11111", 
				"22222", 
				"profile");
		assertEquals(
				TokenRequestManager.generateAccessToken(passwordTokenRequest).getTokenType(),
				"Bearer");
	}
}
