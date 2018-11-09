package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.PostgreSql;

/**
 * Servlet implementation class StationControl
 */
public class WorkstationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    OutputFormatter outputFormatter = (OutputFormatter)(request.getSession()).getAttribute("OutputFormatter");
		
		String level, htmlView = "";
		
		if(request.getParameter("level") != null){
			level = request.getParameter("level");
		}
		else {
			level = "0";
		}
		PostgreSql postgreSql = new PostgreSql(new AppBuild(request).isTest());
		
		try {

		String command, field;
		
		switch (level) {
		case "1":
			field = "csoport";
	   	    command = "select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+request.getParameter("element")+"' group by stations.csoport";
			for (Map<String, String> row : postgreSql.sqlQuery(command, field)) {
		    	htmlView += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+row.get(field)+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+row.get(field)+"'></div>";
		    }
			break;
		case "2":
	   	    String groupSqlFieldName = "csoport";
	   	    String numberSqlFieldName = "sorszam";
	   	     String nameSqlFieldName = "nev_" + outputFormatter.getLocale().getLanguage();
	   	    command = "select * from stations where csoport='"+request.getParameter("element")+"' ORDER BY sorszam ASC";
	   	    
				for (Map<String, String> row : postgreSql.sqlQuery(command, groupSqlFieldName, numberSqlFieldName, nameSqlFieldName)) {
					final String name = row.get(nameSqlFieldName);
					htmlView += "<div class='tmts_stationBtnDivCont col-12 px-0' value='" + row.get(groupSqlFieldName) + "!" + row.get(numberSqlFieldName) + "!" + name + "' OnClick='clickOnStation(this)'><input disabled class='si1'value='" + name +"'></div>";
				}
			break;
		case "0":
		default:
			field = "long";
			command = "SELECT long FROM profitcenter";
			for (Map<String, String> row : postgreSql.sqlQuery(command, field)) {
				htmlView += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+row.get(field)+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+row.get(field)+"'></div>";
			}
			break;
		}
		}catch (SQLException e) {
		}finally {
			try {
				postgreSql.dbClose();
			} catch (Exception e2) {
			}
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(htmlView);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
//	protected String getPcItems(PostgreSql postgreSql) throws SQLException
//	{
//		String view = "";
//		String command = "SELECT long FROM profitcenter";
//		final String sqlFieldName = "long";
//			for (Map<String, String> row : postgreSql.sqlQuery(command, sqlFieldName)) {
//				view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+row.get(sqlFieldName)+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+row.get(sqlFieldName)+"'></div>";
//			}
//		return view;
//	}
//	
//    protected String getGroupItems(PostgreSql postgreSql, HttpServletRequest request) throws SQLException
//    {
//    	String view = ""; 
//    	
//   	    String command = "select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+request.getParameter("element")+"' group by stations.csoport";
//		final String sqlFieldName = "csoport";
//		
//			for (Map<String, String> row : postgreSql.sqlQuery(command, sqlFieldName)) {
//	    		view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+row.get(sqlFieldName)+"' OnClick='StationItemSelect(this)'><input disabled class='si1'value='"+row.get(sqlFieldName)+"'></div>";
//	    	}
//
//    	return view;
//    }
//    
//    protected String getStationItem(PostgreSql postgreSql, HttpServletRequest request, Locale locale) throws SQLException
//    {
//    	String view = "";
//   	    final String groupSqlFieldName = "csoport";
//   	    final String numberSqlFieldName = "sorszam";
//   	    final String nameSqlFieldName = "nev_" + locale.getLanguage();
//   	    String command = "select * from stations where csoport='"+request.getParameter("element")+"' ORDER BY sorszam ASC";
//   	    
//			for (Map<String, String> row : postgreSql.sqlQuery(command, groupSqlFieldName, numberSqlFieldName, nameSqlFieldName)) {
//				final String name = row.get(nameSqlFieldName);
//				view += "<div class='tmts_stationBtnDivCont col-12 px-0' value='" + row.get(groupSqlFieldName) + "!" + row.get(numberSqlFieldName) + "!" + name + "' OnClick='clickOnStation(this)'><input disabled class='si1'value='" + name +"'></div>";
//			}
//    	return view;
//    }
	
}
