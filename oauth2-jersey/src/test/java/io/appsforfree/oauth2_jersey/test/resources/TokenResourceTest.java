package io.appsforfree.oauth2_jersey.test.resources;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.domain.ErrorResponse;
import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.resources.TokenResource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class TokenResourceTest extends JerseyTest
{
	@Override
	public Application configure()
	{
		return new ResourceConfig(TokenResource.class);
	}
	
	@Test
	public void testHandleTokenRequest_nullParameters_statusEquals400()
	{
		Response response = target("/token").request().post(null);
		assertEquals(response.getStatus(), 400);
	}
	
	@Test
	public void testHandleTokenRequest_nullParameters_errorEqualsInvalidRequest()
	{
		Response response = target("/token").request().post(null);
		ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
		assertEquals(errorResponse.getError(), "invalid_request");
	}
	
	@Test
	public void testHandleTokenRequest_invalidContentType_statusEquals415()
	{
		MultivaluedMap<String, String> tokenBody = new MultivaluedHashMap<String,String>();
		
		tokenBody.add("grant_type", "password");
		tokenBody.add("username", "123456");
		tokenBody.add("password", "654321");
		Entity<MultivaluedMap> entity = Entity.entity(
				tokenBody, 
				MediaType.APPLICATION_JSON);
		Response response = target("/token").request().post(entity);
		assertEquals(response.getStatus(), 415);
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientId_statusEquals400()
	{
		MultivaluedMap<String, String> tokenBody = new MultivaluedHashMap<String,String>();
		
		tokenBody.add("grant_type", "password");
		tokenBody.add("username", "123456");
		tokenBody.add("password", "654321");
		Entity<MultivaluedMap> entity = Entity.entity(
				tokenBody, 
				MediaType.APPLICATION_FORM_URLENCODED);
		Response response = target("/token")
				.request()
				.header("Authorization", "Basic MTlzNDU6Njc4OTA=")
				.post(entity);
		assertEquals(response.getStatus(), 400);
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientId_errorEqualsInvalidClient()
	{
		MultivaluedMap<String, String> tokenBody = new MultivaluedHashMap<String,String>();
		
		tokenBody.add("grant_type", "password");
		tokenBody.add("username", "123456");
		tokenBody.add("password", "654321");
		Entity<MultivaluedMap> entity = Entity.entity(
				tokenBody, 
				MediaType.APPLICATION_FORM_URLENCODED);
		Response response = target("/token")
				.request()
				.header("Authorization", "Basic MTlzNDU6Njc4OTA=")
				.post(entity);
		ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
		assertEquals(errorResponse.getError(), "invalid_client");
	}
	
	@Test
	public void testHandleTokenRequest_validParameters_statusEquals200()
	{
		MultivaluedMap<String, String> tokenBody = new MultivaluedHashMap<String,String>();
		
		tokenBody.add("grant_type", "password");
		tokenBody.add("username", "123456");
		tokenBody.add("password", "654321");
		Entity<MultivaluedMap> entity = Entity.entity(
				tokenBody, 
				MediaType.APPLICATION_FORM_URLENCODED);
		Response response = target("/token")
				.request()
				.header("Authorization", "Basic MTExMTE6MjlyMjl=")
				.post(entity);
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void testHandleTokenRequest_validParameters_accessTokenEquals123456()
	{
		MultivaluedMap<String, String> tokenBody = new MultivaluedHashMap<String,String>();
		
		tokenBody.add("grant_type", "password");
		tokenBody.add("username", "123456");
		tokenBody.add("password", "654321");
		Entity<MultivaluedMap> entity = Entity.entity(
				tokenBody, 
				MediaType.APPLICATION_FORM_URLENCODED);
		Response response = target("/token")
				.request()
				.header("Authorization", "Basic MTExMTE6MjlyMjl=")
				.post(entity);
		TokenResponse tokenResponse = response.readEntity(TokenResponse.class);
		assertEquals(tokenResponse.getAccessToken(), "123456");
	}
	
	@Test
	public void testHandleTokenRequest_validParameters_tokenTypeEqualsBearer()
	{
		MultivaluedMap<String, String> tokenBody = new MultivaluedHashMap<String,String>();
		
		tokenBody.add("grant_type", "password");
		tokenBody.add("username", "123456");
		tokenBody.add("password", "654321");
		Entity<MultivaluedMap> entity = Entity.entity(
				tokenBody, 
				MediaType.APPLICATION_FORM_URLENCODED);
		Response response = target("/token")
				.request()
				.header("Authorization", "Basic MTExMTE6MjlyMjl=")
				.post(entity);
		TokenResponse tokenResponse = response.readEntity(TokenResponse.class);
		assertEquals(tokenResponse.getTokenType(), "Bearer");
	}
}
