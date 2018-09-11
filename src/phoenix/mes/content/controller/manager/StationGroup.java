package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import phoenix.mes.content.DatabaseEntities;


public class StationGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String group;

    public StationGroup() {
        super();
    }
    private String Station_layout()
    {
    	String station = "";
   	    DatabaseEntities dbE = new DatabaseEntities();
   	    String query ="select * from stations where csoport='"+group+"' ORDER BY sorszam ASC";
   	    ArrayList<String> li = dbE.SQLQueryRead(query, "sorszam");
    	int itemCount_li = li.size();
    	for (int i = 0; i < itemCount_li; i++) {
    		station += "<div class='tmts_stationBtnDivCont col px-0' value='"+group+"-"+li.get(i)+"' OnClick='Station_Select(this)'><input disabled class='si1'value='"+group+"-"+li.get(i)+"'></div>";
    	}
    	return station;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		group = request.getParameter("group_item");

	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(Station_layout()); 
	}


}
