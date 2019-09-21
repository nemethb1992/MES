package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import de.abas.erp.common.type.AbasDate;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.SelectedWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class AbasTaskList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		String workstation = (String) request.getParameter("ws");
		SelectedWorkstation ws = new SelectedWorkstation(request,workstation);
		if(ws.getGroup().equals(null))
		{
			return;
		}
		String view = "";
		AbasConnection abasConnection = null;
		String date = request.getParameter("date");
		AbasDate abasDate = (date.isEmpty() ? AbasDate.INFINITY : AbasDate.valueOf(date));
		try {
			User user = new User(request);
			OutputFormatter of = (OutputFormatter)request.getSession().getAttribute("OutputFormatter");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
			List<Task> task = (List<Task>)AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getUnassignedTasks(abasDate, abasConnection);
			request.setAttribute("AbasList",task);
			request.setAttribute("OutputFormatter", of);
			request.setAttribute("abasConnection", abasConnection);
			view = RenderView.render("/Views/Manager/Todo/Partial/AbasList.jsp", request, response);
		}catch(LoginException | SQLException e){
			System.out.println(e);
			try {
				new Log(request).logFaliure(FaliureType.TASK_LIST_LOAD, e.getMessage());
			}catch(SQLException exc) {
			}
		}finally
		{			
			abasConnection.close();
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view);
	}
}
