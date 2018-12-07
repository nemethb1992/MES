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
	protected String group;
	protected int no;
	
	
	public Workstation(HttpServletRequest request,  boolean isOperator, String... station){
		this.request = request;
		String[] sp;
		if(station.length > 0 && isOperator)
		{
			sp = station[0].split("!");
			setOperatingStation(station[0]);
			try {
				group = (sp[0] != null ? sp[0] : "");
				no = Integer.parseInt(sp[1]);
				name = getStationName(request);

			}catch (SQLException e) {
			}
		}else if(isOperator) {
			try {
				String stationRaw =  getOperatingStation();
				String[] sp2 = (stationRaw.isEmpty()? null : stationRaw.split("!"));
			if(sp2 != null)
			{
				
					group = (sp2[0] != null ? sp2[0] : "");
					no = Integer.parseInt(sp2[1]);
					name = getStationName(request);

				}
			}catch (SQLException e) {
			}
		}else if(!isOperator) {
			try {
				String stationRaw =  getSelectedStation();
				String[] sp2 = (stationRaw.isEmpty()? null : stationRaw.split("!"));
				if(sp2 != null)
				{

					group = (sp2[0] != null ? sp2[0] : "");
					no = Integer.parseInt(sp2[1]);
					name = getStationName(request);

				}}catch (SQLException e) {
				}
		}


	}
	
	public static void setSelectedStation(HttpServletRequest request, String obj)
	{
		request.getSession().setAttribute("selectedWorkstation", obj);
	}
	
	public Workstation( HttpServletRequest request){
		this.request = request;
	}
	
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
	
	public void setOperatingStation(String obj)
	{
		request.getSession().setAttribute("operatingWorkstation", obj);
	}
	
	public String getOperatingStation()
	{
		return (String)request.getSession().getAttribute("operatingWorkstation");
	}
	public String getSelectedStation()
	{
		return (String)request.getSession().getAttribute("selectedWorkstation");
	}
}
