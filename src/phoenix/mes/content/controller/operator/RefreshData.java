package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.GenericTask.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class StartActualTask
 */
public class RefreshData extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if(!ab.isStabile()){
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("null"); 
			return;
		}
		
		HttpSession session = request.getSession();
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		String responseStr = "null";
		AbasConnection abasConnection = null;
		String taskId = (String)session.getAttribute("TaskId");
		User user = null;
		try {
			user = new User(request); 				
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			Id AbasId = IdImpl.valueOf(taskId);
			Task task = AbasObjectFactory.INSTANCE.createTask(AbasId,abasConnection);		
			Task.Details taskDetails = task.getDetails(abasConnection);			
			if(taskDetails.getStatus() == Status.IN_PROGRESS)
			{
				taskDetails.clearCache();
				responseStr = "inProgress";
				session.setAttribute("Task", task);
			}
		}catch(LoginException | SQLException e)
		{
			System.out.println(e);
			try {
				OperatingWorkstation ws = new OperatingWorkstation(request);
				String workstation = "";
				if(ws != null) {
					workstation = ws.group + " - " + ws.no;
				}
				String stackTrace = Log.getStackTraceString(e);
				new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_REFRESH,stackTrace, e.toString(),taskId,workstation);
			}catch(SQLException exc) {
			}
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
