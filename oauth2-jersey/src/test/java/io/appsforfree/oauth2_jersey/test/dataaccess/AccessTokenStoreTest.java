package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.time.Instant;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.dataaccess.AccessTokenStore;
import io.appsforfree.oauth2_jersey.domain.AccessToken;

public class AccessTokenStoreTest extends DBTestCase
{
	public AccessTokenStoreTest(String name)
	{
		super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:8889/oauth2db");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception 
	{
		// TODO Auto-generated method stub
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("accessToken.xml"));
	}
	
	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception 
	{
        return DatabaseOperation.REFRESH;
    }
 
	@Override
    protected DatabaseOperation getTearDownOperation() throws Exception 
	{
        return DatabaseOperation.DELETE_ALL;
    }
	
	@Test
	public void testGetInstance_returnsNotNull()
	{
		assertNotNull(AccessTokenStore.getInstance());
	}
	
	@Test
	public void testGetInstance_returnsSameInstance()
	{
		AccessTokenStore instance1 = AccessTokenStore.getInstance();
		AccessTokenStore instance2 = AccessTokenStore.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testGetAccessToken_accessTokenDoesntExist_returnsNull()
	{
		assertNull(AccessTokenStore.getInstance().getAccessToken("accessTokenThatDoesntExist"));
	}
	
	@Test
	public void testGetAccessToken_accessTokenExists_returnsNotNull()
	{
		assertNotNull(AccessTokenStore.getInstance().getAccessToken("accessTokenWithRefreshToken"));
	}
	
	@Test
	public void testGetAccessToken_accessTokenExists_accessTokenEqualsAccessTokenWithRefreshToken()
	{
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("accessTokenWithRefreshToken")
					.getAccessToken(), 
				"accessTokenWithRefreshToken");
	}
	
	@Test
	public void testGetAccessToken_accessTokenExists_clientIdEquals12345()
	{
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("accessTokenWithRefreshToken")
					.getClientId(), 
				"12345");
	}
	
	@Test
	public void testGetAccessToken_accessTokenExists_usernameEqualsMyUsername()
	{
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("accessTokenWithRefreshToken")
					.getUsername(), 
				"myUsername");
	}
	
	@Test
	public void testGetAccessToken_accessTokenExists_refreshTokenEqualsAbcdefghijklmnopqrstuvwxyz()
	{
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("accessTokenWithRefreshToken")
					.getRefreshToken(), 
				"abcdefghijklmnopqrstuvwxyz");
	}
	
	@Test
	public void testSaveAccessToken_clientIdIsInvalid_getAccessTokenReturnsNull()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"54321", 
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"abcdefghijklmnopqrtsuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertNull(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken"));
	}
	
	@Test
	public void testSaveAccessToken_usernameIsInvalid_getAccessTokenReturnsNull()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345", 
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"invalidUsername", 
				"abcdefghijklmnopqrtsuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertNull(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken"));
	}
	
	@Test
	public void testSaveAccessToken_refreshTokenIsInvalid_getAccessTokenReturnsNull()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"invalidRefreshToken");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertNull(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken"));
	}
	
	@Test
	public void testSaveAccessToken_validAccessToken_getAccessTokenReturnsNotNull()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"abcdefghijklmnopqrstuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertNotNull(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken"));
	}
	
	@Test
	public void testSaveAccessToken_validAccessToken_accessTokenEqualsNewAccessToken()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"abcdefghijklmnopqrstuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken")
					.getAccessToken(), 
					"newAccessToken");
	}
	
	@Test
	public void testSaveAccessToken_validAccessToken_clientIdEquals12345()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"abcdefghijklmnopqrstuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken")
					.getClientId(), 
					"12345");
	}
	
	@Test
	public void testSaveAccessToken_validAccessToken_usernameEqualsMyUsername()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"abcdefghijklmnopqrstuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken")
					.getUsername(), 
					"myUsername");
	}
	
	@Test
	public void testSaveAccessToken_validAccessToken_refreshTokenEqualsAbcdefghijklmnopqrstuvwxyz()
	{
		AccessToken accessToken = new AccessToken(
				"newAccessToken", 
				"12345", 
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername", 
				"abcdefghijklmnopqrstuvwxyz");
		AccessTokenStore.getInstance().saveAccessToken(accessToken);
		assertEquals(
				AccessTokenStore
					.getInstance()
					.getAccessToken("newAccessToken")
					.getRefreshToken(), 
					"abcdefghijklmnopqrstuvwxyz");
	}
	
	@Test
	public void testDeleteAccessToken_accessTokenEqualsAccessTokenWithRefreshToken_getAccessTokenReturnsNull()
	{
		AccessTokenStore.getInstance().deleteAccessToken("accessTokenWithRefreshToken");
		assertNull(AccessTokenStore.getInstance().getAccessToken("accessTokenWithRefreshToken"));
	}
}
