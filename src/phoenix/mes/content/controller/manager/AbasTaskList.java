package phoenix.mes.content.controller.manager;

import java.io.IOException;
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
import phoenix.mes.content.AppBuild;
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
		Workstation ws = new Workstation(request,ab.isTest());
		if(ws.getSelectedStation() == null)
		{
			return;
		}
		AbasConnection<EDPSession> abasConnection = null;
		String date = request.getParameter("date");
		AbasDate abasDate = ("" == date ? AbasDate.INFINITY : AbasDate.valueOf(date));
		try {
			HttpSession session = request.getSession();
    		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), ab.isTest());
			request.setAttribute("AbasList",  AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getUnassignedTasks(abasDate, abasConnection));
			request.setAttribute("abasConnection", abasConnection);

		}catch(LoginException e){
			System.out.println(e);
		}finally
		{			
			abasConnection.close();
		}
		
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write(RenderView.render("/Views/Manager/Todo/Partial/AbasList.jsp", request, response));
	}
}
