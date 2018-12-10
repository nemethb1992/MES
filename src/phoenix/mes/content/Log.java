package phoenix.mes.content;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import phoenix.mes.content.controller.OperatingWorkstation;
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
		ws = new OperatingWorkstation(request);
	}
	
	public void insert(String workSplitNo,String text, String... username) throws SQLException
	{
		String date = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String command = "INSERT INTO log (user_id,ws_group,ws_no,date,type,text,workslipno) VALUES("+(username.length>0 ? username[0] : user.getUserid())+",'"+ws.getGroup()+"',"+ws.getNumber()+",'"+date+"',0,'"+text+"', '"+workSplitNo+"')";
		pg.sqlUpdate(command);
		pg.dbClose();

	}
	public String get(String workSplitNo) throws SQLException
	{
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String result = pg.sqlSingleQuery("SELECT text FROM log WHERE workslipno='"+workSplitNo+"'","text");
		pg.dbClose();
		return result;
	}
}
