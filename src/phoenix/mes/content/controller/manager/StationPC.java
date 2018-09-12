package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import phoenix.mes.content.DatabaseEntities;


public class StationPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String pc;
    protected String groupLayout()
    {
    	String group = "";
   	    String query ="select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+pc+"' group by stations.csoport";
   	    ArrayList<String> list = DatabaseEntities.sqlQuery(query, "csoport");
		DatabaseEntities.dbClose();
    	int itemCount = list.size();
    	for (int i = 0; i < itemCount; i++) {
    		group += "<div class='tmts_stationBtnDivCont col px-0' value='"+list.get(i)+"' OnClick='Group_Select(this)'><input disabled class='si1'value='"+list.get(i)+"'></div>";
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
