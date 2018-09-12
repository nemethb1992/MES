package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import phoenix.mes.content.DatabaseEntities;

public class BuildUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArrayList<String> DataSheet_Layout()
	{
		String station ="";
		ArrayList<String> layouts = new ArrayList<String>();
		ArrayList<String> li1 = DatabaseEntities.sqlQuery("SELECT * FROM profitcenter","long");
		DatabaseEntities.dbClose();
		int itemCount_li1 = li1.size();
		for (int i = 0; i < itemCount_li1; i++) {
			station += "<div class='tmts_stationBtnDivCont col px-0' value='"+li1.get(i)+"' OnClick='PC_Select(this)'><input disabled class='si1'value='"+li1.get(i)+"'></div>";
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
