package phoenix.mes.content.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class SelectedWorkstation extends Workstation {	
	
	public SelectedWorkstation(HttpServletRequest request, String station){
		this.request = request;
		String[] rawName;
		if(station != null)
		{
			try {
				rawName = station.split("!");
				setSelectedStation(station);
				loadVariables(rawName, request);
				
			}catch (SQLException e) {
			}
		}
	}
	
	public SelectedWorkstation(HttpServletRequest request){
		this.request = request;
		String[] rawName;
		try {
			rawName = getSelectedStation().split("!");
			if(rawName != null)
			{
				loadVariables(rawName, request);
			}
		}catch (SQLException e) {
		}
	}
	
	public void setSelectedStation(String obj)
	{
		request.getSession().setAttribute("selectedWorkstation", obj);
	}

	@SuppressWarnings("null")
	public String getSelectedStation() throws SQLException
	{
		String station = (String)request.getSession().getAttribute("selectedWorkstation");
		if(station != null) {
			loadVariables(station.split("!"), request);
		}
		return (station == null ? "" : station);
	}
	

	


}
