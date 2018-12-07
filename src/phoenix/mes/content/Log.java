package phoenix.mes.content;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import phoenix.mes.content.controller.User;
import phoenix.mes.content.controller.Workstation;

public class Log {
	
	HttpServletRequest request;
	protected Workstation ws;
	protected User user;
	
	public Log(HttpServletRequest request) throws SQLException
	{
		this.request = request;
		user = new User(request);
		ws = new Workstation(request,new AppBuild(request).isOperator());
	}
	
	public void insert(String text, String... username) throws SQLException
	{
		String date = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String command = "INSERT INTO log (user_id,ws_group,ws_no,date,type,text) VALUES("+(username.length>0 ? username[0] : user.getUserid())+",'"+ws.getGroup()+"',"+ws.getNumber()+",'"+date+"',0,'"+text+"')";
		pg.sqlUpdate(command);
		pg.dbClose();

	}
}
