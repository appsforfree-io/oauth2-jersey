package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.business.AuthorizationHelper;
import io.appsforfree.oauth2_jersey.domain.TokenType;

public class AuthorizationHelperTest 
{
	@Test
	public void test_getAuthorizationType_invalidHeader_returnNull()
	{
		assertNull(AuthorizationHelper.getAuthorizationType("123456"));
	}
	
	@Test
	public void test_getAuthorizationType_invalidTokenType_returnNull()
	{
		assertNull(AuthorizationHelper.getAuthorizationType("Bassic 123456"));
	}
	
	@Test
	public void test_getAuthorizationType_basicToken_returnNotNull()
	{
		assertNotNull(AuthorizationHelper.getAuthorizationType("Basic 123456"));
	}
	
	@Test
	public void test_getAuthorizationType_basicToken_returnBasic()
	{
		assertEquals(AuthorizationHelper.getAuthorizationType("Basic 123456"), TokenType.BASIC);
	}
	
	@Test
	public void test_getAuthorizationType_bearerToken_returnNotNull()
	{
		assertNotNull(AuthorizationHelper.getAuthorizationType("Bearer 123456"));
	}
	
	@Test
	public void test_getAuthorizationType_bearerToken_returnBearer()
	{
		assertEquals(AuthorizationHelper.getAuthorizationType("Bearer 123456"), TokenType.BEARER);
	}
	
	@Test
	public void test_getToken_invalidHeader_returnNull()
	{
		assertNull(AuthorizationHelper.getToken("123456"));
	}
	
	@Test
	public void test_getToken_invalidTokenType_returnNull()
	{
		assertNull(AuthorizationHelper.getToken("Bassic 123456"));
	}
	
	@Test
	public void test_getToken_basicToken_returnNotNull()
	{
		assertNotNull(AuthorizationHelper.getToken("Basic 123456"));
	}
	
	@Test
	public void test_getToken_basicToken_return123456()
	{
		assertEquals(AuthorizationHelper.getToken("Basic 123456"), "123456");
	}
	
	@Test
	public void test_getToken_bearerToken_returnNotNull()
	{
		assertNotNull(AuthorizationHelper.getToken("Bearer 123456"));
	}
	
	@Test
	public void test_getToken_bearerToken_return123456()
	{
		assertEquals(AuthorizationHelper.getToken("Bearer 123456"), "123456");
	}
	
	@Test
	public void test_getClientId_basicTokenIsNull_returnNull()
	{
		assertNull(AuthorizationHelper.getClientId(null));
	}
	
	@Test
	public void test_getClientId_basicTokenIsInvalid_returnNull()
	{
		assertNull(AuthorizationHelper.getClientId("123456"));
	}
	
	@Test
	public void test_getClientId_basicTokenIsValid_returnNotNull()
	{
		assertNotNull(AuthorizationHelper.getClientId("MTIzNDU2OjY1NDMyMQ=="));
	}
	
	@Test
	public void test_getClientId_basicTokenIsValid_clientIdEquals123456()
	{
		assertEquals(AuthorizationHelper.getClientId("MTIzNDU2OjY1NDMyMQ=="), "123456");
	}
	
	@Test
	public void test_getClientSecret_basicTokenIsNull_returnNull()
	{
		assertNull(AuthorizationHelper.getClientSecret(null));
	}
	
	@Test
	public void test_getClientSecret_basicTokenIsInvalid_returnNull()
	{
		assertNull(AuthorizationHelper.getClientSecret("123456"));
	}
	
	@Test
	public void test_getClientSecret_basicTokenIsValid_returnNotNull()
	{
		assertNotNull(AuthorizationHelper.getClientSecret("MTIzNDU2OjY1NDMyMQ=="));
	}
	
	@Test
	public void test_getClientSecret_basicTokenIsValid_clientSecretEquals654321()
	{
		assertEquals(AuthorizationHelper.getClientSecret("MTIzNDU2OjY1NDMyMQ=="), "654321");
	}
}
