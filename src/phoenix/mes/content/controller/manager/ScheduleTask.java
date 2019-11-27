package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.GenericTask.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.SelectedWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class ScheduleTask
 */
public class ScheduleTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String encodedURL = response.encodeRedirectURL("/Logout");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new  AppBuild(request);
		if(!ab.isStabile()){
			doGet(request,response);
			return;
		}
		SelectedWorkstation ws = new SelectedWorkstation(request);
		String currentId = request.getParameter("currentId");
		String targetId = request.getParameter("targetId");
		String firstId = request.getParameter("firstId");
		boolean isFirstPlaced = false;
		boolean firstInProgress = false;
		String responseStr ="";
		if(!"".equals(firstId) && firstId != null) {
			isFirstPlaced = true;
		}
		IdImpl id = (null != targetId && !"".equals(targetId) ? (IdImpl) IdImpl.valueOf(targetId) : (IdImpl) IdImpl.NULLREF);
		AbasConnection abasConnection = null;
		User user = null;
    	try {
			user = new User(request);
    		HttpSession session = request.getSession();
     	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
        	Task task = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(currentId), abasConnection);
        	if(isFirstPlaced) {
        		Task firstTask = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(firstId), abasConnection);
        		Status fisrtTaskStatus = firstTask.getDetails(abasConnection).getStatus();
        		if(fisrtTaskStatus == Status.IN_PROGRESS || fisrtTaskStatus == Status.INTERRUPTED) {
        			firstInProgress = true;
        		}
        	}
//    		String nextId = request.getParameter("nextId");
//        	boolean nextIsInProgress = ("".equals(nextId) || nextId == null ? false : ((AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(nextId), abasConnection)).getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true : false));
//        	if(task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS && !nextIsInProgress) {
            if(task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS && !firstInProgress) {
            	task.schedule(AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection), id, abasConnection);
            	responseStr = "success";                               	
            }
		}catch(LoginException | SQLException e)
    	{
    		System.out.println(e);			
    		try {
				new Log(request).logFaliure((user == null? "null" : user.getUsername()), FaliureType.TASK_LIST_NAVIGATION, e.toString());
			}catch(SQLException exc) {
			}
    	} catch (AbasFunctionException e) {
			int errorCode = e.getErrorCode();
			if (errorCode != 6 && errorCode != 7 && errorCode != 8) {
				try {
					String workstation = "";
					if (ws != null) {
						workstation = ws.group + " - " + ws.no;
					}
					String errorText = Log.getErrorText(errorCode);
		    		responseStr = "abasError";
					new Log(request).logFaliure((user == null? "null" : user.getUsername()), FaliureType.TASK_SUBMIT, e.toString(), workstation);
					request.setAttribute("abasError", errorText);
				} catch (SQLException exc) {
				}
			}
		}finally
    	{
    		try
    		{
            	abasConnection.close();
    		}
    		catch(Exception e)
    		{}
    	}

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(responseStr); 
	}
}
