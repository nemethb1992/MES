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

	protected ArrayList<String> managerLayout()
	{
		String station ="";
		ArrayList<String> layouts = new ArrayList<String>();
		ArrayList<String> list = DatabaseEntities.sqlQuery("SELECT * FROM profitcenter","long");
		DatabaseEntities.dbClose();
		int itemCount = list.size();
		for (int i = 0; i < itemCount; i++) {
			station += "<div class='tmts_stationBtnDivCont col px-0' value='"+list.get(i)+"' OnClick='PC_Select(this)'><input disabled class='si1'value='"+list.get(i)+"'></div>";
		}
		layouts.add(station);
		return layouts;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = new Gson().toJson(managerLayout());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
