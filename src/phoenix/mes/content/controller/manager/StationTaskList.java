package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import phoenix.mes.abas.Task.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;
import phoenix.mes.content.controller.RenderView;


public class StationTaskList extends HttpServlet {
	
	protected static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}
	
	protected String[] StationList(String username, String pass, String station, OutputFormatter of, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{ 
		String layout = "";
		List<Task> list = new ArrayList<Task>();
		int stationNo;
		AbasConnection<EDPSession> abasConnection = null;        	

		BigDecimal summedProductionTime = BigDecimal.ZERO;

		try {
			String[] stationSplit = station.split("!");
			stationNo = Integer.parseInt(stationSplit[1]);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), new AppBuild(request).isTest());
			list = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection).getScheduledTasks(abasConnection);

				request.setAttribute("StationList", list);
				request.setAttribute("abasConnection", abasConnection);
				layout = RenderView.render("/Views/Manager/Todo/Partial/StationList.jsp", request, response);
			
			
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
		responseArr[0] = layout.toString();
		responseArr[1] = of.formatTime(summedProductionTime);
		return responseArr;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
 	   	String username=(String)session.getAttribute("username");
 	   	String pass=(String)session.getAttribute("pass");
 	   	String station = (String)session.getAttribute("selectedStation");
 	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

 	   	
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(new Gson().toJson(StationList(username,pass,station, of, request,response)));
	}

}
