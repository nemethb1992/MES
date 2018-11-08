package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;


public class StationTaskList extends HttpServlet {
	
	protected static final long serialVersionUID = 1L;
	protected OutputFormatter dict;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
 	   	String username=(String)session.getAttribute("username");
 	   	String pass=(String)session.getAttribute("pass");
 	   	String station = (String)session.getAttribute("selectedStation");
		dict  = (OutputFormatter)session.getAttribute("OutputFormatter");
 	    
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(StationList(username,pass,station));
	}
	
    protected String StationList(String username, String pass, String station)
    { 
		StringBuilder layout = new StringBuilder();
    	List<Task> list = new ArrayList<Task>();
    	int stationNo;
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
        	String[] stationSplit = station.split("!");
        	stationNo = Integer.parseInt(stationSplit[1]);
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dict.getLocale(), true);
        	list = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection).getScheduledTasks(abasConnection);

          	for (Task task: list) {
          		final Task.Details taskDetails = task.getDetails(abasConnection);

          		layout.append("<div class='dnd-container col-12 px-0' value='3'><div class='container px-0'><div class='row w-100 mx-auto'><div class='col-4 pr-0 py-2 dnd-input-div'>");
          		layout.append("<p>"+dict.getWord(DictionaryEntry.WORKSHEET_NO)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getWorkSlipNo()+"</textarea>");
          		layout.append("<p>"+dict.getWord(DictionaryEntry.ARTICLE)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductIdNo()+"</textarea>");
          		layout.append("<p>"+dict.getWord(DictionaryEntry.SEARCH_WORD)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductSwd()+"</textarea>");
          		layout.append("</div><div class='col-6 pr-0 py-2 dnd-input-div'>");
          		layout.append("<p>"+dict.getWord(DictionaryEntry.PLACE_OF_USE)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getUsage()+"</textarea>");
          		layout.append("<p>"+dict.getWord(DictionaryEntry.NAME)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductDescription()+"</textarea>");
          		layout.append("</div><div class='col-2 dnd-input-div px-0'><div class='row w-100 mx-auto h-100 d-flex'><div class='col-12 px-0'>");
          		layout.append("<input class='h-100 w-100 task-panel-button mini-button up-task-button' type='button'></div><div class='col-12 my-1 px-0'>");
          		layout.append("<input class='h-100 w-100 task-panel-button mini-button remove-task-button' type='button'></div><div class='col-12 px-0'>");
          		layout.append("<input class='h-100 w-100 task-panel-button mini-button down-task-button' type='button'></div></div></div></div></div></div>");
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

    	return layout.toString();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
