package phoenix.mes.content.controller.operator;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;

/**
 * Servlet implementation class StartActualTask
 */
public class RefreshDatas extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

		String responseStr = "null";

		AbasConnection<EDPSession> abasConnection = null;
		
		String workstation = (String)session.getAttribute("operatorWorkstation");

		try {				
			session.removeAttribute("Task");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
			Task task = AbasObjectFactory.INSTANCE.createWorkStation(workstation.split("!")[0],Integer.parseInt(workstation.split("!")[1]), abasConnection).startFirstScheduledTask(abasConnection);

			IdImpl oldId = (IdImpl) ((Task)session.getAttribute("Task")).getWorkSlipId();
			
			if(task.getWorkSlipId().equals(oldId) && task.isInProgress(abasConnection))
			{
				task.getDetails(abasConnection).clearCache();
				session.setAttribute("Task", task);
				responseStr = "inProgress";
			}
		}catch(LoginException e)
		{
			System.out.println(e);
		}finally
		{
			try {
				if (null != abasConnection) {
					abasConnection.close();
				}
			} catch (Throwable t) {
			}
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(responseStr); 
	}

}
