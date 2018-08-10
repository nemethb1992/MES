package Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Session.Session_Datas;
import de.abas.ceks.jedp.EDPException;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.DbContext;
import phoenix.mes.abas.ObjectFactory;
import phoenix.mes.abas.Task;


public class StationTaskList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String layout ="";
	String station= "";
    public StationTaskList() {
        super();
    }
    private String StationList()
    {
    	List<Task> li = new ArrayList<Task>();
    	int stationNo;
    	DbContext abasSession = null;
    	try {
        	AbasDate date = AbasDate.valueOf("20180809");
        	String[] Station = station.split("-");
        	stationNo = Integer.parseInt(Station[1]);
        	
        	abasSession = ObjectFactory.startAbasSession(Session_Datas.getUsername(), Session_Datas.getPassword(), true);

        	li = ObjectFactory.createWorkStation(Station[0], stationNo, abasSession).getExecutableTasks(abasSession);
        	
          	for (Task task: li) {
          		final Task.Details taskDetails = task.getDetails(abasSession);
          		layout += "			<div class='dnd-container'><div class='icon-form dnd-icon pass-item pass-item-remove' OnClick='RemoveFromList(this)' value='remove'></div>\r\n" + 
          				"					<div class='dnd-input-container'>\r\n" + 
          				"						<div class='dnd-upper'>\r\n" + 
          				"							<div class='dnd-input-div'>\r\n" + 
          				"								<p>Munkaszám</p>\r\n" + 
            			"								<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getWorkSlipNo()+"'>\r\n" + 
            			"							</div>\r\n" + 
            			"							<div class='dnd-input-div'>\r\n" + 
            			"								<p>Cikkszám</p>\r\n" + 
            			"								<input disabled class='dnd-input dnd-in2' value='"+taskDetails.getProductIdNo()+"'>\r\n" + 
            			"							</div>\r\n" + 
            			"							<div class='dnd-input-div'>\r\n" + 
            			"								<p>Keresőszó</p>\r\n" + 
            			"								<input disabled class='dnd-input dnd-in3' value='"+taskDetails.getProductSwd()+"'>\r\n" + 
          				"							</div>\r\n" + 
          				"						</div>\r\n" + 
          				"						<div class='dnd-downer'>\r\n" + 
          				"							<div class='dnd-input-div'>\r\n" + 
          				"								<p>Termék megnevezés</p>\r\n" + 
          				"								<input disabled class='dnd-input dnd-in4' value='"+taskDetails.getProductDescription()+"'>\r\n" + 
          				"							</div>\r\n" + 
          				"							<div class='dnd-input-div'>\r\n" + 
          				"								<p>Termék megnevezés 2</p>\r\n" + 
          				"								<input disabled class='dnd-input dnd-in5' value='"+taskDetails.getProductDescription2()+"'>\r\n" + 
          				"							</div>\r\n" + 
          				"							<div class='dnd-input-div'>\r\n" + 
          				"								<p>Felhasználás</p>\r\n" + 
          				"								<input disabled class='dnd-input dnd-in6' value='"+taskDetails.getUsage()+"'>\r\n" + 
          				"							</div>\r\n" + 
          				"						</div>\r\n" + 
          				"					</div>\r\n" + 
          				"				</div>";
          	}
    	}catch(EDPException e)
    	{
    		System.out.println(e);
    	}finally
    	{
    		try
    		{
        		abasSession.close();
    		}
    		catch(Exception e)
    		{}
    	}

    	return layout;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		station = request.getParameter("station");
		
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(StationList());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
