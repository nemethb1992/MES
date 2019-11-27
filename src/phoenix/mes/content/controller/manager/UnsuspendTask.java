package phoenix.mes.content.controller.manager;

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
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.GenericTask.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

public class UnsuspendTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String encodedURL = response.encodeRedirectURL("/Logout");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String taskID = (String) request.getParameter("TaskID");
		OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
		Task task = null;
		String responseStr ="";
		Task.Details taskDetails = null;
		AbasConnection abasConnection = null;
		User user = null;
		try {
			user = new User(request);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(),
					user.getPassword(), of.getLocale(), new AppBuild(request).isTest());
			Id AbasId = IdImpl.valueOf(taskID);
			task = AbasObjectFactory.INSTANCE.createTask(AbasId, abasConnection);
			taskDetails = task.getDetails(abasConnection);
			if(taskDetails.getStatus() == Status.SUSPENDED || taskDetails.getStatus() == Status.INTERRUPTED) {
				task.unsuspend(abasConnection);
			}
			
		} catch (LoginException | SQLException e) {

		} catch (AbasFunctionException e) {
			int errorCode = e.getErrorCode();
			if(errorCode != 6 && errorCode != 7 && errorCode != 8) {
	    		try {
					OperatingWorkstation ws = new OperatingWorkstation(request);
					String workstation = "";
					if(ws != null) {
						workstation = ws.group + " - " + ws.no;
					}

					String abasErrorText = Log.getErrorText(errorCode);
					responseStr = "abasError";
					new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_SUBMIT, e.toString(), workstation);
					request.setAttribute("abasError", abasErrorText);
				}catch(SQLException exc) {
				}	
			}

		}finally
		{
			try {
				if (null != abasConnection) {
					abasConnection.close();
				}
			} catch (Throwable t) {
			}
			response.getWriter().write("true");
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(responseStr);
	}

}
