package phoenix.mes.content.controller.operator;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
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

		String workstation = (String)session.getAttribute("operatorWorkstation");

		String responsedString = "null";

		if(workstation != null)
		{
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			AbasConnection<EDPSession> abasConnection = null;
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
				session.removeAttribute("Task");
				Task task = AbasObjectFactory.INSTANCE.createWorkStation(workstation.split("!")[0],Integer.parseInt(workstation.split("!")[1]), abasConnection).startFirstScheduledTask(abasConnection);

				if(task.isInProgress(abasConnection))
				{
					task.getDetails(abasConnection).clearCache();
					session.setAttribute("Task", task);
					responsedString = "notnull";
				}
				//TODO Visszadob az OpenTask lapra. Task unSuchedule?

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
		}

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(responsedString); 
	}

}
