package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.OperatingLanguage;
import phoenix.mes.content.Dictionary;
import phoenix.mes.content.PostgreSqlOperationsMES;

/**
 * Servlet implementation class StationControl
 */
public class WorkstationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
	    Dictionary dict = (Dictionary)session.getAttribute("Dictionary");
		String view = "";
		String level = "0";
		if(request.getParameter("level") != null)
			level = request.getParameter("level");
		switch (level) {

		case "1":
			view = getGroupItems(request);
			break;
		case "2":
			view = getStationItem(request,dict.getLanguage());
			break;
		case "0":
		default:
			view = getPcItems();
			break;
		}
		
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	protected String getPcItems()
	{
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true);
		Collection<String> list;
		String view ="";
		
   	    String command ="SELECT * FROM profitcenter";
   	    
		try {
			list = postgreSql.sqlQuery(command,"long");
			for (String item : list) {
				view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+item+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+item+"'></div>";
			}
		} catch (SQLException e) {
		}finally {
			try {
				postgreSql.dbClose();
			} catch (Exception e2) {
			}
		}
		return view;
	}
	
    protected String getGroupItems(HttpServletRequest request)
    {
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true); 
    	Collection<String> list;
    	String view = ""; 
    	
   	    String command ="select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+request.getParameter("element")+"' group by stations.csoport";
   	    
		try {
			list = postgreSql.sqlQuery(command, "csoport");    	
			for (String item : list) {
	    		view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+item+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+item+"'></div>";
	    	}
		} catch (SQLException e) {
		}finally {
			try {
				postgreSql.dbClose();
			} catch (Exception e2) {
			}
		}
    	return view;
    }
    
    protected String getStationItem(HttpServletRequest request,OperatingLanguage language)
    {
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true);
    	Collection<String> list;
    	String view = "";
    	
   	    String command ="select * from stations where csoport='"+request.getParameter("element")+"' ORDER BY sorszam ASC";
   	    
		try {
			list = postgreSql.sqlGetStaton(command,language);    	
			for (String item : list) {
				view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+item+"' OnClick='clickOnStation(this)'><input disabled class='si1'value='"+item.split("!")[2]+"'></div>";
			}
		} catch (SQLException e) {
			//TODO Jelezni a felhasználó felé.
		}finally {			
			try {
				postgreSql.dbClose();
			} catch (Exception e2) {
			}
		}
    	return view;
    }
	
}
