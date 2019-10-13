package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScopeStore 
{
	private static ScopeStore scopeStore = new ScopeStore();
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	public static ScopeStore getInstance() { return scopeStore; }
	
	public List<String> getValidScopes(String clientId) 
	{ 
		Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        List<String> validScopes = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetValidScopes(?)}");
            callableStatement.setString(1, clientId);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
            	if (validScopes == null) { validScopes = new ArrayList<>(); }
                validScopes.add(rs.getString(1));
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
        return validScopes; 
	}
}
