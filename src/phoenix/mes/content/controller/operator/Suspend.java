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
 * Servlet implementation class Suspend
 */
public class Suspend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/Logout").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!(new AppBuild(null)).isStable(request)){
			doGet(request,response);
			return;
		}
		
		HttpSession session = request.getSession();

		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		        	
			String username=(String)session.getAttribute("username");
			String pass=(String)session.getAttribute("pass");
			AbasConnection<EDPSession> abasConnection = null;
			
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), new AppBuild(request).isTest());
				Task task = (Task)session.getAttribute("Task");
				if(task != null)
				{
					task.suspend(abasConnection);
					session.removeAttribute("Task");
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
	
	}

}
