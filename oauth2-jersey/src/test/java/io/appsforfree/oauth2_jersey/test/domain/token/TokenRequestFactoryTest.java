package io.appsforfree.oauth2_jersey.test.domain.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.domain.request.GrantType;
import io.appsforfree.oauth2_jersey.domain.request.PasswordTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequestFactory;;

public class TokenRequestFactoryTest 
{
	@Test
	public void test_createRequest_nullParams_returnNull()
	{
		assertNull(TokenRequestFactory.createRequest(null, null));
	}
	
	@Test
	public void test_createRequest_invalidBasicToken_returnNull()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		assertNull(TokenRequestFactory.createRequest(map, null));
	}
	
	@Test
	public void test_createRequest_validParams_returnNotNull()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		assertNotNull(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ=="));
	}
	
	@Test
	public void test_createRequest_validParams_tokenRequestIsPassword()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		assertTrue(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ==") instanceof PasswordTokenRequest);
	}
	
	@Test
	public void test_createRequest_validParams_grantTypeEqualsPassword()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		assertEquals(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ==").getGrantType(), GrantType.PASSWORD);
	}
	
	@Test
	public void test_createRequest_validParams_grantTypeEqualsRefreshToken()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "refresh_token");
		map.add("refresh_token", "1234567890");
		assertEquals(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ==").getGrantType(), GrantType.REFRESHTOKEN);
	}
	
	@Test
	public void test_createRequest_validParams_clientIdEquals123456()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		assertEquals(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ==").getClientId(), "123456");
	}
	
	@Test
	public void test_createRequest_validParams_clientSecretEquals654321()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		assertEquals(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ==").getClientSecret(), "654321");
	}
	
	@Test
	public void test_createRequest_validParams_scopeEquals654321()
	{
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", "123456");
		map.add("password", "654321");
		map.add("scope", "profile");
		assertEquals(TokenRequestFactory.createRequest(map, "Basic MTIzNDU2OjY1NDMyMQ==").getScope(), "profile");
	}
}
