package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.io.FileInputStream;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;

import io.appsforfree.oauth2_jersey.dataaccess.GrantTypeStore;

public class GrantTypeStoreTest extends DBTestCase 
{
	public GrantTypeStoreTest(String name)
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
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/grantType.xml"));
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
		assertNotNull(GrantTypeStore.getInstance());
	}
	
	@Test
	public void testGetInstance_returnsSameInstance()
	{
		GrantTypeStore instance1 = GrantTypeStore.getInstance();
		GrantTypeStore instance2 = GrantTypeStore.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testGetSupportedGrantType_invalidClientId_returnsNull()
	{
		assertNull(GrantTypeStore.getInstance().getSupportedGrantTypes("invalidClientId"));
	}
	
	@Test
	public void testGetSupportedGrantType_validClientId_returnsNotNull()
	{
		assertNotNull(GrantTypeStore.getInstance().getSupportedGrantTypes("12345"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals123456_containsPassword()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("12345");
		assertTrue(supportedGrantTypes.contains("password"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals12345_doesnotContainClientCredentials()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("12345");
		assertFalse(supportedGrantTypes.contains("client_credentials"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals12345_doesnotContainAuthorizationCode()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("12345");
		assertFalse(supportedGrantTypes.contains("authorization_code"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals123456_containsClientCredentials()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("123456");
		assertTrue(supportedGrantTypes.contains("client_credentials"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals123456_doesnotContainPassword()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("123456");
		assertFalse(supportedGrantTypes.contains("password"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals123456_doesnotContainAuthorizationCode()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("123456");
		assertFalse(supportedGrantTypes.contains("authorization_code"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals1234567_containsClientCredentials()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("1234567");
		assertTrue(supportedGrantTypes.contains("client_credentials"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals1234567_containsAuthorizationCode()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("1234567");
		assertTrue(supportedGrantTypes.contains("authorization_code"));
	}
	
	@Test
	public void testGetSupportedGrantType_clientIdEquals1234567_doesnotContainPassword()
	{
		List<String> supportedGrantTypes = GrantTypeStore.getInstance().getSupportedGrantTypes("1234567");
		assertFalse(supportedGrantTypes.contains("password"));
	}
}
