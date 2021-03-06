package phoenix.mes.content.navigation;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.WorkCenter;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;


/**
 * Servlet implementation class OpenTask
 */
public class DataSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/Logout").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
//		if(!ab.isStabile()){
//			doGet(request,response);
//			return;
//		}
		String firstTask = request.getParameter("firstTask");	
		OperatingWorkstation ws = new OperatingWorkstation(request);
		if(ws.getGroup().equals(null))
		{
			doGet(request,response);
			return;
		}	
		String page = "/Views/Operator/OpenTask/OpenTask.jsp";
		AbasConnection abasConnection = null;
		try {
			HttpSession session = request.getSession();
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			User user = new User(request);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(),user.getPassword(), of.getLocale(), ab.isTest());
			Task task = null;
			if(firstTask != null) {
				task = AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(),ws.getNumber(), abasConnection).startFirstScheduledTask(abasConnection);
			}else {
				task = (Task)session.getAttribute("Task");
			}
			if(task != null) {
				WorkCenter.Details workcenter = AbasObjectFactory.INSTANCE.createWorkCenter(ws.getGroup(), abasConnection).getDetails(abasConnection);
				session.setAttribute("WorkCenter", workcenter);
				session.setAttribute("Task", task);
				session.setAttribute("TaskId", task.getWorkSlipId().toString());
				page = "/Views/Operator/DataSheet/DataSheet.jsp";
			}
		}catch(LoginException e)
		{
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				if (null != abasConnection) {
					abasConnection.close();
				}
			} catch (Throwable t) {
				
			}
		}
		String encodedURL = response.encodeRedirectURL(page);
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);

	}

}
