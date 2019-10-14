package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;

import io.appsforfree.oauth2_jersey.business.RefreshTokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidGrantException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidScopeException;
import io.appsforfree.oauth2_jersey.domain.exception.UnauthorizedClientException;
import io.appsforfree.oauth2_jersey.domain.request.RefreshTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class RefreshTokenRequestManagerTest extends DBTestCase
{
	public RefreshTokenRequestManagerTest(String name)
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
	public void testGetInstance_returnsNotNull()
	{
		assertNotNull(RefreshTokenRequestManager.getInstance());
	}
	
	@Test
	public void testGetInstance_returnsSameInstance()
	{
		RefreshTokenRequestManager instance1 = RefreshTokenRequestManager.getInstance();
		RefreshTokenRequestManager instance2 = RefreshTokenRequestManager.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testHandleTokenRequest_tokenRequestIsNull_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			RefreshTokenRequestManager.getInstance().handleTokenRequest(null);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidTokenRequest_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			TokenRequest refreshTokenRequest = new RefreshTokenRequest(
					null, 
					null, 
					null, 
					null);
			RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientId_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest refreshTokenRequest = new RefreshTokenRequest(
					"1234567890",  
					"123456", 
					"54321", 
					"profile");
			RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientSecret_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest refreshTokenRequest = new RefreshTokenRequest(
					"1234567890", 
					"12345", 
					"543210", 
					"profile");
			RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_clientDoesntSupportPasswordGrantType_throwUnauthorizedClientException() throws Exception
	{
		assertThrows(UnauthorizedClientException.class, () -> {
			TokenRequest refreshTokenRequest = new RefreshTokenRequest(
					"1234567890", 
					"123456", 
					"654321", 
					"profile");
			RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_clientDoesntSupportScope1_throwInvalidScopeException() throws Exception
	{
		assertThrows(InvalidScopeException.class, () -> {
			TokenRequest refreshTokenRequest = new RefreshTokenRequest(
					"1234567890", 
					"12345", 
					"54321", 
					"scope1");
			RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_refreshTokenDoesntExist_throwInvalidGrantException() throws Exception
	{
		assertThrows(InvalidGrantException.class, () -> {
			TokenRequest refreshTokenRequest = new RefreshTokenRequest(
					"1234567890",
					"12345", 
					"54321", 
					"profile");
			RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_validTokenRequest_returnNotNull() throws Exception
	{
		TokenRequest refreshTokenRequest = new RefreshTokenRequest(
				"abcdefghijklmnopqrstuvwxyz", 
				"12345", 
				"54321", 
				"profile");
		assertNotNull(RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest));
	}
	
	@Test
	public void testHandleTokenRequest_validTokenRequest_tokenTypeEqualsBearer() throws Exception
	{
		TokenRequest refreshTokenRequest = new RefreshTokenRequest(
				"abcdefghijklmnopqrstuvwxyz", 
				"12345", 
				"54321", 
				"profile");
		assertEquals(
				RefreshTokenRequestManager.getInstance().handleTokenRequest(refreshTokenRequest).getTokenType(),
				"Bearer");
	}
}
