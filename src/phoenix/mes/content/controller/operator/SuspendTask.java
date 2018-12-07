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
import phoenix.mes.content.controller.AbasAuthentication;
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
		if(!ab.isStable()){
			doGet(request,response);
			return;
		}

		String username = request.getParameter("username");
		String pass = request.getParameter("password");

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		try {
			if(!new AbasAuthentication().bind(username, pass, request) && !new User(request).isModifier(username))
			{
				response.getWriter().write("Sikertelen hitelesítés!");
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
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), ab.isTest());

			if(task != null)
			{
				task.resume(abasConnection);
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
			response.getWriter().write("true");
		}

	}

}
