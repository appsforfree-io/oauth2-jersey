package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;

import io.appsforfree.oauth2_jersey.business.ClientCredentialsRequestManager;
import io.appsforfree.oauth2_jersey.business.TokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidScopeException;
import io.appsforfree.oauth2_jersey.domain.exception.UnauthorizedClientException;
import io.appsforfree.oauth2_jersey.domain.request.ClientCredentialsTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class ClientCredentialsRequestManagerTest extends DBTestCase 
{
	public ClientCredentialsRequestManagerTest(String name)
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
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/accessToken.xml"));
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
	public void testGetInstance_returnNotNull()
	{
		assertNotNull(ClientCredentialsRequestManager.getInstance());
	}
	
	@Test
	public void testGetInstance_returnSameInstance()
	{
		TokenRequestManager instance1 = ClientCredentialsRequestManager.getInstance();
		TokenRequestManager instance2 = ClientCredentialsRequestManager.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testHandleTokenRequest_tokenRequestIsNull_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			ClientCredentialsRequestManager.getInstance().handleTokenRequest(null);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidTokenRequest_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest(
					null, 
					null, 
					null);
			ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientId_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest( 
					"1234567", 
					"54321", 
					"profile");
			ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientSecret_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest(
					"12345", 
					"543210", 
					"profile");
			ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_clientDoesntSupportClientCredentialsGrantType_throwUnauthorizedClientException() throws Exception
	{
		assertThrows(UnauthorizedClientException.class, () -> {
			TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest(
					"12345", 
					"54321", 
					"profile");
			ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_clientDoesntSupportScope1_throwInvalidScopeException() throws Exception
	{
		assertThrows(InvalidScopeException.class, () -> {
			TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest(
					"123456", 
					"654321", 
					"scope1");
			ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_validTokenRequest_returnNotNull() throws Exception
	{
		TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest(
				"123456", 
				"654321", 
				"profile");
		assertNotNull(ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest));
	}
	
	@Test
	public void testHandleTokenRequest_validTokenRequest_tokenTypeEqualsBearer() throws Exception
	{
		TokenRequest clientCredentialsTokenRequest = new ClientCredentialsTokenRequest(
				"123456", 
				"654321", 
				"profile");
		assertEquals(
				ClientCredentialsRequestManager.getInstance().handleTokenRequest(clientCredentialsTokenRequest).getTokenType(),
				"Bearer");
	}
}
