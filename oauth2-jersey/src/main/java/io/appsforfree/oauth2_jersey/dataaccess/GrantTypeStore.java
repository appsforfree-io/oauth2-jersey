package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrantTypeStore 
{
	private static GrantTypeStore grantTypeStore = new GrantTypeStore();
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	public static GrantTypeStore getInstance() { return grantTypeStore; }
	
	public List<String> getSupportedGrantTypes(String clientId) 
	{ 
		Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        List<String> grantTypes = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetSupportedGrantTypes(?)}");
            callableStatement.setString(1, clientId);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
            	if (grantTypes == null) { grantTypes = new ArrayList<>(); }
            	grantTypes.add(rs.getString(1));
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
        return grantTypes;
	}
}
