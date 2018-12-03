package phoenix.mes.content.utility;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.PostgreSql;

public class AccessControl {
	
	final protected String username;
	protected boolean isTest;
	protected HttpServletRequest request;
	
	public AccessControl(HttpServletRequest request, String... username)
	{
		this.username = (username.length == 0?(String)request.getSession().getAttribute("username"):username[0]);
		this.request = request;
		this.isTest = new AppBuild(request).isTest();
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public enum AccessType
	{
		SIMPLE(false),
		MODIFIER(true);

		AccessType(boolean asd) {
			
		}
	}
	
	public boolean isModifier() throws SQLException
	{
		PostgreSql pg = new PostgreSql(isTest);
		String result = pg.sqlSingleQuery("SELECT modifier FROM users WHERE username='"+username+"'", "modifier");
		pg.dbClose();
		return ("0".equals(result)||"".equals(result)? false: true);
	}
	
	public void insert() throws SQLException 
	{
		PostgreSql pg = new PostgreSql(isTest);
		pg.sqlUpdate("INSERT INTO users (username) VALUES('"+username+"')");
		pg.dbClose();
	}
}
