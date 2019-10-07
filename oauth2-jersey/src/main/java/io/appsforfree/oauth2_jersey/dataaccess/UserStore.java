package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.appsforfree.oauth2_jersey.domain.User;

public class UserStore 
{
	private static UserStore userStore = new UserStore();
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	public static UserStore getInstance()
	{
		return userStore;
	}
	
	public void saveUser(User user)
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call SaveUser(?, ?)}");
            callableStatement.setString(1, user.getUsername());
            callableStatement.setString(2, user.getPassword());
            callableStatement.execute();
        }
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            databaseManager.close(connection, callableStatement, null);
        }
	}
	
	public void removeUser(String username)
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call DeleteUser(?)}");
            callableStatement.setString(1, username);
            callableStatement.execute();
        }
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            databaseManager.close(connection, callableStatement, null);
        }
	}
	
	public User getUser(String username)
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        User user = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetUser(?)}");
            callableStatement.setString(1, username);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
                String password = rs.getString(2);
                user = new User(
                        username,
                        password);
                break;
            }
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            databaseManager.close(connection, callableStatement, rs);
        }
        return user;
	}
}
