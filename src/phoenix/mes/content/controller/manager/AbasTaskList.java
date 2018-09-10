package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.EqualExpression;

import com.mysql.jdbc.ConnectionProperties;

import de.abas.ceks.jedp.EDPException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.DbContext;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.SessionData;
import phoenix.mes.content.LanguageSource;
import sun.security.x509.Extension;


public class AbasTaskList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    LanguageSource l = new LanguageSource();   

	String lng;
	String station="";
	String date="";
	

	
    public AbasTaskList() {
        super();
    }
	String Word(int index)
	{
		return l.LanguageSelector(lng, index);
	}
    private String AbasList()
    {

    	
    	String layout ="";
    	List<Task> li = new ArrayList<Task>();
    	int stationNo;
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
        	String[] Station = station.split("-");
        	AbasDate _AbasDate = AbasDate.valueOf(this.date);
        	stationNo = Integer.parseInt(Station[1]);
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(SessionData.getUsername(), SessionData.getPassword(), l.getAbasLanguage(), true);
        	li = AbasObjectFactory.INSTANCE.createWorkStation(Station[0], stationNo, abasConnection).getUnassignedTasks(_AbasDate, abasConnection);

          	for (Task task: li) {
          		final Task.Details taskDetails = task.getDetails(abasConnection);
          		layout += "					<div class='dnd-container col-12 px-0' value='3'>\r\n" + 
          				"						<div class='container px-0'>\r\n" + 
          				"							<div class='row w-100 mx-auto'>\r\n" + 
          				"								<div class='col-5 py-2 dnd-input-div'>" + 
          				"									<p>"+Word(5)+"</p>\r\n" +  //Munkalapszám
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getWorkSlipNo()+"'>\r\n" + 
          				"									<p>"+Word(6)+"</p>\r\n" +   //Cikkszám
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductIdNo()+"'>\r\n" + 
          				"									<p>"+Word(7)+"</p>\r\n" +   //Keresőszó
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductSwd()+"'>\r\n" + 
          				"								</div>\r\n" + 
          				"								<div class='col-5 py-2 dnd-input-div'>\r\n" + 
          				"									<p>Felhasználás</p>\r\n" +  //Felhasználás
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getUsage()+"'>\r\n" + 
          				"									<p>Termék megnevezés</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductDescription()+"'>\r\n" + 
          				"									<p>Termék megnevezés 2</p>\r\n" + 
          				"									<input disabled class='dnd-input dnd-in1' value='"+taskDetails.getProductDescription2()+"'>\r\n" + 
          				"								</div>\r\n" + 
          				"								<div class='col-2 dnd-input-div px-0'>\r\n" + 
          				"									<input class='h-100 w-100 task-panel-button' value='' type='button'>\r\n" + 
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

		String dateSeged [];
		station = request.getParameter("station");
		date = request.getParameter("date");
		if(date == "" || date == null)
		{
	    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	    	Date today = new Date();
	    	date = dateFormat.format(today);
		}
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
         for (Cookie cookie : cookies) {
           if (cookie.getName().equals("language")) {
        	   lng = cookie.getValue();
            }
          }
        }
		
	    response.setContentType("text/plain"); 
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(AbasList());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

