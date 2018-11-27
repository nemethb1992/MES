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
import phoenix.mes.abas.Task.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class ScheduleTask
 */
public class ScheduleTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Logout").forward(request, response);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(!(new AppBuild(null)).isStable(request)){
			doGet(request,response);
			return;
		}
		
		HttpSession session = request.getSession();
		
		AbasConnection<EDPSession> abasConnection = null;
		
 	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
 	   	String station = (String)session.getAttribute("selectedStation");
		String currentId = request.getParameter("currentId");
		String targetId = request.getParameter("targetId");
		
		IdImpl id = (null != targetId && !"".equals(targetId) ? (IdImpl) IdImpl.valueOf(targetId) : (IdImpl) IdImpl.NULLREF);
		
    	try {        	
    		String[] stationSplit = station.split("!");
    		int stationNo = Integer.parseInt(stationSplit[1]);
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
        	Task task = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(currentId), abasConnection);
    		String nextId = request.getParameter("nextId");
        	boolean nextIsInProgress = ("".equals(nextId) || nextId == null ? false : ((AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(nextId), abasConnection)).getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true : false));
        	if(task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS && !nextIsInProgress) {
            	task.schedule(AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection), id, abasConnection);
        	}
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
