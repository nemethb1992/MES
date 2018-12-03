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

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.Authentication;
import phoenix.mes.content.utility.AccessControl;
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
		
		if(!(new AppBuild(null)).isStable(request)){
			doGet(request,response);
			return;
		}

		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		
		try {
			if(!new Authentication().bind(username, pass, request) && !new AccessControl(request, username).isModifier())
			{
				return;
			}
		} catch (SQLException | LoginException | NamingException e1) {
		}

		HttpSession session = request.getSession();

		Task task = (Task)session.getAttribute("Task");
		if(null == task)
		{
			return;
		}

		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

		AbasConnection<EDPSession> abasConnection = null;

		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), new AppBuild(request).isTest());

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
