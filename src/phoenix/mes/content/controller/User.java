package phoenix.mes.content.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.PostgreSql;

public class User {

	HttpServletRequest request;
	protected final String username;
	protected final String password;
	protected Integer userid;
	
	public User(HttpServletRequest request) throws SQLException
	{
		this.request = request;
		HttpSession session = request.getSession();
		this.username = (String) session.getAttribute("username");
		this.password = (String) session.getAttribute("password");
		this.userid = getUserid();
	}
	
	public User(HttpServletRequest request, String username, String password) throws SQLException
	{
		this.request = request;
		this.username = username;
		this.password = password;
		this.userid = getUserid();
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("userid", userid);
	}

    public boolean isExists(String... username) throws SQLException
    {
    	return sqlEngine("SELECT users.username FROM users WHERE username='"+(username.length != 0 ? username[0] : this.username)+"'", "username");
    }
    
    public boolean hasAccess(String... username) throws SQLException
    { 
    	return sqlEngine("SELECT users.username FROM users WHERE username='"+(username.length != 0 ? username[0] : this.username)+"' AND access = 1", "username");
    }

    public boolean isModifier(String... username) throws SQLException
    {
    	return sqlEngine("SELECT users.username FROM users WHERE username='"+(username.length != 0 ? username[0] : this.username)+"' AND modifier = 1", "username");
    }

	public int getUserid() throws SQLException {
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String id = pg.sqlSingleQuery("SELECT users.id FROM users WHERE username='"+this.username+"'", "id");
		if(id == "") {
			registration();
			id = pg.sqlSingleQuery("SELECT users.id FROM users WHERE username='"+this.username+"'", "id");
		}
		return Integer.parseInt(id);
	}
    
	public String getUsername() {
		return (username != null ? username : (String)request.getSession().getAttribute("username"));
	}
	
	public String getPassword() {
		return (password != null ? password : (String)request.getSession().getAttribute("password"));
	}

	public void registration(String... username) throws SQLException 
	{
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		pg.sqlUpdate("INSERT INTO users (username) VALUES('"+(username.length != 0 ? username[0] : this.username)+"')");
		pg.dbClose();
	}
	
    protected boolean sqlEngine(String command, String field) throws SQLException
    {
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String result = pg.sqlSingleQuery(command,field);
		pg.dbClose();
		if("".equals(result) || "0".equals(result)){
			return false;
		}
		return true;
    }
}
