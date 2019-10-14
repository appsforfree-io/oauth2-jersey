package io.appsforfree.oauth2_jersey.dataaccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseManager 
{
	private BasicDataSource dataSource;
	private static DatabaseManager databaseManager = new DatabaseManager();
	private String driver;
	
	public static DatabaseManager getInstance()
	{
		return databaseManager;
	}
	
	private DatabaseManager()
	{	
		dataSource = new BasicDataSource();
		Properties dbProperties = new Properties();
		InputStream fis;
		try {
			fis = getClass().getClassLoader().getResourceAsStream("database.properties");
			dbProperties.load(fis);
			driver = dbProperties.getProperty("db.driver.class");
			dataSource.setUrl(dbProperties.getProperty("db.conn.url"));
			dataSource.setUsername(dbProperties.getProperty("db.username"));
			dataSource.setPassword(dbProperties.getProperty("db.password"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		try
		{
			Class.forName(driver);
			return dataSource.getConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void close(
			Connection connection, 
			CallableStatement callableStatement, 
			ResultSet rs)
	{
		try 
		{
			if (rs != null) { rs.close(); }
			if (callableStatement != null) { callableStatement.close(); }
			if (connection != null) { connection.close(); }
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
