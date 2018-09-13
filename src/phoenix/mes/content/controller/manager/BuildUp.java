package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import phoenix.mes.content.PostgreSqlOperationsMES;

public class BuildUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected ArrayList<String> managerLayout()
	{
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true);
		String station ="";
		ArrayList<String> layout = new ArrayList<String>();
		Collection<String> list;
   	    String command ="SELECT * FROM profitcenter";
		try {
			list = postgreSql.sqlQuery(command,"long");
			for (String item : list) {
				station += "<div class='tmts_stationBtnDivCont col px-0' value='"+item+"' OnClick='PC_Select(this)'><input disabled class='si1'value='"+item+"'></div>";
			}
			layout.add(station);
		} catch (SQLException e) {
			//TODO Jelezni a felhasználó felé.
		}finally {
			postgreSql.dbClose();
		}
		return layout;

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = new Gson().toJson(managerLayout());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
