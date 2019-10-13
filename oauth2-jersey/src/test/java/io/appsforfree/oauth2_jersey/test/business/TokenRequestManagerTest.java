package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import org.junit.jupiter.api.Test;

import io.appsforfree.oauth2_jersey.business.TokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.request.PasswordTokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidClientException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidRequestException;
import io.appsforfree.oauth2_jersey.domain.exception.InvalidScopeException;
import io.appsforfree.oauth2_jersey.domain.exception.UnauthorizedClientException;

public class TokenRequestManagerTest extends DBTestCase
{
	public TokenRequestManagerTest(String name)
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
		assertNotNull(TokenRequestManager.getInstance());
	}
	
	@Test
	public void testGetInstance_returnSameInstance()
	{
		TokenRequestManager instance1 = TokenRequestManager.getInstance();
		TokenRequestManager instance2 = TokenRequestManager.getInstance();
		assertEquals(instance1, instance2);
	}
	
	@Test
	public void test_generateAccessToken_tokenRequestIsNull_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			TokenRequestManager.getInstance().generateAccessToken(null);
		});
	}
	
	@Test
	public void test_generateAccessToken_invalidTokenRequest_throwInvalidRequestException() throws Exception
	{
		assertThrows(InvalidRequestException.class, () -> {
			TokenRequest passwordTokenRequest = new PasswordTokenRequest(
					null, 
					null, 
					null, 
					null, 
					null);
			TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest);
		});
	}
	
	@Test
	public void test_generateAccessToken_invalidClientId_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest passwordTokenRequest = new PasswordTokenRequest(
					"myUsername", 
					"myPassword", 
					"123456", 
					"54321", 
					"profile");
			TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest);
		});
	}
	
	@Test
	public void test_generateAccessToken_invalidClientSecret_throwInvalidClientException() throws Exception
	{
		assertThrows(InvalidClientException.class, () -> {
			TokenRequest passwordTokenRequest = new PasswordTokenRequest(
					"myUsername", 
					"myPassword", 
					"12345", 
					"543210", 
					"profile");
			TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest);
		});
	}
	
	@Test
	public void test_generateAccessToken_clientDoesntSupportPasswordGrantType_throwUnauthorizedClientException() throws Exception
	{
		assertThrows(UnauthorizedClientException.class, () -> {
			TokenRequest passwordTokenRequest = new PasswordTokenRequest(
					"myUsername", 
					"myPassword", 
					"123456", 
					"654321", 
					"profile");
			TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest);
		});
	}
	
	@Test
	public void test_generateAccessToken_clientDoesntSupportScope1_throwInvalidScopeException() throws Exception
	{
		assertThrows(InvalidScopeException.class, () -> {
			TokenRequest passwordTokenRequest = new PasswordTokenRequest(
					"myUsername", 
					"myPassword", 
					"12345", 
					"54321", 
					"scope1");
			TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest);
		});
	}
	
	@Test
	public void test_generateAccessToken_validTokenRequest_returnNotNull() throws Exception
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				"myUsername", 
				"myPassword", 
				"12345", 
				"54321", 
				"profile");
		assertNotNull(TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest));
	}
	
	@Test
	public void test_generateAccessToken_validTokenRequest_tokenTypeEqualsBearer() throws Exception
	{
		TokenRequest passwordTokenRequest = new PasswordTokenRequest(
				"myUsername", 
				"myPassword", 
				"12345", 
				"54321", 
				"profile");
		assertEquals(
				TokenRequestManager.getInstance().generateAccessToken(passwordTokenRequest).getTokenType(),
				"Bearer");
	}
}
