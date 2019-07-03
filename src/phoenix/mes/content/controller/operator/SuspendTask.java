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

/**
 * Servlet implementation class Suspend
 */
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
		boolean secure = (secureParam.equals("true") ? true : false);

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		if(secure) {
			try {
				if(!new AbasAuthentication().bind(username, pass, request) && !new User(request).isModifier(username))
				{
					response.getWriter().write("Sikertelen hitelesítés!");
					return;
				}
			} catch (SQLException | LoginException | NamingException e1) {
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

		Task task = (Task)session.getAttribute("Task");
		if(null == task){
			return;
		}

		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		AbasConnection abasConnection = null;

		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), ab.isTest());

			if(task != null)
			{
				task.resume(abasConnection);
				task.suspend(abasConnection);
				session.removeAttribute("Task");
			}
		}catch(LoginException | AbasFunctionException e)
		{
			System.out.println(e);
    		try {
				OperatingWorkstation ws = new OperatingWorkstation(request);
				String workstation = "";
				if(ws != null) {
					workstation = ws.group + " - " + ws.no;
				}
				new Log(request).logFaliure(FaliureType.TASK_SUSPEND, e.getMessage(),workstation);
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
			response.getWriter().write("true");
		}

	}

}
