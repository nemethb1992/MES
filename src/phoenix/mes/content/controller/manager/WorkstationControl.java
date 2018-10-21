package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.PostgreSql;

/**
 * Servlet implementation class StationControl
 */
public class WorkstationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
	    OutputFormatter outputFormatter = (OutputFormatter)session.getAttribute("OutputFormatter");
		String view = "";
		String level = "0";
		if(request.getParameter("level") != null)
			level = request.getParameter("level");
		switch (level) {

		case "1":
			view = getGroupItems(request);
			break;
		case "2":
			view = getStationItem(request,outputFormatter.getLocale());
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
		PostgreSql postgreSql = new PostgreSql(true);
		String view = "";
		String command = "SELECT long FROM profitcenter";
		final String sqlFieldName = "long";
		try {
			for (Map<String, String> row : postgreSql.sqlQuery(command, sqlFieldName)) {
				view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+row.get(sqlFieldName)+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+row.get(sqlFieldName)+"'></div>";
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
		PostgreSql postgreSql = new PostgreSql(true); 
    	String view = ""; 
    	
   	    String command = "select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+request.getParameter("element")+"' group by stations.csoport";
		final String sqlFieldName = "csoport";
		try {	
			for (Map<String, String> row : postgreSql.sqlQuery(command, sqlFieldName)) {
	    		view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+row.get(sqlFieldName)+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+row.get(sqlFieldName)+"'></div>";
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
    
    protected String getStationItem(HttpServletRequest request, Locale locale)
    {
		PostgreSql postgreSql = new PostgreSql(true);
    	String view = "";
   	    final String groupSqlFieldName = "csoport";
   	    final String numberSqlFieldName = "sorszam";
   	    final String nameSqlFieldName = "nev_" + locale.getLanguage();
   	    String command = "select * from stations where csoport='"+request.getParameter("element")+"' ORDER BY sorszam ASC";
   	    try {
			for (Map<String, String> row : postgreSql.sqlQuery(command, groupSqlFieldName, numberSqlFieldName, nameSqlFieldName)) {
				final String name = row.get(nameSqlFieldName);
				view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='" + row.get(groupSqlFieldName) + "!" + row.get(numberSqlFieldName) + "!" + name + "' OnClick='clickOnStation(this)'><input disabled class='si1'value='" + name +"'></div>";
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
