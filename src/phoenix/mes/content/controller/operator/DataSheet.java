package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

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
import phoenix.mes.abas.Task.BomElement;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;


public class DataSheet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected OutputFormatter outputFormatter;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String wsName = "";
		Task.Details taskDetails;
		Task task ;
		
		HttpSession session = request.getSession();
		AbasConnection<EDPSession> abasConnection = null;

		String username=(String)session.getAttribute("username");
		String pass=(String)session.getAttribute("pass");
		String tab = request.getParameter("tabNo");
		String workstation = (String)session.getAttribute("operatorWorkstation");
		outputFormatter = (OutputFormatter)session.getAttribute("OutputFormatter");
//		workstation="234PG!1!Test Workstation";
		String view = "";
		        	
		if(workstation != null)
		{
			try {
				wsName = getWorkStationName(workstation);
			} catch (SQLException e) {
				wsName = " - ";
			}
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, outputFormatter.getLocale(), true);
				task = (Task)session.getAttribute("Task");
				if(task == null)
				{
//					task = AbasObjectFactory.INSTANCE.createTask(new IdImpl("(7896209,9,0)"), abasConnection); // "(8027770,9,0)"
//					task = AbasObjectFactory.INSTANCE.createWorkStation("380PG",1, abasConnection).getNextExecutableTask(abasConnection);
					task = AbasObjectFactory.INSTANCE.createWorkStation(workstation.split("!")[0],Integer.parseInt(workstation.split("!")[1]), abasConnection).getFirstScheduledTask(abasConnection);
					session.setAttribute("Task", task);
					taskDetails = task.getDetails(abasConnection);
				}

				taskDetails = task.getDetails(abasConnection);				    	
				switch (tab) {
				case "1":
					view = getDataSheet(taskDetails,workstation,wsName,request);
					break;
				case "2":
					view = getDocuments(taskDetails);
					break;
				case "3":
					view = getBom(taskDetails, request);
					break;
				case "4":
					view = getDescription(taskDetails);
					break;
				default:
					break;
				}
				
			}catch(LoginException e)
			{
				System.out.println(e);
			}finally
			{
				try {
					abasConnection.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view); 
	}
	
	protected String getDataSheet(Task.Details taskDetails, String workstation, String wsName, HttpServletRequest request)
	{

		 String view = "				<div class='container-fluid px-0'>\r\n" + 
		 		"							<div class='row data-row mx-3 mt-3'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.WORKSTATION)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled value='"+workstation.split("!")[0]+" - "+workstation.split("!")[1]+" - "+wsName+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.WORKSHEET_NO)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getWorkSlipNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.GET_STARTED)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getStartDate()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.WORKSTATION_NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+wsName+"'>\r\n" +
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.PLACE_OF_USE)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getUsage()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"							<div class='row data-row m-3'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.ARTICLE)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductIdNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.SEARCH_WORD)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductSwd()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.OPEN_QUANTITY)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+outputFormatter.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())+" "+taskDetails.getStockUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductDescription()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.NAME)+" 2</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductDescription2()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"							<div class='row data-row m-3'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.OPERATION_NUMEBER)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOperationIdNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.SEARCH_WORD)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOperationSwd()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOperationDescription()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.EXECUTION_NO)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+outputFormatter.formatWithoutTrailingZeroes(taskDetails.getNumberOfExecutions())+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.SETTING_TIME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+outputFormatter.formatWithoutTrailingZeroes(taskDetails.getSetupTime())+" "+taskDetails.getSetupTimeUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+outputFormatter.getWord(DictionaryEntry.TIME_FOR_PCS)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text disabled  value='"+outputFormatter.formatWithoutTrailingZeroes(taskDetails.getUnitTime())+" "+taskDetails.getUnitTimeUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12  px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='mb-0'>"+outputFormatter.getWord(DictionaryEntry.PRODUCTION_INFO)+"</p>\r\n" + 
		 		"										<textarea class='px-2 w-100 h6'  disabled >"+taskDetails.getOperationReservationText()+"</textarea>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"						</div>";
		 
		return view;
	}
	
	protected String getDocuments(Task.Details data)
	{
    	String view = "<div class='list-group dokumentum-list'><button type='button' class='list-group-item list-group-item-action active disabled'>"+outputFormatter.getWord(DictionaryEntry.DOCUMENTS)+"</button>";
    	for (int i = 0; i < 8; i++) {
    		view +="<button type='button' onclick='openAsset(this)' class='document-button list-group-item list-group-item-action'>Dokumentum "+i+"</button>";
		}
    	view += "</div>";
		return view;
	}
	
	protected String getBom(Task.Details taskDetails, HttpServletRequest request)
	{

    	List<BomElement> li = taskDetails.getBom();
		
    	String view = "				<table class=\"table table-striped mytable\">\r\n" + 
    			"  								<thead>\r\n" + 
    			"  								  <tr>\r\n" + 
    			"     								 <th scope=\"col\">"+outputFormatter.getWord(DictionaryEntry.ARTICLE)+"</th>\r\n" + 
    			"     								 <th scope=\"col\">"+outputFormatter.getWord(DictionaryEntry.SEARCH_WORD)+"</th>\r\n" +  
    			"   									 <th scope=\"col\">"+outputFormatter.getWord(DictionaryEntry.NAME)+" 1</th>\r\n" +  
    			"  									 <th scope=\"col\">"+outputFormatter.getWord(DictionaryEntry.NAME)+" 2</th>\r\n" +   
    			"  									 <th scope=\"col\">"+outputFormatter.getWord(DictionaryEntry.PLUG_IN_QUANTITY)+"</th>\r\n" + 
    			"   								  </tr>\r\n" + 
    			"  								</thead>\r\n" + 
    			"  								<tbody class='darabjegyz-tbody'>";
    	for (BomElement bomItem: li) {
    		view += "<tr>"+ 
    				"<th scope='row'>"+bomItem.getIdNo()+"</th>" +
    				"<td>"+bomItem.getSwd()+"</td>" + 
    				"<td>"+bomItem.getDescription()+"</td>" + 
    				"<td>"+bomItem.getDescription2()+"</td>" + 
    				"<td>"+outputFormatter.formatWithoutTrailingZeroes(bomItem.getQuantityPerProduct())+" "+bomItem.getStockUnit()+"</td>" + 
    				"</tr>";
		}		
    	view += "</tbody></table>";
		return view;
	}
	
	protected String getDescription(Task.Details taskDetails)
	{
		String view ="					<div class='conteiner-fluid h-100'>\r\n" + 
				"							<div class='row h-100 m-3'>\r\n" + 
				"								<div class='mx-3 col col-textarea light-shadow'>\r\n" + 
				"									<p class='h5 p-2'>"+outputFormatter.getWord(DictionaryEntry.INFO_ARTICLE)+" 1</p>\r\n" + 
				"									<textarea disabled class='p-3 BigTextInput'>"+taskDetails.getSalesOrderItemText()+"</textarea>\r\n" + 
				"								</div>\r\n" + 
				"								<div class='mx-3 col col-textarea light-shadow'>\r\n" + 
				"									<p class='h5 p-2'>"+outputFormatter.getWord(DictionaryEntry.INFO_ARTICLE)+" 2</p>\r\n" + 
				"									<textarea disabled class='p-3 BigTextInput'>"+taskDetails.getSalesOrderItemText2()+"</textarea>\r\n" + 
				"								</div>\r\n" + 
				"							</div>\r\n" + 
				"						</div>";
		
		return view;
	}
	
	protected String getWorkStationName(String wsCode) throws SQLException
	{
		PostgreSql postgreSql = new PostgreSql(true); 
		Locale language = outputFormatter.getLocale();
		String[] stationSplit = wsCode.split("!");
		String command, field;
		
		switch (language.getDisplayLanguage()) {
		case "en":
			command = "SELECT nev_en FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_en";
			break;
		case "de":
			command = "SELECT nev_de FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_de";
			break;
		case "hu":
		default:
			command = "SELECT nev_hu FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_hu";
			break;
		}
		
		return postgreSql.sqlSingleQuery(command, field);
	}
		
}
