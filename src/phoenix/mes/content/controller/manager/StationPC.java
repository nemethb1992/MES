package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import phoenix.mes.content.PostgreSqlOperationsMES;


public class StationPC extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected String pc;
	
    protected String groupLayout()
    {
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true); 
		
    	Collection<String> list;
    	String group = "";   	   
   	    String command ="select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+pc+"' group by stations.csoport";
   	    
		try {
			list = postgreSql.sqlQuery(command, "csoport");    	
			for (String item : list) {
	    		group += "<div class='tmts_stationBtnDivCont col-12 px-0' value='"+item+"' OnClick='Group_Select(this)'><input disabled class='si1'value='"+item+"'></div>";
	    	}
		} catch (SQLException e) {
			//TODO Jelezni a felhasználó felé.
		}finally {
			postgreSql.dbClose();
		}
    	return group;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		pc = request.getParameter("pc_name");
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(groupLayout()); 
	}
}
