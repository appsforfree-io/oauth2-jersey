package io.appsforfree.oauth2_jersey.test.dataaccess;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import io.appsforfree.oauth2_jersey.dataaccess.UserStore;
import io.appsforfree.oauth2_jersey.domain.User;

public class UserStoreTest extends DBTestCase
{
	public UserStoreTest(String name)
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
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("user.xml"));
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
		assertNotNull(UserStore.getInstance());
	}
	
	@Test
	public void testGetInstance_instanceAlwaysTheSame()
	{
		UserStore firstInstance = UserStore.getInstance();
		UserStore secondInstance = UserStore.getInstance();
		assertEquals(firstInstance, secondInstance);
	}
	
	@Test
	public void testGetUser_usernameDoesntExist_returnsNull()
	{
		assertNull(UserStore.getInstance().getUser("12345"));
	}
	
	@Test
	public void testGetUser_usernameExists_returnsNotNull()
	{
		assertNotNull(UserStore.getInstance().getUser("myUsername"));
	}
	
	@Test
	public void testGetUser_usernameExists_usernameEqualsMyUsername()
	{
		User user = UserStore.getInstance().getUser("myUsername");
		assertEquals(user.getUsername(), "myUsername");
	}
	
	@Test
	public void testGetUser_usernameExists_passwordEqualsMyPassword()
	{
		User user = UserStore.getInstance().getUser("myUsername");
		assertEquals(user.getPassword(), "myPassword");
	}
	
	@Test
	public void testSaveUser_usernameEqualsMySecondUsername_getUserReturnsNotNull()
	{
		User user = new User("mySecondUsername", "mySecondPassword");
		UserStore.getInstance().saveUser(user);
		assertNotNull(UserStore.getInstance().getUser("mySecondUsername"));
	}
	
	@Test
	public void testSaveUser_usernameEqualsMySecondUsername_usernameEqualsMySecondUsername()
	{
		User user = new User("mySecondUsername", "mySecondPassword");
		UserStore.getInstance().saveUser(user);
		assertEquals(
				UserStore.getInstance().getUser("mySecondUsername").getUsername(), 
				"mySecondUsername");
	}
	
	@Test
	public void testSaveUser_usernameEqualsMySecondUsername_passwordEqualsMySecondPassword()
	{
		User user = new User("mySecondUsername", "mySecondPassword");
		UserStore.getInstance().saveUser(user);
		assertEquals(
				UserStore.getInstance().getUser("mySecondUsername").getPassword(), 
				"mySecondPassword");
	}
	
	@Test
	public void testDeleteUser_usernameEqualsMyUsername_getUserReturnsNull()
	{
		UserStore.getInstance().removeUser("myUsername");
		assertNull(UserStore.getInstance().getUser("myUsername"));
	}
}
