package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;


public class StationTaskList extends HttpServlet {
	
	protected static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}
	
	protected String[] StationList(String username, String pass, String station, OutputFormatter of, HttpServletRequest request)
	{ 
		StringBuilder layout = new StringBuilder();
		List<Task> list = new ArrayList<Task>();
		int stationNo;
		AbasConnection<EDPSession> abasConnection = null;        	

		BigDecimal summedProductionTime = BigDecimal.ZERO;

		try {
			String[] stationSplit = station.split("!");
			stationNo = Integer.parseInt(stationSplit[1]);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), new AppBuild(request).isTest());
			list = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0], stationNo, abasConnection).getScheduledTasks(abasConnection);


			for (Task task: list) {
				final Task.Details taskDetails = task.getDetails(abasConnection);
				boolean progress = task.isInProgress(abasConnection);
				summedProductionTime = summedProductionTime.add(taskDetails.getCalculatedProductionTime());
				layout.append("<div class='dnd-container "+(taskDetails.isSuspendedTask() ? "dnd-container-suspended" : "")+" "+(task.isInProgress(abasConnection) ? "dnd-container-inprogress" : "")+" col-12 px-0' value='3'><input class='d-none workSlipId' value='"+task.getWorkSlipId()+"'><div class='container px-0'><div class='row w-100 mx-auto'><div class='"+(progress ? "col-6" : "col-4")+" pr-0 py-2 dnd-input-div'>");
				layout.append("<p>"+of.getWord(DictionaryEntry.WORKSHEET_NO)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getWorkSlipNo()+"</textarea>");
				layout.append("<p>"+of.getWord(DictionaryEntry.ARTICLE)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductIdNo()+"</textarea>");
				layout.append("<p>"+of.getWord(DictionaryEntry.SEARCH_WORD)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductSwd()+"</textarea>");
				layout.append("</div><div class='col-6 pr-0 py-2 dnd-input-div'>");
				layout.append("<p>"+of.getWord(DictionaryEntry.PLACE_OF_USE)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getUsage()+"</textarea>");
				layout.append("<p>"+of.getWord(DictionaryEntry.NAME)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductDescription()+"</textarea>");
				layout.append("<p>"+of.getWord(DictionaryEntry.CALCULATED_PROD_TIME)+"</p><textarea disabled class='dnd-input dnd-in1'>"+of.formatTime(taskDetails.getCalculatedProductionTime())+"</textarea>");
				layout.append("</div><div  class='col-2 "+(progress ? "d-none" : "")+" dnd-input-div px-0'><div class='row w-100 mx-auto h-100 d-flex'><div class='col-12 px-0'>");
				layout.append("<input class='h-100 w-100 task-panel-button mini-button up-task-button' onclick='MoveTaskUp(this)' type='button'></div><div class='col-12 my-1 px-0'>");
				layout.append("<input class='h-100 w-100 task-panel-button mini-button remove-task-button' onclick='RemoveFromStation(this)' type='button'></div><div class='col-12 px-0'>");
				layout.append("<input class='h-100 w-100 task-panel-button mini-button down-task-button' onclick='MoveTaskDown(this)' type='button'></div></div></div></div></div></div>");
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


		String[] responseArr = new String[2];

		responseArr[0] = layout.toString();
		responseArr[1] = of.formatTime(summedProductionTime);
		
		return responseArr;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
 	   	String username=(String)session.getAttribute("username");
 	   	String pass=(String)session.getAttribute("pass");
 	   	String station = (String)session.getAttribute("selectedStation");
 	   	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

 	   	
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().write(new Gson().toJson(StationList(username,pass,station, of, request)));
	}

}
