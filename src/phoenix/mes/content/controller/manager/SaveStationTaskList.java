package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import phoenix.mes.content.controller.SelectedWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

public class SaveStationTaskList extends HttpServlet {
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
		String[] taskIdList = request.getParameterValues("StationTaskList[]");
		AbasConnection abasConnection = null;
		
    	try {
			User user = new User(request);
    		HttpSession session = request.getSession();
     	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), ab.isTest());
    		Task task = null;
    		for (int i = 0; i < taskIdList.length; i++) {
        		task = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(taskIdList[i]), abasConnection);
        		
          		IdImpl prevTaskId = (IdImpl) IdImpl.NULLREF;
        		if(i > 0 && task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS) {
        			prevTaskId = (IdImpl) IdImpl.valueOf(taskIdList[i-1]);
        		}
        		task.schedule(AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection), prevTaskId, abasConnection);         	
			}

		}catch(LoginException | SQLException | AbasFunctionException e)
    	{
    		System.out.println(e);			
    		try {
				new Log(request).logFaliure(FaliureType.TASK_LIST_NAVIGATION, e.getMessage());
			}catch(SQLException exc) {
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
	}

}
