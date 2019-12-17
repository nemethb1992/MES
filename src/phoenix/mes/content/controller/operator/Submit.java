package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


public class Submit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected String quantity;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild build = new AppBuild(request);
		if(!build.isStabile()){
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("finished");
			return;
		}

		HttpSession session = request.getSession();
		AbasConnection abasConnection = null;
		String responseStr = "null";
		BigDecimal finishedQty = new BigDecimal(request.getParameter("finishedQty"));
		BigDecimal scrapQty = new BigDecimal(request.getParameter("scrapQty"));
		User user = null;
		try {
			user = new User(request); 
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), build.isTest());
			Task task = (Task)session.getAttribute("Task");
			if(task != null)
			{
				task.postCompletionConfirmation(finishedQty, scrapQty, abasConnection);
				responseStr = "submit_done";
				Task.Details taskDetails = task.getDetails(abasConnection);
				Status status = taskDetails.getStatus();
				if(status == Status.DONE || status == Status.INTERRUPTED || status == Status.DELETED) {
					responseStr = "exit";
				}else {

					session.setAttribute("Task", task);
				}
			}
		}catch(LoginException | SQLException e)
		{
			responseStr = "error";
    		System.out.println(e.getMessage());			
    		try {
				OperatingWorkstation ws = new OperatingWorkstation(request);
				String workstation = "";
				if(ws != null) {
					workstation = ws.group + " - " + ws.no;
				}
				String stackTrace = Log.getStackTraceString(e);
				new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_SUBMIT,stackTrace, e.toString(),null,workstation);
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
					String errorText = Log.getErrorText(errorCode);
					String stackTrace = Log.getStackTraceString(e);
					responseStr = "abasError";
					new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_SUBMIT,stackTrace, e.toString(),null, workstation);
					request.setAttribute("abasError", errorText);
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
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(responseStr); 
	}

}
