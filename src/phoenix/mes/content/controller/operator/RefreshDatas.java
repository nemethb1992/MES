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
import phoenix.mes.abas.Task.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;

/**
 * Servlet implementation class StartActualTask
 */
public class RefreshDatas extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(!(new AppBuild(null)).isStable(request)){
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("null"); 
			return;
		}
		
		HttpSession session = request.getSession();
		
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

		String responseStr = "null";

		AbasConnection<EDPSession> abasConnection = null;

		try {				
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
			
			Task task = (Task)session.getAttribute("Task");
			
			Task.Details taskDetails = task.getDetails(abasConnection);
			//FIXME
			if(taskDetails.getStatus() == Status.IN_PROGRESS)
			{
				taskDetails.clearCache();
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
