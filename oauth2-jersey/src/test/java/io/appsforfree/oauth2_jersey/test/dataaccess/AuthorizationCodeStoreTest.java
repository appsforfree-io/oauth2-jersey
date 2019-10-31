package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.time.Instant;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.dataaccess.AuthorizationCodeStore;
import io.appsforfree.oauth2_jersey.domain.AuthorizationCode;

public class AuthorizationCodeStoreTest extends DBTestCase
{
	public AuthorizationCodeStoreTest(String name)
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
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("authorizationCode.xml"));
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
		assertNotNull(AuthorizationCodeStore.getInstance());
	}
	
	@Test
	public void testGetInstance_returnsSameInstance()
	{
		AuthorizationCodeStore instance1 = AuthorizationCodeStore.getInstance();
		AuthorizationCodeStore instance2 = AuthorizationCodeStore.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testGetAuthorizationCode_authorizationCodeDoesntExist_returnsNull()
	{
		assertNull(AuthorizationCodeStore.getInstance().getAuthorizationCode("authorizationCodeThatDoesntExist"));
	}
	
	@Test
	public void testGetAuthorizationCode_authorizationCodeExists_returnsNotNull()
	{
		assertNotNull(AuthorizationCodeStore.getInstance().getAuthorizationCode("anAuthorizationCode"));
	}
	
	@Test
	public void testGetAuthorizationCode_authorizationCodeExists_codeEqualsAnAuthorizationCode()
	{
		assertEquals(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("anAuthorizationCode")
					.getCode(), 
				"anAuthorizationCode");
	}
	
	@Test
	public void testGetAuthorizationCode_authorizationCodeExists_clientIdEquals12345()
	{
		assertEquals(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("anAuthorizationCode")
					.getClientId(), 
				"12345");
	}
	
	@Test
	public void testGetAuthorizationCode_authorizationCodeExists_usernameEqualsMyUsername()
	{
		assertEquals(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("anAuthorizationCode")
					.getUsername(), 
				"myUsername");
	}
	
	@Test
	public void testSaveAuthorizationCode_clientIdIsInvalid_getAuthorizationCodeReturnsNull()
	{
		AuthorizationCode authorizationCode = new AuthorizationCode(
				"newCode", 
				"54321", 
				"http://locahost:8080/callback",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername");
		AuthorizationCodeStore.getInstance().saveAuthorizationCode(authorizationCode);
		assertNull(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("newCode"));
	}
	
	@Test
	public void testSaveAuthorizationCode_usernameIsInvalid_getAuthorizationCodeReturnsNull()
	{
		AuthorizationCode authorizationCode = new AuthorizationCode(
				"newCode", 
				"12345", 
				"http://locahost:8080/callback",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"invalidUsername");
		AuthorizationCodeStore.getInstance().saveAuthorizationCode(authorizationCode);
		assertNull(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("newCode"));
	}
	
	@Test
	public void testSaveAuthorizationCode_validAccessToken_getAuthorizationCodeReturnsNotNull()
	{
		AuthorizationCode authorizationCode = new AuthorizationCode(
				"newCode", 
				"12345", 
				"http://locahost:8080/callback",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername");
		AuthorizationCodeStore.getInstance().saveAuthorizationCode(authorizationCode);
		assertNotNull(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("newCode"));
	}
	
	@Test
	public void testSaveAuthorizationCode_validAuthorizationCode_codeEqualsNewCode()
	{
		AuthorizationCode authorizationCode = new AuthorizationCode(
				"newCode", 
				"12345", 
				"http://locahost:8080/callback",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername");
		AuthorizationCodeStore.getInstance().saveAuthorizationCode(authorizationCode);
		assertEquals(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("newCode")
					.getCode(), 
					"newCode");
	}
	
	@Test
	public void testSaveAuthorizationCode_validAuthorizationCode_clientIdEquals12345()
	{
		AuthorizationCode authorizationCode = new AuthorizationCode(
				"newCode", 
				"12345", 
				"http://locahost:8080/callback",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername");
		AuthorizationCodeStore.getInstance().saveAuthorizationCode(authorizationCode);
		assertEquals(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("newCode")
					.getClientId(), 
					"12345");
	}
	
	@Test
	public void testAuthorizationCode_validAuthorizationCode_usernameEqualsMyUsername()
	{
		AuthorizationCode authorizationCode = new AuthorizationCode(
				"newCode", 
				"12345", 
				"http://locahost:8080/callback",
				Instant.parse("2019-10-14T07:53:00Z"),
				Instant.parse("2019-10-14T08:53:00Z"),
				"myUsername");
		AuthorizationCodeStore.getInstance().saveAuthorizationCode(authorizationCode);
		assertEquals(
				AuthorizationCodeStore
					.getInstance()
					.getAuthorizationCode("newCode")
					.getUsername(), 
					"myUsername");
	}
	
	@Test
	public void testDeleteAuthorizationCode_codeEqualsAnAuthorizationCode_getAccessTokenReturnsNull()
	{
		AuthorizationCodeStore.getInstance().deleteAuthorizationCode("anAuthorizationCode");
		assertNull(AuthorizationCodeStore.getInstance().getAuthorizationCode("anAuthorizationCode"));
	}
}
