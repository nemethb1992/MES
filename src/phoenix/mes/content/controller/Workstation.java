package phoenix.mes.content.controller;

import java.sql.SQLException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.utility.OutputFormatter;

public class Workstation {
	
	protected HttpServletRequest request;
	protected String name;
	public String group;
	public int no;
	
	public String getGroup() {
		return group;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return no;
	}
	
	protected String getStationName(HttpServletRequest request) throws SQLException
	{
		PostgreSql postgreSql = new PostgreSql(new AppBuild(request).isTest()); 
		Locale language = ((OutputFormatter)request.getSession().getAttribute("OutputFormatter")).getLocale();
		String command, field;
		
		switch (language.getLanguage()) {
		case "en":
			command = "SELECT nev_en FROM stations WHERE csoport = '"+group+"' AND sorszam = "+no;
			field = "nev_en";
			break;
		case "de":
			command = "SELECT nev_de FROM stations WHERE csoport = '"+group+"' AND sorszam = "+no;
			field = "nev_de";
			break;
		case "hu":
		default:
			command = "SELECT nev_hu FROM stations WHERE csoport = '"+group+"' AND sorszam = "+no;
			field = "nev_hu";
			break;
		}
		String name = postgreSql.sqlSingleQuery(command, field);
		postgreSql.dbClose();
		return name;
	}
	
	protected void loadVariables(String[] rawName, HttpServletRequest request) throws SQLException {
		group = (rawName[0] != null ? rawName[0] : "");
		no = Integer.parseInt(rawName[1]);
		name = getStationName(request);
	}
}
