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
import de.abas.erp.common.type.enums.EnumLanguageCode;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;


public class StationTaskList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String language;
	EnumLanguageCode abasLanguage;
	String layout ="";
	String station= "";
	String username;
	String pass;
	
    public StationTaskList() {
        super();
    }
    private String StationList()
    {
    	List<Task> li = new ArrayList<Task>();
    	int stationNo;
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
        	String[] Station = station.split("-");
        	stationNo = Integer.parseInt(Station[1]);
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, abasLanguage, true);
        	li = AbasObjectFactory.INSTANCE.createWorkStation(Station[0], stationNo, abasConnection).getExecutableTasks(abasConnection);

          	for (Task task: li) {
          		final Task.Details taskDetails = task.getDetails(abasConnection);
          		layout += "					<div class='dnd-container col-12 px-0' value='3'>\r\n" + 
          				"						<div class='container px-0'>\r\n" + 
          				"							<div class='row w-100 mx-auto'>\r\n" + 
          				"								<div class='col-5 py-2 dnd-input-div'>\r\n" + 
          				"									<p>Munkaszám</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getWorkSlipNo()+"'>\r\n" + 
          				"									<p>Cikkszám</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductIdNo()+"'>\r\n" + 
          				"									<p>Keresöszó</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductSwd()+"'>\r\n" + 
          				"								</div>\r\n" + 
          				"								<div class='col-5 py-2 dnd-input-div'>\r\n" + 
          				"									<p>Felhasználás</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getUsage()+"'>\r\n" + 
          				"									<p>Termék megnevezés</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductDescription()+"'>\r\n" + 
          				"									<p>Termék megnevezés 2</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductDescription2()+"'>\r\n" + 
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	    HttpSession session = request.getSession();
 	    username=(String)session.getAttribute("username");
 	    pass=(String)session.getAttribute("pass");
		language = (String)session.getAttribute("language");
		station = (String)session.getAttribute("selectedStation");
		abasLanguage = (EnumLanguageCode)session.getAttribute("abasLanguageType");
 	    
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(StationList());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
