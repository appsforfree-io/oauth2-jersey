package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import io.appsforfree.oauth2_jersey.domain.AuthorizationCode;

public class AuthorizationCodeStore 
{
	private static AuthorizationCodeStore authorizationCodeStore = new AuthorizationCodeStore();
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	public static AuthorizationCodeStore getInstance() { return authorizationCodeStore; }
	
	public void saveAuthorizationCode(AuthorizationCode authorizationCode) 
	{  
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call SaveAuthorizationCode(?, ?, ?, ?, ?, ?)}");
            callableStatement.setString(1, authorizationCode.getCode());
            callableStatement.setString(2, authorizationCode.getClientId());
            callableStatement.setString(3, authorizationCode.getRedirectUri());
            callableStatement.setTimestamp(4, Timestamp.from(authorizationCode.getIssuedAt()));
            callableStatement.setTimestamp(5, Timestamp.from(authorizationCode.getExpiresOn()));
            callableStatement.setString(6, authorizationCode.getUsername());
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
	
	public AuthorizationCode getAuthorizationCode(String code) 
	{ 
		Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        AuthorizationCode ac = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetAuthorizationCode(?)}");
            callableStatement.setString(1, code);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
                String clientId = rs.getString(2);
                String redirectUri = rs.getString(3);
                Instant issuedAt = rs.getTimestamp(4).toInstant();
                Instant expiresOn = rs.getTimestamp(5).toInstant();
                String username = rs.getString(6);
                ac = new AuthorizationCode(
                		code, 
                		clientId,
                		redirectUri,
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
        return ac; 
	}
	
	public void deleteAuthorizationCode(String code) 
	{  
		Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call DeleteAuthorizationCode(?)}");
            callableStatement.setString(1, code);
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
}
