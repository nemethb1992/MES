package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.util.List;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class AbasTaskList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String station = (String)session.getAttribute("selectedStation");

		if(station == null)
		{
			return;
		}
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
		
		String date = request.getParameter("date");
		
		AbasConnection<EDPSession> abasConnection = null;
		String[] Station = station.split("!");
		AbasDate abasDate = ("" == date ? AbasDate.INFINITY : AbasDate.valueOf(date));
		String view = "";
		try {
			int stationNo = Integer.parseInt(Station[1]);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
			List<Task> li = AbasObjectFactory.INSTANCE.createWorkStation(Station[0], stationNo, abasConnection).getUnassignedTasks(abasDate, abasConnection);
			request.setAttribute("AbasList", li);
			request.setAttribute("abasConnection", abasConnection);
			view = RenderView.render("/Views/Manager/Todo/Partial/AbasList.jsp", request, response);

		}catch(LoginException e){
			System.out.println(e);
		}finally
		{			
			try {
			abasConnection.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}
		}
		
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write(view);
	}
}
