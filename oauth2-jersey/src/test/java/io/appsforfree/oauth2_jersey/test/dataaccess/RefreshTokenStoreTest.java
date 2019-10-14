package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.time.Instant;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.dataaccess.RefreshTokenStore;
import io.appsforfree.oauth2_jersey.domain.RefreshToken;

public class RefreshTokenStoreTest extends DBTestCase
{
	public RefreshTokenStoreTest(String name)
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
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("refreshToken.xml"));
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
		assertNotNull(RefreshTokenStore.getInstance());
	}
	
	@Test
	public void testGetInstance_returnsSameInstance()
	{
		RefreshTokenStore instance1 = RefreshTokenStore.getInstance();
		RefreshTokenStore instance2 = RefreshTokenStore.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenDoesntExist_returnsNull()
	{
		assertNull(RefreshTokenStore.getInstance().getRefreshToken("aaaaaaaa"));
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenExists_returnsNotNull()
	{
		assertNotNull(RefreshTokenStore.getInstance().getRefreshToken("abcdefghijklmnopqrstuvwxyz"));
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenExists_refreshTokenEqualsAbcdefghijklmnopqrstuvwxyz()
	{
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("abcdefghijklmnopqrstuvwxyz")
					.getRefreshToken(), 
				"abcdefghijklmnopqrstuvwxyz");
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenExists_clientIdEquals12345()
	{
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("abcdefghijklmnopqrstuvwxyz")
					.getClientId(), 
				"12345");
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenExists_issuedAtEquals1234567()
	{
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("abcdefghijklmnopqrstuvwxyz")
					.getIssuedAt(), 
				Instant.parse("2019-10-13T20:21:00Z"));
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenExists_expiresOnEquals12345678()
	{
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("abcdefghijklmnopqrstuvwxyz")
					.getExpiresOn(), 
				Instant.parse("2019-10-13T20:22:00Z"));
	}
	
	@Test
	public void testGetRefreshToken_refreshTokenExists_usernameEqualsMyUsername()
	{
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("abcdefghijklmnopqrstuvwxyz")
					.getUsername(), 
				"myUsername");
	}
	
	@Test
	public void testSaveRefreshToken_invalidClientId_getRefreshTokenReturnsNull()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"54321",
				Instant.parse("2019-10-13T22:24:00Z"),
				Instant.parse("2019-10-13T22:25:00Z"),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertNull(RefreshTokenStore.getInstance().getRefreshToken("newRefreshToken"));
	}
	
	@Test
	public void testSaveRefreshToken_invalidUsername_getRefreshTokenReturnsNull()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.parse("2019-10-13T22:24:00Z"),
				Instant.parse("2019-10-13T22:25:00Z"),
				"invalidUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertNull(RefreshTokenStore.getInstance().getRefreshToken("newRefreshToken"));
	}
	
	@Test
	public void testSaveRefreshToken_validClientIdAndValidUsername_getRefreshTokenReturnsNotNull()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.parse("2019-10-13T22:24:00Z"),
				Instant.parse("2019-10-13T22:25:00Z"),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertNotNull(RefreshTokenStore.getInstance().getRefreshToken("newRefreshToken"));
	}
	
	@Test
	public void testSaveRefreshToken_validClientIdAndValidUsername_refreshTokenEqualsNewRefreshToken()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.ofEpochSecond(123456789),
				Instant.ofEpochSecond(987654321),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("newRefreshToken")
					.getRefreshToken(), 
				"newRefreshToken");
	}
	
	@Test
	public void testSaveRefreshToken_validClientIdAndValidUsername_clientIdEquals12345()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.parse("2019-10-13T22:24:00Z"),
				Instant.parse("2019-10-13T22:25:00Z"),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("newRefreshToken")
					.getClientId(), 
				"12345");
	}
	
	@Test
	public void testSaveRefreshToken_validClientIdAndValidUsername_usernameEqualsMyUsername()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.parse("2019-10-13T22:24:00Z"),
				Instant.parse("2019-10-13T22:25:00Z"),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("newRefreshToken")
					.getUsername(), 
				"myUsername");
	}
	
	@Test
	public void testSaveRefreshToken_validClientIdAndValidUsername_issuedAtEquals123456789()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.ofEpochSecond(123456789),
				Instant.ofEpochSecond(987654321),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("newRefreshToken")
					.getIssuedAt(), 
				Instant.ofEpochSecond(123456789));
	}
	
	@Test
	public void testSaveRefreshToken_validClientIdAndValidUsername_expiresOnEquals987654321()
	{
		RefreshToken refreshToken = new RefreshToken(
				"newRefreshToken", 
				"12345", 
				Instant.ofEpochSecond(123456789),
				Instant.ofEpochSecond(987654321),
				"myUsername");
		RefreshTokenStore.getInstance().saveRefreshToken(refreshToken);
		assertEquals(
				RefreshTokenStore
					.getInstance()
					.getRefreshToken("newRefreshToken")
					.getExpiresOn(), 
				Instant.ofEpochSecond(987654321));
	}
	
	@Test
	public void testDeleteRefreshToken_refreshTokenEqualsAbcdefghijklmnopqrstuvwxyz_getRefreshTokenReturnsNull()
	{
		RefreshTokenStore.getInstance().deleteRefreshToken("abcdefghijklmnopqrstuvwxyz");
		assertNull(RefreshTokenStore.getInstance().getRefreshToken("abcdefghijklmnopqrstuvwxyz"));
	}
}
