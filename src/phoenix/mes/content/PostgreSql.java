package phoenix.mes.content;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgreSql {

	static {
		try {
	    	Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected Connection conn = null;

	protected final boolean testSystem;

	public PostgreSql(final boolean testSystem)
    {
		this.testSystem = testSystem;
    }

    protected void dbOpen() throws SQLException
    {
    	if (null == conn) {
    		conn = DriverManager.getConnection("jdbc:postgresql://192.168.145.217/" + (testSystem ? "dmes" : "mes"), "mes", "jGbLv!nh+?zc346J");
//    		conn = DriverManager.getConnection("jdbc:postgresql://192.168.145.217/" + (testSystem ? "dmes" : "mes"), "balazs.nemeth", "Hxx8kahxx8ka");
    	}
    }

    public void dbClose()
    {
        try {
            conn.close();
        } catch (SQLException e) {
        	System.out.println(e);
        } finally {
        	conn = null;
        }
    }

    public void sqlUpdate(String command) throws SQLException
    {
    	dbOpen();
    	final PreparedStatement preparedStmt = conn.prepareStatement(command);
    	try {
    		preparedStmt.executeUpdate();
    	} catch(SQLException e){
    		System.out.println(e.toString());
    	}
    	finally {
    		try {
    			preparedStmt.close();
    		} catch(SQLException e) {
    		}
    	}
    }

    public List<Map<String, String>> sqlQuery(String command, String firstField, String... moreFields) throws SQLException
    {
    	dbOpen();
    	final Statement stmt = conn.createStatement();
    	try {
    		final ResultSet rs = stmt.executeQuery(command);
        	final List<Map<String, String>> li = new ArrayList<>();
        	final int initialCapacity = (null == moreFields ? 1 : (int)Math.ceil((1 + moreFields.length) / 0.75));
        	while (rs.next())
    		{
    			final Map<String, String> row;
    			if (null == moreFields) {
    				row = Collections.singletonMap(firstField, rs.getString(firstField));
    			} else {
    				row = new HashMap<>(initialCapacity);
    				row.put(firstField, rs.getString(firstField));
        			for (String field : moreFields) {
        				row.put(field, rs.getString(field));
        			}
    			}
    			li.add(row);
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
    	final List<Map<String, String>> results = sqlQuery(command, field);
    	return (results.isEmpty() ? "" : results.get(0).get(field));
    }
    
    public int count(String command, String field) throws SQLException
    {
    	final List<Map<String, String>> results = sqlQuery(command, field);
    	dbClose();
    	return results.size();
    }
    

}
