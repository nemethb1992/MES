package phoenix.mes.content;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseEntities {

	protected static Connection conn;
	DatabaseEntities()
    {
		
    }
    //Initialize values

    protected static void dbOpen()
    {
    	try {
        	Class.forName("org.postgresql.Driver");
    	}catch(ClassNotFoundException c) {
    		
    	}
    	try {
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.145.217/dmes", "mes", "jGbLv!nh+?zc346J");
		} catch (SQLException e) {
		}
    }
    public static void dbClose()
    {
        try{
            conn.close();
        }
        catch (SQLException e){
        }
    }
    public void sqlUpdate(String query)
    {
		dbOpen();
    	if (conn != null)
    	{

    		PreparedStatement preparedStmt = null;

    		try {
    			preparedStmt = conn.prepareStatement(query);
    			preparedStmt.executeUpdate();
    		} catch (SQLException e) {
    		} finally {
    			try {
    				preparedStmt.close();
    			}catch(SQLException e){	
    			}

    		}

    	}
    }
    public static ArrayList<String> sqlQuery(String query, String mezo)
    {  
    	ArrayList<String> li = new ArrayList<String>();

    	dbOpen();
    	if (conn != null)
    	{
    		try {
    			Statement stmt = conn.createStatement();
    			ResultSet rs = stmt.executeQuery(query);
    			while (rs.next())
    			{
    				li.add(rs.getString(mezo));
    			}
    			rs.close();
    			stmt.close();		
    			} 
    		catch (SQLException e) 
    		{
			e.printStackTrace();
    		}
    	}
		return li;
    }
    
}
