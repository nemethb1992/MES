package phoenix.mes.content.navigation;

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
 * Servlet implementation class OpenTask
 */
public class DataSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/Logout").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		if(!(new AppBuild(null)).isStable(request)){
			doGet(request,response);
			return;
		}
		
		String workstation = (String)session.getAttribute("operatorWorkstation");
		
		boolean testSystem = new AppBuild(request).isTest();

		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		
		if(workstation == null)
		{
			doGet(request,response);
			return;
		}
		
		AbasConnection<EDPSession> abasConnection = null;
		try {
			String username = (String)session.getAttribute("username");
			String pass = (String)session.getAttribute("pass");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), testSystem);
			Task task = AbasObjectFactory.INSTANCE.createWorkStation(workstation.split("!")[0],Integer.parseInt(workstation.split("!")[1]), abasConnection).startFirstScheduledTask(abasConnection);
			session.setAttribute("Task", task);

		}catch(LoginException e)
		{
		}finally
		{
			try {
				if (null != abasConnection) {
					abasConnection.close();
				}
			} catch (Throwable t) {
				
			}
		}
		getServletContext().getRequestDispatcher("/Views/Operator/DataSheet/DataSheet.jsp").forward(request, response);

	}

}
