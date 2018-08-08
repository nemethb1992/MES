package Database;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class dbEntities {

	private Connection conn;
	private Statement st;
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    String dbName = "jdbc:postgresql://localhost/struts_new";
    String dbDriver = "org.postgresql.Driver";
    String URL = "jdbc:postgresql://192.168.145.217/dmes"; 
    String USER = "mes";
    String PASS = "jGbLv!nh+?zc346J";
    
    public dbEntities()
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
        		Logger.getLogger(dbEntities.class.getName()).log(Level.SEVERE, null, ex2);
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
//            preparedStmt.setString(1, "valami");
//            preparedStmt.setString(2, "valami");
            preparedStmt.executeUpdate();
            preparedStmt.close();
        	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        dbClose();
    }
    public ArrayList SQLQueryRead()
    {        

    ArrayList li = new ArrayList();
    	
    if (this.dbOpen() == true)
    {
		try {
	    Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(URL, USER, PASS);
	    Statement stmt = conn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT * FROM Stations");
    	while (rs.next())
    	{
    	    li.add(rs.getString("short"));
    	}
    	rs.close();
    	st.close();		
    	} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		return li;
    }
//    static final String JDBC_DRIVER = "org.postgresql.Driver";
//    String dbName = "jdbc:postgresql://localhost/struts_new";
//    String dbDriver = "org.postgresql.Driver";
//    String URL = "jdbc:postgresql://192.168.145.217/dmes"; 
//    String USER = "mes";
//    String PASS = "jGbLv!nh+?zc346J";
//    
//    try { 
//
//        Class.forName("org.postgresql.Driver");
//        Connection conn = DriverManager.getConnection(URL, USER, PASS);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("query");
//        int i = 0;
//        while (rs.next()) {
////            userlist[i] = rs.getString("name");
////            passlist[i] = rs.getString("pass");
////            cardlist[i] = rs.getString("card");
////            pclist[i] = rs.getString("pc");
////            authlist[i] = rs.getInt("auth");
//            i++;
//        }
//        conn.close();
//
//    } catch (SQLException ex) {
//        Logger.getLogger(dbEntities.class.getName()).log(Level.SEVERE, null, ex);
//    } catch (ClassNotFoundException ex2) {
//        Logger.getLogger(dbEntities.class.getName()).log(Level.SEVERE, null, ex2);
//    }
}
