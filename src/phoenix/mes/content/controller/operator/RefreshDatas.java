package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;

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
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class StartActualTask
 */
public class RefreshDatas extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if(!ab.isStable()){
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
			User user = new User(request); 				
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			Task task = (Task)session.getAttribute("Task");			
			Task.Details taskDetails = task.getDetails(abasConnection);			
			if(taskDetails.getStatus() == Status.IN_PROGRESS)
			{
				taskDetails.clearCache();
				responseStr = "inProgress";
			}
		}catch(LoginException | SQLException e)
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
