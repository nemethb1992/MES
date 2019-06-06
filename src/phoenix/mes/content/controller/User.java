package phoenix.mes.content.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.PostgreSql;

public class User {

	HttpServletRequest request;
	protected Integer userid;
	protected final String username;
	protected final String password;
	protected boolean modifier;
	protected boolean access;
	protected StationAccess workstationAccess;
	
	public User(HttpServletRequest request) throws SQLException
	{
		HttpSession session = request.getSession();
		this.request = request;
		this.username = (String) session.getAttribute("username");
		this.password = (String) session.getAttribute("password");
		this.userid = setUserId();
		this.modifier = setModifier();
		this.access = setAccessValue();
		if(modifier) {
			this.workstationAccess = setWorkstationAccess();
		}
	}
	
	public User(HttpServletRequest request, String username, String password) throws SQLException
	{
		if(!isExists(username)) {
			registration(username);
		}
		
		HttpSession session = request.getSession();
		this.request = request;
		this.username = username;
		this.password = password;
		this.userid = setUserId();
		this.modifier = setModifier();
		this.access = setAccessValue();
		if(modifier) {
			this.workstationAccess = setWorkstationAccess();
		}
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("userid", userid);
	}
    
    public boolean hasAccess(String... username) throws SQLException
    { 
    	return access;
    }

	public int getUserid(){
		return userid;
	}
    
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public StationAccess getStationAccess() {
		return workstationAccess;
	}
	
    public boolean isModifier()
    {
    	return modifier;
    }
    
    public boolean isModifier(String username) throws SQLException
    {
    	return sqlBindEngine("SELECT users.username FROM users WHERE username='"+username+"' AND modifier = 1", "username");
    }
    
    public boolean isExists(String... username) throws SQLException
    {
    	return sqlBindEngine("SELECT users.username FROM users WHERE username='"+(username.length != 0 ? username[0] : this.username)+"'", "username");
    }
    
	protected void registration(String... username) throws SQLException 
	{
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		pg.sqlUpdate("INSERT INTO users (username) VALUES('"+(username.length != 0 ? username[0] : this.username)+"')");
		pg.dbClose();
	}
	
	protected int setUserId() throws SQLException {
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String id = pg.sqlSingleQuery("SELECT users.id FROM users WHERE username='"+this.username+"'", "id");
		if(id == "") {
			registration();
			id = pg.sqlSingleQuery("SELECT users.id FROM users WHERE username='"+this.username+"'", "id");
		}
		pg.dbClose();
		return Integer.parseInt(id);
	}
    
    protected boolean setAccessValue(String... username) throws SQLException
    { 
    	return sqlBindEngine("SELECT users.username FROM users WHERE username='"+(username.length != 0 ? username[0] : this.username)+"' AND access = 1", "username");
    }
    
    protected boolean setModifier() throws SQLException
    {
    	return sqlBindEngine("SELECT users.username FROM users WHERE username='"+username+"' AND modifier = 1", "username");
    }
    
    protected StationAccess setWorkstationAccess() throws SQLException{
    	return new StationAccess(request, userid);
    }
	
    protected boolean sqlBindEngine(String command, String field) throws SQLException
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
