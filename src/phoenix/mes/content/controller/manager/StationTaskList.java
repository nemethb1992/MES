package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import phoenix.mes.content.controller.SelectedWorkstation;
import phoenix.mes.content.controller.User;
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
			AppBuild ab = new AppBuild(request);
			SelectedWorkstation ws = new SelectedWorkstation(request);
			User user = new User(request);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			List<Task> task = AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getScheduledTasks(abasConnection);
			request.setAttribute("StationList",task);
			request.setAttribute("abasConnection", abasConnection);			
			view = RenderView.render("/Views/Manager/Todo/Partial/StationList.jsp", request, response);
			summedProductionTime = (BigDecimal)request.getAttribute("summedProductionTime");
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
		responseArr[1] = of.formatTime(summedProductionTime);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(new Gson().toJson(responseArr));
	}
}
