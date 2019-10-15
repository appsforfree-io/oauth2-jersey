package io.appsforfree.oauth2_jersey.test.domain.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

import io.appsforfree.oauth2_jersey.domain.RefreshToken;

public class RefreshTokenTest 
{
	@Test
	public void testConstructor_returnsNotNull()
	{
		assertNotNull(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2019-10-14T15:47:00Z"), 
						Instant.parse("2019-10-14T16:47:00Z"), 
						"myUsername"));
	}
	
	@Test
	public void testConstructor_refreshTokenEquals123456789_getRefreshTokenReturns123456789()
	{
		assertEquals(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2019-10-14T15:47:00Z"), 
						Instant.parse("2019-10-14T16:47:00Z"), 
						"myUsername").getRefreshToken(),
				"123456789");
	}
	
	@Test
	public void testConstructor_clientIdEquals12345_getClientIdReturns12345()
	{
		assertEquals(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2019-10-14T15:47:00Z"), 
						Instant.parse("2019-10-14T16:47:00Z"), 
						"myUsername").getClientId(),
				"12345");
	}
	
	@Test
	public void testConstructor_issuedAtEquals201910141547_getIssuedAtReturns201910141547()
	{
		assertEquals(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2019-10-14T15:47:00Z"), 
						Instant.parse("2019-10-14T16:47:00Z"), 
						"myUsername").getIssuedAt(),
				Instant.parse("2019-10-14T15:47:00Z"));
	}
	
	@Test
	public void testConstructor_expiresOnEquals201910141647_getIssuedAtReturns201910141647()
	{
		assertEquals(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2019-10-14T15:47:00Z"), 
						Instant.parse("2019-10-14T16:47:00Z"), 
						"myUsername").getExpiresOn(),
				Instant.parse("2019-10-14T16:47:00Z"));
	}
	
	@Test
	public void testConstructor_usernameEqualsMyUsername_getUsernameReturnsMyUsername()
	{
		assertEquals(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2019-10-14T15:47:00Z"), 
						Instant.parse("2019-10-14T16:47:00Z"), 
						"myUsername").getUsername(),
				"myUsername");
	}
	
	@Test
	public void testIsExpired_expiresOnIsInThePast_returnsTrue()
	{
		assertTrue(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2010-10-14T15:47:00Z"), 
						Instant.parse("2011-10-14T16:47:00Z"), 
						"myUsername").isExpired());
	}
	
	@Test
	public void testIsExpired_expiresOnIsInTheFuture_returnsFalse()
	{
		assertFalse(
				new RefreshToken(
						"123456789", 
						"12345", 
						Instant.parse("2010-10-14T15:47:00Z"), 
						Instant.parse("2100-10-14T16:47:00Z"), 
						"myUsername").isExpired());
	}
}
