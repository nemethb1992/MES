package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class StationTaskList extends HttpServlet {

	protected static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

		AbasConnection<EDPSession> abasConnection = null;  

		BigDecimal summedProductionTime = BigDecimal.ZERO;

		String view = "";

		try {
			String station = (String)session.getAttribute("selectedStation");
			String[] stationSplit = station.split("!");
			int stationNo = Integer.parseInt(stationSplit[1]);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
			List<Task> list = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection).getScheduledTasks(abasConnection);
			request.setAttribute("StationList", list);
			request.setAttribute("abasConnection", abasConnection);
			view = RenderView.render("/Views/Manager/Todo/Partial/StationList.jsp", request, response);
		}catch(LoginException e)
		{
			System.out.println(e);
		}finally
		{
			try
			{
				abasConnection.close();
			}
			catch(Exception e)
			{}
		}
		String[] responseArr = new String[2];
		responseArr[0] = view.toString();
		responseArr[1] = of.formatTime(summedProductionTime);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(new Gson().toJson(responseArr));
	}
}
