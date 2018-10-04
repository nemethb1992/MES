package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import phoenix.mes.content.PostgreSqlOperationsMES;


public class StationGroup extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected String group;
	
    protected String stationLayout()
    {
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true);

    	Collection<String> list;
    	String station = "";
   	    String command ="select * from stations where csoport='"+group+"' ORDER BY sorszam ASC";
   	    
		try {
			list = postgreSql.sqlQuery(command, "sorszam");    	
			for (String item : list) {
	    		station += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+group+"!"+item+"' OnClick='Station_Select(this)'><input disabled class='si1'value='"+group+" - "+item+"'></div>";
	    	}
		} catch (SQLException e) {
			//TODO Jelezni a felhasználó felé.
		}finally {
			postgreSql.dbClose();
		}
    	
    	return station;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		group = request.getParameter("group_item");
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(stationLayout()); 
	}


}
