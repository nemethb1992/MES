package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.Task.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.SelectedWorkstation;
import phoenix.mes.content.controller.User;
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

		AppBuild ab = new  AppBuild(request);
		if(!ab.isStable()){
			doGet(request,response);
			return;
		}
		SelectedWorkstation ws = new SelectedWorkstation(request);
		String currentId = request.getParameter("currentId");
		String targetId = request.getParameter("targetId");
		IdImpl id = (null != targetId && !"".equals(targetId) ? (IdImpl) IdImpl.valueOf(targetId) : (IdImpl) IdImpl.NULLREF);
		AbasConnection<EDPSession> abasConnection = null;
		
    	try {
			User user = new User(request);
    		HttpSession session = request.getSession();
     	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
        	Task task = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(currentId), abasConnection);
    		String nextId = request.getParameter("nextId");
        	boolean nextIsInProgress = ("".equals(nextId) || nextId == null ? false : ((AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(nextId), abasConnection)).getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true : false));
        	if(task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS && !nextIsInProgress) {
            	task.schedule(AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection), id, abasConnection);
        	}
		}catch(LoginException | SQLException | AbasFunctionException e)
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
