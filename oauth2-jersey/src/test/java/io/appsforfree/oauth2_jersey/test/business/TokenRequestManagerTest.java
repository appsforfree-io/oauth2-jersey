package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.business.TokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.request.PasswordTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;

public class TokenRequestManagerTest 
{
	@Test(expected = InvalidRequestException.class)
	public void test_generateAccessToken_tokenRequestIsNull_returnNull() throws Exception
	{
		TokenRequestManager.generateAccessToken(null);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_generateAccessToken_invalidTokenRequest_returnNull() throws Exception
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				null, 
				null, 
				null, 
				null, 
				null);
		TokenRequestManager.generateAccessToken(passwordTokenRequest);
	}
	
	@Test
	public void test_generateAccessToken_validTokenRequest_returnNotNull() throws Exception
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
	public void test_generateAccessToken_validTokenRequest_accessTokenEquals123456() throws Exception
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
	public void test_generateAccessToken_validTokenRequest_tokenTypeEqualsBearer() throws Exception
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
