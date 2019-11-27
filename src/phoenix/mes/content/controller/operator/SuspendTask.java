package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
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
import phoenix.mes.content.controller.AbasAuthentication;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;


public class SuspendTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/Logout").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if(!ab.isStabile()){
			doGet(request,response);
			return;
		}

		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		String secureParam = request.getParameter("secure");
		String taskId = request.getParameter("taskId");
		String errorText = request.getParameter("errorText");
		String responseStr ="true";
		boolean secure = (secureParam.equals("true") ? true : false);

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		if(secure) {
			try {
				boolean val1 = new AbasAuthentication().bind(username, pass, request);
				boolean val2 = new User(request).isModifier(username);
				
				if(!val1 || !val2)
				{
					response.getWriter().write("Sikertelen hitelesítés!");
					return;
				}
			} catch (SQLException | LoginException | NamingException e1) {

				response.getWriter().write("Sikertelen hitelesítés!");
				return;
			}
		}else if(!secure) {
			try {
				User user = new User(request);
				username = user.getUsername();
				pass = user.getPassword();
			} catch(Exception e) {
			}
		}

		HttpSession session = request.getSession();
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		AbasConnection abasConnection = null;

		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), ab.isTest());
			Task task = null;
			Id AbasId= null;
			if(taskId != null) {
				AbasId = IdImpl.valueOf(taskId);
			}
			else {
				AbasId = IdImpl.valueOf((String)session.getAttribute("TaskId"));
			}
			task = AbasObjectFactory.INSTANCE.createTask(AbasId,abasConnection);
			if(null == task){
				return;
			}
			if(task != null)
			{
//				task.resume(abasConnection);
				task.suspend(abasConnection);
				new Log(request).insert(task.getDetails(abasConnection).getWorkSlipNo(),errorText);
				session.removeAttribute("Task");
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
				new Log(request).logFaliure(username,FaliureType.TASK_SUSPEND, e.toString(),workstation);
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
					new Log(request).logFaliure(username,FaliureType.TASK_SUSPEND, e.toString(), workstation);
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
