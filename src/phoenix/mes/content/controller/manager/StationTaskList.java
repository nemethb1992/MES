package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

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
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.controller.Workstation;
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
		String time = "";
		try {
			AppBuild ab = new AppBuild(request);
			Workstation ws = new Workstation(request,ab.isOperator());
			User user = new User(request);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			request.setAttribute("StationList", AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getScheduledTasks(abasConnection));
			request.setAttribute("abasConnection", abasConnection);			
			summedProductionTime = (BigDecimal)request.getAttribute("summedProductionTime");
			view = RenderView.render("/Views/Manager/Todo/Partial/StationList.jsp", request, response);
//			time = of.formatTime(summedProductionTime);
		}catch(LoginException | SQLException  e)
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
		responseArr[0] = view;
		responseArr[1] = time;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(new Gson().toJson(responseArr));
	}
}
