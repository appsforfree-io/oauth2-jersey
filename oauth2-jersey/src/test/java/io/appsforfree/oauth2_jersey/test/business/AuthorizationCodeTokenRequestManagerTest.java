package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;

import io.appsforfree.oauth2_jersey.business.AuthorizationCodeTokenRequestManager;
import io.appsforfree.oauth2_jersey.business.TokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidGrantException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.UnauthorizedClientException;
import io.appsforfree.oauth2_jersey.domain.request.AuthorizationCodeTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

public class AuthorizationCodeTokenRequestManagerTest extends DBTestCase 
{
	public AuthorizationCodeTokenRequestManagerTest(String name)
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
		assertNotNull(AuthorizationCodeTokenRequestManager.getInstance());
	}
	
	@Test
	public void testGetInstance_returnSameInstance()
	{
		TokenRequestManager instance1 = AuthorizationCodeTokenRequestManager.getInstance();
		TokenRequestManager instance2 = AuthorizationCodeTokenRequestManager.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void testHandleTokenRequest_tokenRequestIsNull_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(null);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidTokenRequest_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
					null, 
					null, 
					null, 
					null);
			AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidClientId_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
					"anAuthorizationCode", 
					"http://localhost:8080/redirect", 
					"123456789",
					"54321");
			AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_clientDoesntSupportAuthorizationCodeGrantType_throwUnauthorizedClientException() throws Exception
	{
		assertThrows(UnauthorizedClientException.class, () -> {
			TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
					"anAuthorizationCode", 
					"http://localhost:8080/redirect", 
					"123456",
					"654321");
			AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_codeDoesntExist_throwInvalidGrantException() throws Exception
	{
		assertThrows(InvalidGrantException.class, () -> {
			TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
					"myInvalidCode", 
					"http://localhost:8080/redirect", 
					"12345",
					"54321");
			AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_invalidRedirectUri_throwInvalidGrantException() throws Exception
	{
		assertThrows(InvalidGrantException.class, () -> {
			TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
					"anAuthorizationCode", 
					"http://localhost:8080/badRedirect", 
					"12345",
					"54321");
			AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest);
		});
	}
	
	@Test
	public void testHandleTokenRequest_validTokenRequest_returnNotNull() throws Exception
	{
		TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
				"anAuthorizationCode", 
				"http://localhost:8080/redirect", 
				"12345",
				"54321");
		assertNotNull(AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest));
	}
	
	@Test
	public void testHandleTokenRequest_validTokenRequest_tokenTypeEqualsBearer() throws Exception
	{
		TokenRequest authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
				"anAuthorizationCode", 
				"http://localhost:8080/redirect", 
				"12345",
				"54321");
		assertEquals(
				AuthorizationCodeTokenRequestManager.getInstance().handleTokenRequest(authorizationCodeTokenRequest).getTokenType(),
				"Bearer");
	}
}
