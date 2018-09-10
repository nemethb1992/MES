package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import phoenix.mes.content.DatabaseEntities;


public class StationPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String pc;

    public StationPC() {
        super();
    }
    private String Group_layout()
    {
    	String group = "";
   	    DatabaseEntities dbE = new DatabaseEntities();
   	    String query ="select stations.csoport from stations left join profitcenter on stations.pc = profitcenter.id where long='"+pc+"' group by stations.csoport";
   	    ArrayList<String> li = dbE.SQLQueryRead(query, "csoport");
    	int itemCount_li = li.size();
    	for (int i = 0; i < itemCount_li; i++) {
    		group += "<div class='tmts_stationBtnDivCont col px-0' value='"+li.get(i)+"' OnClick='Group_Select(this)'><input disabled class='si1'value='"+li.get(i)+"'></div>";
    	}
    	return group;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pc = request.getParameter("pc_name");

	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(Group_layout()); 
	}




}
