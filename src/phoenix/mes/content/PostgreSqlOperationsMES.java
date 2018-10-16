package phoenix.mes.content;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import phoenix.mes.OperatingLanguage;

public class PostgreSqlOperationsMES {

	static {
		try {
	    	Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected Connection conn = null;

	protected final boolean testSystem;

	public PostgreSqlOperationsMES(final boolean testSystem)
    {
		this.testSystem = testSystem;
    }

    protected void dbOpen() throws SQLException
    {
    	if (null == conn) {
    		conn = DriverManager.getConnection("jdbc:postgresql://192.168.145.217/" + (testSystem ? "dmes" : "mes"), "mes", "jGbLv!nh+?zc346J");
    	}
    }

    public void dbClose()
    {
        try {
            conn.close();
        } catch (SQLException e) {
        } finally {
        	conn = null;
        }
    }

    public void sqlUpdate(String command) throws SQLException
    {
    	dbOpen();
    	PreparedStatement preparedStmt = conn.prepareStatement(command);
    	try {
    		preparedStmt.executeUpdate();
    	} finally {
    		try {
    			preparedStmt.close();
    		} catch(SQLException e) {
    		}
    	}
    }

    public Collection<String> sqlQuery(String command, String field) throws SQLException
    {
    	dbOpen();
    	Statement stmt = conn.createStatement();
    	try {
    		ResultSet rs = stmt.executeQuery(command);
        	Collection<String> li = new ArrayList<String>();
    		while (rs.next())
    		{
    			li.add(rs.getString(field));
    		}
    		return li;
    	} finally {
    		try {
    			stmt.close();
    		} catch (SQLException e) {
    		}
    	}
    }
    
    public Collection<String> sqlGetStaton(String command, OperatingLanguage language) throws SQLException
    {
		String field;
		
		switch (language) {
		case en:
			field = "nev_en";
			break;
		case de:
			field = "nev_de";
			break;
		case hu:
		default:
			field = "nev_hu";
			break;
		}
    	dbOpen();
    	Statement stmt = conn.createStatement();
    	try {
    		ResultSet rs = stmt.executeQuery(command);
        	Collection<String> li = new ArrayList<String>();
    		while (rs.next())
    		{
    			li.add(rs.getString("csoport")+"!"+rs.getString("sorszam")+"!"+rs.getString(field));
    		}
    		return li;
    	} finally {
    		try {
    			stmt.close();
    		} catch (SQLException e) {
    		}
    	}
    }
    
    public String sqlSingleQuery(String command, String field) throws SQLException
    {
    	dbOpen();
    	Statement stmt = conn.createStatement();
    	try {
    		ResultSet rs = stmt.executeQuery(command);
    		String item = "";
    		while (rs.next())
    		{
    			item = rs.getString(field);
    		}
    		return item;
    	} finally {
    		try {
    			stmt.close();
    		} catch (SQLException e) {
    		}
    	}
    }

}
