package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.OperatingLanguage;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.Task.BomElement;
import phoenix.mes.content.Dictionary;
import phoenix.mes.content.PostgreSqlOperationsMES;
import phoenix.mes.content.Dictionary.Entry;


public class DataSheet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected Dictionary dict;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String wsCode;
		String wsName = "";
		Task.Details taskDetails;
		Task task ;
		
		HttpSession session = request.getSession();
		AbasConnection<EDPSession> abasConnection = null;

		String username=(String)session.getAttribute("username");
		String pass=(String)session.getAttribute("pass");
		String tab = request.getParameter("tabNo");
		dict = (Dictionary)session.getAttribute("Dictionary");
		wsCode="234PG!1";
		String view = "";
		
		// 	    String workstation=(String)session.getAttribute("workstation");
		//		String[] stationSplit = workstation.split("!");
		//			int stationNo = Integer.parseInt(stationSplit[1]);
		//        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dictionary.getLanguage(), true);
		//        	Task nextTask = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0],stationNo, abasConnection).getNextExecutableTask(abasConnection);

		try {
			wsName = getWorkStationName(wsCode);
		} catch (SQLException e) {
			wsName = " - ";
		}


		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dict.getLanguage(), true);
			task = (Task)session.getAttribute("Task");
			if(task == null)
			{
				task = AbasObjectFactory.INSTANCE.createTask(new IdImpl("(8027770,9,0)"), abasConnection); //"(7896209,9,0)"
				session.setAttribute("Task", task);
				taskDetails = task.getDetails(abasConnection);
			}

			taskDetails = task.getDetails(abasConnection);
			
	    	
			switch (tab) {
			case "1":
				view = getDataSheet(taskDetails,wsCode,wsName);
				break;
			case "2":
				view = getDocuments(taskDetails);
				break;
			case "3":
				view = getBom(taskDetails);
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

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view); 
	}
	
	protected String getDataSheet(Task.Details taskDetails, String wsCode, String wsName)
	{
		
		 String[] stationSplit = wsCode.split("!");
		 
		 String view = "				<div class='container-fluid px-0'>\r\n" + 
		 		"							<div class='row data-row mx-3 mt-3'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.WORKSTATION)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled value='"+stationSplit[0]+" - "+stationSplit[1]+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.WORKSHEET_NO)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getWorkSlipNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.GET_STARTED)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getStartDate()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.WORKSTATION_NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+wsName+"'>\r\n" +
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.PLACE_OF_USE)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getUsage()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"							<div class='row data-row m-3'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.ARTICLE)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductIdNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.SEARCH_WORD)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductSwd()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.OPEN_QUANTITY)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOutstandingQuantity()+" "+taskDetails.getStockUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductDescription()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.NAME)+" 2</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getProductDescription2()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"							<div class='row data-row m-3'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.OPERATION_NUMEBER)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOperationIdNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.SEARCH_WORD)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOperationSwd()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getOperationDescription()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.EXECUTION_NO)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getNumberOfExecutions()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.SETTING_TIME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text' disabled  value='"+taskDetails.getSetupTime()+" "+taskDetails.getSetupTimeUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p>"+dict.getWord(Entry.TIME_FOR_PCS)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 h6' type='text disabled  value='"+taskDetails.getUnitTime()+" "+taskDetails.getUnitTimeUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12  px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='mb-0'>"+dict.getWord(Entry.PRODUCTION_INFO)+"</p>\r\n" + 
		 		"										<textarea class='px-2 w-100 h6'  disabled >"+taskDetails.getOperationReservationText()+"</textarea>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"						</div>";
		 
		return view;
	}
	
	protected String getDocuments(Task.Details data)
	{
    	String view = "<div class='list-group dokumentum-list'><button type='button' class='list-group-item list-group-item-action active disabled'>"+dict.getWord(Entry.DOCUMENTS)+"</button>";
    	for (int i = 0; i < 8; i++) {
    		view +="<button type='button' onclick='openAsset(this)' class='document-button list-group-item list-group-item-action'>Dokumentum "+i+"</button>";
		}
    	view += "</div>";
		return view;
	}
	
	protected String getBom(Task.Details taskDetails)
	{

    	List<BomElement> li = taskDetails.getBom();
		
    	String view = "				<table class=\"table table-striped mytable\">\r\n" + 
    			"  								<thead>\r\n" + 
    			"  								  <tr>\r\n" + 
    			"     								 <th scope=\"col\">"+dict.getWord(Entry.ARTICLE)+"</th>\r\n" + 
    			"     								 <th scope=\"col\">"+dict.getWord(Entry.SEARCH_WORD)+"</th>\r\n" +  
    			"   									 <th scope=\"col\">"+dict.getWord(Entry.NAME)+" 1</th>\r\n" +  
    			"  									 <th scope=\"col\">"+dict.getWord(Entry.NAME)+" 2</th>\r\n" +   
    			"  									 <th scope=\"col\">"+dict.getWord(Entry.PLUG_IN_QUANTITY)+"</th>\r\n" + 
    			"   								  </tr>\r\n" + 
    			"  								</thead>\r\n" + 
    			"  								<tbody class='darabjegyz-tbody'>";
    	for (BomElement bomItem: li) {
    		view += "<tr>"+ 
    				"<th scope='row'>"+bomItem.getIdNo()+"</th>" +
    				"<td>"+bomItem.getSwd()+"</td>" + 
    				"<td>"+bomItem.getDescription()+"</td>" + 
    				"<td>"+bomItem.getDescription2()+"</td>" + 
    				"<td>"+bomItem.getQuantityPerProduct()+" "+bomItem.getStockUnit()+"</td>" + 
    				"</tr>";
		}		
    	view += "</tbody></table>";
		return view;
	}
	
	protected String getDescription(Task.Details taskDetails)
	{
		String view ="					<div class='conteiner-fluid h-100'>\r\n" + 
				"							<div class='row h-100 m-3'>\r\n" + 
				"								<div class='col'>\r\n" + 
				"									<p class='h5 p-2'>"+dict.getWord(Entry.INFO_ARTICLE)+" 1</p>\r\n" + 
				"									<textarea disabled class='BigTextInput light-shadow'>"+taskDetails.getSalesOrderItemText()+"</textarea>\r\n" + 
				"								</div>\r\n" + 
				"								<div class='col'>\r\n" + 
				"									<p class='h5 p-2'>"+dict.getWord(Entry.INFO_ARTICLE)+" 2</p>\r\n" + 
				"									<textarea disabled class='BigTextInput light-shadow'>"+taskDetails.getSalesOrderItemText2()+"</textarea>\r\n" + 
				"								</div>\r\n" + 
				"							</div>\r\n" + 
				"						</div>";
		
		return view;
	}
	
	protected String getWorkStationName(String wsCode) throws SQLException
	{
		PostgreSqlOperationsMES postgreSql = new PostgreSqlOperationsMES(true); 
		OperatingLanguage language = dict.getLanguage();
		String[] stationSplit = wsCode.split("!");
		String command, field;
		
		switch (language) {
		case en:
			command = "SELECT nev_en FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_en";
			break;
		case de:
			command = "SELECT nev_de FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_de";
			break;
		case hu:
		default:
			command = "SELECT nev_hu FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_hu";
			break;
		}
		
		return postgreSql.sqlSingleQuery(command, field);
	}
		
}
