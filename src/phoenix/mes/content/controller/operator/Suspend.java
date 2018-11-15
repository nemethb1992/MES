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
 * Servlet implementation class Suspend
 */
public class Suspend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		// TODO split
		String workstation = (String)session.getAttribute("operatorWorkstation");
		
		System.out.println(workstation);
		// TODO
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
//		workstation="234PG!1";
		String view = "";
		
		boolean testSystem = new AppBuild(request).isTest();
		        	
		if(workstation != null)
		{
			String username=(String)session.getAttribute("username");
			String pass=(String)session.getAttribute("pass");
			AbasConnection<EDPSession> abasConnection = null;
			
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), testSystem);
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
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view); 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
