package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.dataaccess.ClientStore;
import io.appsforfree.oauth2_jersey.domain.Client;

public class ClientStoreTest extends DBTestCase
{
	public ClientStoreTest(String name)
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
		return new FlatXmlDataSetBuilder().build(this.getClass().getResourceAsStream("client.xml"));
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
    public void testGetClientId_12345_clientIsNotNull() 
	{
        assertNotNull(ClientStore.getInstance().getClient("12345"));
    }
	
	@Test
    public void testGetClientId_12345_clientIdEquals12345() 
	{
        assertEquals(
        		ClientStore.getInstance().getClient("12345").getClientId(), 
        		"12345");
    }
	
	@Test
    public void testGetClientId_12345_clientSecretEquals54321() 
	{
		assertEquals(
        		ClientStore.getInstance().getClient("12345").getClientSecret(), 
        		"54321");
    }
	
	@Test
    public void testGetClientId_12345_clientTypeEqualsConfidential() 
	{
		assertEquals(
        		ClientStore.getInstance().getClient("12345").getClientType(), 
        		"confidential");
    }
	
	@Test
    public void testSaveClient_clientIsNotNull() 
	{
		Client client = new Client("11111", "22222", "confidential");
		ClientStore.getInstance().saveClient(client);
        assertNotNull(ClientStore.getInstance().getClient("11111"));
    }
	
	@Test
    public void testSaveClient_clientIdEquals11111() 
	{
		Client client = new Client("11111", "22222", "confidential");
		ClientStore.getInstance().saveClient(client);
        assertEquals(
        		ClientStore.getInstance().getClient("11111").getClientId(), 
        		"11111");
    }
	
	@Test
    public void testSaveClient_clientSecretEquals22222() 
	{
		Client client = new Client("11111", "22222", "confidential");
		ClientStore.getInstance().saveClient(client);
        assertEquals(
        		ClientStore.getInstance().getClient("11111").getClientSecret(), 
        		"22222");
    }
	
	@Test
    public void testSaveClient_clientTypeEqualsConfidential() 
	{
		Client client = new Client("11111", "22222", "confidential");
		ClientStore.getInstance().saveClient(client);
        assertEquals(
        		ClientStore.getInstance().getClient("11111").getClientType(), 
        		"confidential");
    }
	
	@Test
    public void testSaveClient_clientTypeEqualsPublic() 
	{
		Client client = new Client("11111", "22222", "public");
		ClientStore.getInstance().saveClient(client);
        assertEquals(
        		ClientStore.getInstance().getClient("11111").getClientType(), 
        		"public");
    }
	
	@Test
    public void testSaveClient_invalidClientType_getClientReturnsNull() 
	{
		Client client = new Client("11111", "22222", "abcdef");
		ClientStore.getInstance().saveClient(client);
        assertNull(ClientStore.getInstance().getClient("11111"));
    }
	
	@Test
    public void testRemoveClient_getClientReturnsNull() 
	{
		Client client = new Client("11111", "22222", "public");
		ClientStore.getInstance().saveClient(client);
		assertNotNull(ClientStore.getInstance().getClient("11111"));
		ClientStore.getInstance().removeClient("11111");
        assertNull(ClientStore.getInstance().getClient("11111"));
    }
}
