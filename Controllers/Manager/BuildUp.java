package Manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Database.dbEntities;

/**
 * Servlet implementation class BuildUp
 */
public class BuildUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildUp() {
        super();
        // TODO Auto-generated constructor stub
    }

    private ArrayList<String> DataSheet_Layout()
    {
    	String station ="";



    	ArrayList<String> layouts = new ArrayList<String>();
    	

   	   dbEntities dbE = new dbEntities();
   	   ArrayList<String> li1 = dbE.SQLQueryRead("SELECT * FROM profitcenter","long");
   	int itemCount_li1 = li1.size();
    	for (int i = 0; i < itemCount_li1; i++) {
    		station += "<div class='tmts_stationBtnDivCont' value='"+li1.get(i)+"' OnClick='PC_Select(this)'><input disabled class='si1'value='"+li1.get(i)+"'></div>";
    	}

    	layouts.add(station);

    	return layouts;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String json = new Gson().toJson(DataSheet_Layout());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
}
