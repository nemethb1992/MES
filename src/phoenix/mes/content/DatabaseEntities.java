package phoenix.mes.content;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseEntities {

	private Connection conn;
	private Statement stmt;
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    String dbName = "jdbc:postgresql://localhost/struts_new";
    String dbDriver = "org.postgresql.Driver";
    String URL = "jdbc:postgresql://192.168.145.217/dmes"; 
    String USER = "mes";
    String PASS = "jGbLv!nh+?zc346J";
    
    public DatabaseEntities()
    {
    }
    //Initialize values

    public boolean dbOpen()
    {
    	boolean valid = false;
        try
        {
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            valid = true;
        	}
        	catch (ClassNotFoundException ex2) {
        		valid = false;
        		Logger.getLogger(DatabaseEntities.class.getName()).log(Level.SEVERE, null, ex2);
      } catch (SQLException e) {
          valid = false;
    	e.printStackTrace();
    }
        return valid;
    }
    private boolean dbClose()
    {
        try
        {
            conn.close();
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    public void SQLQueryExecute(String query)
    {
        if (this.dbOpen() == true)
        {
        	try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        dbClose();
    }
    public ArrayList<String> SQLQueryRead(String query, String mezo)
    {  
    	ArrayList<String> li = new ArrayList<String>();
    	if (this.dbOpen() == true)
    	{
    		try {
    			stmt = conn.createStatement();
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
    	dbClose();
		return li;
    }
    
}
