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
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class ResumeTask
 */
public class ResumeTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Logout").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if(!ab.isStable()){
			doGet(request,response);
			return;
		}
		
		HttpSession session = request.getSession();

		Task task = (Task)session.getAttribute("Task");
		if(null == task)
		{
			return;
		}
		
		String username=(String)session.getAttribute("username");
		String pass=(String)session.getAttribute("pass");
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

		AbasConnection<EDPSession> abasConnection = null;

		try {        	
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), ab.isTest());
			task.resume(abasConnection);
		} catch (LoginException e) {
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

}
