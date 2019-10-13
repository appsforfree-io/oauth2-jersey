package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.dataaccess.ScopeStore;

public class ScopeStoreTest extends DBTestCase
{
	public ScopeStoreTest(String name)
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
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("scope.xml"));
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
		assertNotNull(ScopeStore.getInstance());
	}
	
	@Test
	public void testGetInstance_returnsSameInstance()
	{
		ScopeStore instance1 = ScopeStore.getInstance();
		ScopeStore instance2 = ScopeStore.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testGetValidScope_invalidClientId_returnsNull()
	{
		assertNull(ScopeStore.getInstance().getValidScopes("invalidClientId"));
	}
	
	@Test
	public void testGetValidScope_validClientId_returnsNotNull()
	{
		assertNotNull(ScopeStore.getInstance().getValidScopes("12345"));
	}
	
	@Test
	public void testGetValidScope_validClientId_containsScope1()
	{
		List<String> validScopes = ScopeStore.getInstance().getValidScopes("12345");
		assertTrue(validScopes.contains("scope1"));
	}
	
	@Test
	public void testGetValidScope_validClientId_containsScope2()
	{
		List<String> validScopes = ScopeStore.getInstance().getValidScopes("12345");
		assertTrue(validScopes.contains("scope2"));
	}
	
	@Test
	public void testGetValidScope_validClientId_doesNotContainScope3()
	{
		List<String> validScopes = ScopeStore.getInstance().getValidScopes("12345");
		assertFalse(validScopes.contains("scope3"));
	}
}
