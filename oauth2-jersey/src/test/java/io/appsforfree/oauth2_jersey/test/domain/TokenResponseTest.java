package io.appsforfree.oauth2_jersey.test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.TokenType;

public class TokenResponseTest 
{
	@Test
	public void test_accessTokenIsNotNull()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER);
		
		assertNotNull(accessToken.getAccessToken());
	}
	
	@Test
	public void test_tokenTypeIsNotNull()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER);
		
		assertNotNull(accessToken.getTokenType());
	}
	
	@Test
	public void test_accessTokenEquals123456()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER);
		
		assertEquals(accessToken.getAccessToken(), "123456");
	}
	
	@Test
	public void test_tokenTypeEqualsBearer()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER);
		
		assertEquals(accessToken.getTokenType(), "Bearer");
	}
	
	@Test
	public void test_expiresInEquals12345678()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER, 12345678, "654321", "profile");
		
		assertEquals(accessToken.getExpiresIn(), 12345678);
	}
	
	@Test
	public void test_refreshTokenEquals654321()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER, 12345678, "654321", "profile");
		
		assertEquals(accessToken.getRefreshToken(), "654321");
	}
	
	@Test
	public void test_scopeEqualsProfile()
	{
		TokenResponse accessToken = new TokenResponse("123456", TokenType.BEARER, 12345678, "654321", "profile");
		
		assertEquals(accessToken.getScope(), "profile");
	}
}
