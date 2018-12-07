package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.controller.Workstation;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class AbasTaskList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		Workstation ws = new Workstation(request,ab.isOperator());
		if(ws.getSelectedStation() == null)
		{
			return;
		}
		String view = "";
		AbasConnection<EDPSession> abasConnection = null;
		String date = request.getParameter("date");
		AbasDate abasDate = (date.isEmpty() ? AbasDate.INFINITY : AbasDate.valueOf(date));
		try {
			User user = new User(request);
			OutputFormatter of = (OutputFormatter)request.getSession().getAttribute("OutputFormatter");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			request.setAttribute("AbasList",  AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getUnassignedTasks(abasDate, abasConnection));
			request.setAttribute("abasConnection", abasConnection);
			view = RenderView.render("/Views/Manager/Todo/Partial/AbasList.jsp", request, response);
		}catch(LoginException | SQLException e){
			System.out.println(e);
		}finally
		{			
			abasConnection.close();
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view);
	}
}
