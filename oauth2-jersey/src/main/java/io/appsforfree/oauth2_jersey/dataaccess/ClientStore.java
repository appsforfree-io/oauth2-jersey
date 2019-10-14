package io.appsforfree.oauth2_jersey.dataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.appsforfree.oauth2_jersey.domain.Client;

public class ClientStore 
{
	private static ClientStore clientStore = new ClientStore();
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    
    public static ClientStore getInstance()
    {
        return clientStore;
    }
    
    public void saveClient(Client client)
    {
    	Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call SaveClient(?, ?, ?)}");
            callableStatement.setString(1, client.getClientId());
            callableStatement.setString(2, client.getClientSecret());
            callableStatement.setString(3, client.getClientType());
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
    
    public void removeClient(String clientId)
    {
    	Connection connection = null;
        CallableStatement callableStatement = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call RemoveClient(?)}");
            callableStatement.setString(1, clientId);
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
    
    public Client getClient(String clientId)
    {
    	Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        Client client = null;
        try 
        {
            connection = this.databaseManager.getConnection();
            callableStatement = connection.prepareCall("{call GetClient(?)}");
            callableStatement.setString(1, clientId);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            while (rs.next()) 
            {
                String clientSecret = rs.getString(2);
                String clientType = rs.getString(3);
                client = new Client(
                        clientId,
                        clientSecret,
                        clientType);
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
        return client;
    }
}
