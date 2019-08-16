package phoenix.mes.content.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import phoenix.mes.content.controller.OperatingWorkstation;

public class OperatingWorkstation extends Workstation{
	
	public OperatingWorkstation(HttpServletRequest request, String station){
		this.request = request;
		String[] rawName = null;
		if(station != null)
		{
			try {
				rawName = station.split("!");
				setOperatingStation(request ,station);
				loadVariables(rawName, request);

			}catch (SQLException e) {
			}
		}
		if(rawName != null)
		{
			try {
				loadVariables(rawName,request);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public OperatingWorkstation(HttpServletRequest request){
		this.request = request;
		String[] rawName;
		try {
			rawName = getOperatingStation().split("!");
			if(rawName != null)
			{
				loadVariables(rawName, request);
			}
		}catch (Exception e) {
		}
	}
	
	public static void setOperatingStation(HttpServletRequest request, String obj)
	{
		request.getSession().setAttribute("operatingWorkstation", obj);
	}
	
	@SuppressWarnings("null")
	public String getOperatingStation() throws SQLException
	{
		String station = (String)request.getSession().getAttribute("operatingWorkstation");
		if(station != null) {
			loadVariables(station.split("!"), request);
		}
		return (station == null ? "" : station);
	}
}
