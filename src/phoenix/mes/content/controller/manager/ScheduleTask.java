package phoenix.mes.content.controller.manager;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;

/**
 * Servlet implementation class ScheduleTask
 */
public class ScheduleTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String targetId = request.getParameter("targetId");
		
		HttpSession session = request.getSession();
		
		AbasConnection<EDPSession> abasConnection = null;
		
		IdImpl id = (null != targetId && !"".equals(targetId) ? (IdImpl) IdImpl.valueOf(targetId) : (IdImpl) IdImpl.NULLREF);
		
//		String asd = 
//		if(null != targetId && !"".equals(targetId))
//		{
//			id = ;
//		}
//		else
//		{
//			id = ;
//		}

 	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
 	   	String station = (String)session.getAttribute("selectedStation");
		String currentId = request.getParameter("currentId");
		
    	try {        	
    		String[] stationSplit = station.split("!");
    		int stationNo = Integer.parseInt(stationSplit[1]);
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
        	Task task = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(currentId), abasConnection);
        	task.schedule(AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection), id, abasConnection);
		}catch(LoginException e)
    	{
    		System.out.println(e);
    	}finally
    	{
    		try
    		{
            	abasConnection.close();
    		}
    		catch(Exception e)
    		{}
    	}
	}

}
