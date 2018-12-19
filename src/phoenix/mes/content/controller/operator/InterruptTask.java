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
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class InterruptTask
 */
public class InterruptTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Logout").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppBuild ab = new AppBuild(request);
		if(!ab.isStable()){
			doGet(request,response);
			return;
		}

		HttpSession session = request.getSession();
		Task task = (Task)session.getAttribute("Task");
		if(null == task)
		{
			return;
		}

		AbasConnection<EDPSession> abasConnection = null;
		try {  
			User user = new User(request);   
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");   	
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			task.interrupt(abasConnection);
			new Log(request).insert(task.getDetails(abasConnection).getWorkSlipNo(),request.getParameter("errorText"));
		} catch (LoginException | SQLException | AbasFunctionException e) {
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