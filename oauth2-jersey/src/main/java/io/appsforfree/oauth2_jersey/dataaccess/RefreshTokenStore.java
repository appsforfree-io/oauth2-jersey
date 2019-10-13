package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import io.appsforfree.oauth2_jersey.domain.RefreshToken;

public class RefreshTokenStore 
{
	private static RefreshTokenStore refreshTokenStore = new RefreshTokenStore();
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	public static RefreshTokenStore getInstance() { return refreshTokenStore; }
	
	public void saveRefreshToken(RefreshToken refreshToken) 
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call SaveRefreshToken(?, ?, ?, ?, ?)}");
            callableStatement.setString(1, refreshToken.getRefreshToken());
            callableStatement.setString(2, refreshToken.getClientId());
            callableStatement.setTimestamp(3, Timestamp.from(refreshToken.getIssuedAt()));
            callableStatement.setTimestamp(4, Timestamp.from(refreshToken.getExpiresOn()));
            callableStatement.setString(5, refreshToken.getUsername());
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
	
	public void deleteRefreshToken(String refreshToken) 
	{
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call DeleteRefreshToken(?)}");
            callableStatement.setString(1, refreshToken);
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
	
	public RefreshToken getRefreshToken(String refreshToken) 
	{ 
		Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        RefreshToken rt = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetRefreshToken(?)}");
            callableStatement.setString(1, refreshToken);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
                String clientId = rs.getString(2);
                Instant issuedAt = rs.getTimestamp(3).toInstant();
                Instant expiresOn = rs.getTimestamp(4).toInstant();
                String username = rs.getString(5);
                rt = new RefreshToken(
                		refreshToken, 
                		clientId,
                		issuedAt,
                		expiresOn,
                		username);
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
        return rt; 
	}
}
