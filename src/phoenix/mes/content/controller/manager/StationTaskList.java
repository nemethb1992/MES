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
import phoenix.mes.content.Dictionary;
import phoenix.mes.content.Dictionary.Entry;


public class StationTaskList extends HttpServlet {
	
	protected static final long serialVersionUID = 1L;
	protected Dictionary dict;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
 	   	String username=(String)session.getAttribute("username");
 	   	String pass=(String)session.getAttribute("pass");
 	   	String station = (String)session.getAttribute("selectedStation");
		dict  = (Dictionary)session.getAttribute("Dictionary");
 	    
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(StationList(username,pass,station));
	}
	
    protected String StationList(String username, String pass, String station)
    {
    	String layout = "";
    	List<Task> list = new ArrayList<Task>();
    	int stationNo;
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
        	String[] stationSplit = station.split("!");
        	stationNo = Integer.parseInt(stationSplit[1]);
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dict.getLanguage(), true);
        	list = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection).getExecutableTasks(abasConnection);

          	for (Task task: list) {
          		final Task.Details taskDetails = task.getDetails(abasConnection);
          		layout += "					<div class='dnd-container col-12 px-0' value='3'>\r\n" + 
          				"						<div class='container px-0'>\r\n" + 
          				"							<div class='row w-100 mx-auto'>\r\n" + 
						"								<div class='col-4 pr-0 py-2 dnd-input-div'>" + 
						"									<p>"+dict.getWord(Entry.WORKSHEET_NO)+"</p>\r\n" +  //Munkalapszám
						"									<textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getWorkSlipNo()+"</textarea>\r\n" + 
						"									<p>"+dict.getWord(Entry.ARTICLE)+"</p>\r\n"+   //Cikkszám
						"									<textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductIdNo()+"</textarea>\r\n" + 
						"									<p>"+dict.getWord(Entry.SEARCH_WORD)+"</p>\r\n" +   //Keresőszó
						"									<textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductSwd()+"</textarea>\r\n" + 
						"								</div>\r\n" + 
						"								<div class='col-6 pr-0 py-2 dnd-input-div'>\r\n" + 
						"									<p>"+dict.getWord(Entry.PLACE_OF_USE)+"</p>\r\n" +  //Felhasználás
						"									<textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getUsage()+"</textarea>\r\n" + 
						"									<p>"+dict.getWord(Entry.NAME)+"</p>\r\n" +  //Termék megnevezés
						"									<textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductDescription()+"</textarea>\r\n" + 
						"								</div>\r\n" + 
          				"								<div class='col-2 dnd-input-div px-0'>\r\n" + 
          				"									<div class='row w-100 mx-auto h-100 d-flex'>\r\n" + 
          				"										<div class='col-12 px-0'>\r\n" + 
          				"											<input class='h-100 w-100 task-panel-button mini-button up-task-button' type='button'>\r\n" + 
          				"										</div>\r\n" + 
          				"										<div class='col-12 my-1 px-0'>\r\n" + 
          				"											<input class='h-100 w-100 task-panel-button mini-button remove-task-button' type='button'>\r\n" + 
          				"										</div>\r\n" + 
          				"										<div class='col-12 px-0'>\r\n" + 
          				"											<input class='h-100 w-100 task-panel-button mini-button down-task-button' type='button'>\r\n" + 
          				"										</div>\r\n" + 
          				"									</div>\r\n" + 
          				"								</div>\r\n" + 
          				"							</div>\r\n" + 
          				"						</div>\r\n" + 
          				"					</div>";
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

    	return layout;
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
