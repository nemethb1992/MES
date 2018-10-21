package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;


public class AbasTaskList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	protected OutputFormatter outputFormatter;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String station = (String)session.getAttribute("selectedStation");
		if(station != null)
		{
			String username=(String)session.getAttribute("username");
			String pass=(String)session.getAttribute("pass");
			String date = request.getParameter("date");
			outputFormatter  = (OutputFormatter)session.getAttribute("OutputFormatter");
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write(abasList(station, date, username, pass,request));
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected String abasList(String station, String date, String username, String pass, HttpServletRequest request)
	{
		AbasConnection<EDPSession> abasConnection = null;
		String[] Station = station.split("!");
		AbasDate abasDate = ("" == date ? AbasDate.INFINITY : AbasDate.valueOf(date));
		StringBuilder layout = new StringBuilder();
//		String layout = "";
		try {
			int stationNo = Integer.parseInt(Station[1]);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, outputFormatter.getLocale(), true);
			List<Task> li = AbasObjectFactory.INSTANCE.createWorkStation(Station[0], stationNo, abasConnection).getUnassignedTasks(abasDate, abasConnection);
			layout.append("<div class='abas-list dnd-frame m-0 row light-shadow'>");
			
			for (Task task: li) {
				final Task.Details taskDetails = task.getDetails(abasConnection);
				String startDate = taskDetails.getStartDate().toString();
				String startDateFormated = startDate.substring(0,4) + "." + startDate.substring(4,6) + "." + startDate.substring(6,8) + ".";
				
				layout.append("<div class='dnd-container col-12 px-0' value='3' style='max-height: 80px;'><div class='container-fluid h-100'><div class='row h-100'><div class='col abas-listitem-data-col px-0'>");
				layout.append("<div class='container-fluid'><div class='row'><div class='col my-col-1 article-col px-1 pl-2 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.WORKSHEET_NO)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getWorkSlipNo()+"</textarea></div>");
				layout.append("<div class='col my-col-2 px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.ARTICLE)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductIdNo()+"</textarea></div>");
				layout.append("<div class='col my-col-3 px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.SEARCH_WORD)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getProductSwd()+"</textarea></div>");
				layout.append("<div class='col my-col-4 placeofuse-col  px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.PLACE_OF_USE)+"</p><textarea disabled class='dnd-input dnd-in1'>"+taskDetails.getUsage()+"</textarea></div>");
				layout.append("<div class='col my-col-5 px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.NAME)+"</p><textarea wrap='soft' class='dnd-input dnd-in1'>"+taskDetails.getProductDescription()+"</textarea></div>");
				layout.append("<div class='col my-col-6 px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.GET_STARTED)+"</p><textarea disabled class='dnd-input dnd-in1'>"+startDateFormated+"</textarea></div>");
				layout.append("<div class='col my-col-7 px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.OPEN_QUANTITY)+"</p>");
				layout.append("<textarea disabled class='dnd-input dnd-in1'>"+outputFormatter.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())+" "+taskDetails.getStockUnit()+"</textarea></div>");
				layout.append("<div class='col my-col-8 px-1 py-2 dnd-input-div'>");
				layout.append("<p>"+outputFormatter.getWord(DictionaryEntry.CALCULATED_PROD_TIME)+"</p><textarea disabled class='dnd-input dnd-in1'>"+outputFormatter.formatTime(taskDetails.getCalculatedProductionTime())+"</textarea></div>");
				layout.append("</div></div></div><div class='col col-button p-1'><div class='w-100 h-100 dnd-input-div px-0'><input class='h-100 w-100 task-panel-button' value='' type='button'>");
				layout.append("</div></div></div></div></div>");
				
				
				

			}
			layout.append("</div>");
		}catch(LoginException e){
			System.out.println(e);
		}finally
		{			
			try {
			abasConnection.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}
		}
		return layout.toString();
	}
}

