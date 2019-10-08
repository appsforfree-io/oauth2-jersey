package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.appsforfree.oauth2_jersey.domain.AccessToken;

public class AccessTokenStore 
{
	private static AccessTokenStore accessTokenStore = new AccessTokenStore();
	
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	public static AccessTokenStore getInstance() { return accessTokenStore; }
	
	public void saveAccessToken(AccessToken accessToken) 
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call SaveAccessToken(?, ?, ?, ?)}");
            callableStatement.setString(1, accessToken.getAccessToken());
            callableStatement.setString(2, accessToken.getClientId());
            callableStatement.setString(3, accessToken.getUsername());
            callableStatement.setString(4, accessToken.getRefreshToken());
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
	
	public void deleteAccessToken(String accessToken) 
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call DeleteAccessToken(?)}");
            callableStatement.setString(1, accessToken);
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
	
	public AccessToken getAccessToken(String accessToken) 
	{ 
		Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        AccessToken at = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetAccessToken(?)}");
            callableStatement.setString(1, accessToken);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
                String clientId = rs.getString(2);
                String username = rs.getString(3);
                String refreshToken = rs.getString(4);
                at = new AccessToken(
                		accessToken, 
                		clientId, 
                		username,
                		refreshToken);
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
        return at; 
	}
}
