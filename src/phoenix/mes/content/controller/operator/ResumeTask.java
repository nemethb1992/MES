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
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
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
		if(!ab.isStabile()){
			doGet(request,response);
			return;
		}
		String taskId = request.getParameter("taskId");
		
		HttpSession session = request.getSession();
		String responseStr ="true";

		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		AbasConnection abasConnection = null;
		User user = null;
		try { 
			user = new User(request);       	
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			Task task = null;
			Id AbasId = null;
			if(taskId != null) {
				AbasId = IdImpl.valueOf(taskId);
			}else {
				AbasId = IdImpl.valueOf((String)session.getAttribute("TaskId"));
			}
			task = AbasObjectFactory.INSTANCE.createTask(AbasId,abasConnection);
			if(null == task){
				return;
			}
			task.resume(abasConnection);
		} catch (LoginException | SQLException e) {
			try {
				OperatingWorkstation ws = new OperatingWorkstation(request);
				String workstation = "";
				if(ws != null) {
					workstation = ws.group + " - " + ws.no;
				}
				new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_RESUME, e.toString(),workstation);
			}catch(SQLException exc) {
			}
		} catch (AbasFunctionException e) {
			int errorCode = e.getErrorCode();
			if (errorCode != 6 && errorCode != 7 && errorCode != 8) {
				try {
					OperatingWorkstation ws = new OperatingWorkstation(request);
					String workstation = "";
					if (ws != null) {
						workstation = ws.group + " - " + ws.no;
					}
					String abasErrorText = Log.getErrorText(errorCode);
					responseStr = "abasError";
					new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_RESUME, e.toString(), workstation);
					request.setAttribute("abasError", abasErrorText);
				} catch (SQLException exc) {
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
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write(responseStr);
		}
	}

}
