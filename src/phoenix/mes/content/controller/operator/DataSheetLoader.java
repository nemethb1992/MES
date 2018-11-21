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
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;


public class DataSheetLoader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// TODO split
		String workstation = (String)session.getAttribute("operatorWorkstation");
		// TODO
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
//		workstation="234PG!1";
		String view = "";
		
		boolean testSystem = new AppBuild(request).isTest();
		        	
		if(workstation != null)
		{
			String wsName = "";
			try {
				wsName = getWorkStationName(workstation, of, testSystem);
			} catch (SQLException e) {
				wsName = " - ";
			}
			
			String username = (String)session.getAttribute("username");
			String pass = (String)session.getAttribute("pass");
			AbasConnection<EDPSession> abasConnection = null;
			
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), testSystem);
				Task task = (Task)session.getAttribute("Task");

				if(task == null)
				{
					doGet(request,response);
					return;
				}
				Task.Details taskDetails = task.getDetails(abasConnection);				    	
				switch (request.getParameter("tabNo")) {
				case "1":
					view = getDataSheet(taskDetails,workstation,wsName,of,request);
					break;
				case "2":
					view = getDocuments(taskDetails, of);
					break;
				case "3":
					view = getBom(taskDetails, of, request);
					break;
				case "4":
					view = getItemTexts(taskDetails, of);
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
					if (null != abasConnection) {
						abasConnection.close();
					}
				} catch (Throwable t) {
				}
			}
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view); 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	
	}
	
	protected String getDataSheet(Task.Details taskDetails, String workstation, String wsName, OutputFormatter of, HttpServletRequest request)
	{

		// TODO StringBuilder
		 String layout = "				<div class='container-fluid my-white-container h-100 px-0'><div class='row data-row mx-3 mt-0'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.WORKSTATION)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled value='"+workstation.split("!")[0]+" - "+workstation.split("!")[1]+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.WORKSHEET_NO)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getWorkSlipNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.GET_STARTED)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getStartDate()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.WORKSTATION_NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+wsName+"'>\r\n" +
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.PLACE_OF_USE)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getUsage()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"							<div class='row data-row mx-3 my-0'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.ARTICLE)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getProductIdNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.SEARCH_WORD)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getProductSwd()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.OPEN_QUANTITY)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())+" "+taskDetails.getStockUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getProductDescription()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.NAME)+" 2</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getProductDescription2()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div>\r\n" + 
		 		"							<div class='row data-row mx-3 my-0'>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.OPERATION_NUMEBER)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getOperationIdNo()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.SEARCH_WORD)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getOperationSwd()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.NAME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+taskDetails.getOperationDescription()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.EXECUTION_NO)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+of.formatWithoutTrailingZeroes(taskDetails.getNumberOfExecutions())+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.SETTING_TIME)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text' disabled  value='"+of.formatWithoutTrailingZeroes(taskDetails.getSetupTime())+" "+taskDetails.getSetupTimeUnit()+"'>\r\n" + 
		 		"									</div>\r\n" + 
		 		"									<div class='inputContainer'>\r\n" + 
		 		"										<p class='task-data-label'>"+of.getWord(DictionaryEntry.TIME_FOR_PCS)+"</p>\r\n" + 
		 		"										<input class='px-2 w-100 task-data-value' type='text disabled  value='"+of.formatWithoutTrailingZeroes(taskDetails.getUnitTime())+" "+taskDetails.getUnitTimeUnit()+"'>\r\n" + 
		 		"									</div>" + 
		 		"								</div>" + 
		 		"								<div class='col-12 px-3'>" + 
		 		"									<div class='inputContainer'>" + 
		 		"										<p class='mb-0 task-data-label'>"+of.getWord(DictionaryEntry.PRODUCTION_INFO)+"</p>\r\n" + 
		 		"										<textarea class='px-2 w-100 task-data-value'  disabled >"+taskDetails.getOperationReservationText()+"</textarea>\r\n" + 
		 		"									</div>\r\n" + 
		 		"								</div>\r\n" + 
		 		"							</div></div>";
		 
		return layout;
	}
	
	protected String getDocuments(Task.Details data, OutputFormatter of)
	{
    	String view = "<div class='list-group dokumentum-list'><button type='button' class='list-group-item list-group-item-action active disabled'>"+of.getWord(DictionaryEntry.DOCUMENTS)+"</button>";
    	for (int i = 0; i < 8; i++) {
    		view +="<button type='button' onclick='openAsset(this)' class='document-button list-group-item list-group-item-action'>Dokumentum "+i+"</button>";
		}
    	view += "</div>";
		return view;
	}
	
	protected String getBom(Task.Details taskDetails,OutputFormatter of, HttpServletRequest request)
	{

    	List<BomElement> li = taskDetails.getBom();
		
    	String view = "				<table class=\"table table-striped mytable\">\r\n" + 
    			"  								<thead>\r\n" + 
    			"  								  <tr>\r\n" + 
    			"     								 <th scope=\"col\">"+of.getWord(DictionaryEntry.ARTICLE)+"</th>\r\n" + 
    			"     								 <th scope=\"col\">"+of.getWord(DictionaryEntry.SEARCH_WORD)+"</th>\r\n" +  
    			"   									 <th scope=\"col\">"+of.getWord(DictionaryEntry.NAME)+" 1</th>\r\n" +  
    			"  									 <th scope=\"col\">"+of.getWord(DictionaryEntry.NAME)+" 2</th>\r\n" +   
    			"  									 <th scope=\"col\">"+of.getWord(DictionaryEntry.PLUG_IN_QUANTITY)+"</th>\r\n" + 
    			"   								  </tr>\r\n" + 
    			"  								</thead>\r\n" + 
    			"  								<tbody class='darabjegyz-tbody'>";
    	for (BomElement bomItem: li) {
    		view += "<tr>"+ 
    				"<th scope='row'>"+bomItem.getIdNo()+"</th>" +
    				"<td>"+bomItem.getSwd()+"</td>" + 
    				"<td>"+bomItem.getDescription()+"</td>" + 
    				"<td>"+bomItem.getDescription2()+"</td>" + 
    				"<td>"+of.formatWithoutTrailingZeroes(bomItem.getQuantityPerProduct())+" "+bomItem.getStockUnit()+"</td>" + 
    				"</tr>";
		}		
    	view += "</tbody></table>";
		return view;
	}
	
	protected String getItemTexts(Task.Details taskDetails, OutputFormatter of)
	{
		String view ="					<div class='conteiner-fluid h-100'>\r\n" + 
				"							<div class='row h-100 m-0'>\r\n" + 
				"								<div class='mx-3 col col-textarea light-shadow'>\r\n" + 
				"									<p class='h5 p-2'>"+of.getWord(DictionaryEntry.INFO_ARTICLE)+" 1</p>\r\n" + 
				"									<textarea disabled class='p-3 BigTextInput'>"+taskDetails.getSalesOrderItemText()+"</textarea>\r\n" + 
				"								</div>\r\n" + 
				"								<div class='mx-3 col col-textarea light-shadow'>\r\n" + 
				"									<p class='h5 p-2'>"+of.getWord(DictionaryEntry.INFO_ARTICLE)+" 2</p>\r\n" + 
				"									<textarea disabled class='p-3 BigTextInput'>"+taskDetails.getSalesOrderItemText2()+"</textarea>\r\n" + 
				"								</div>\r\n" + 
				"							</div>\r\n" + 
				"						</div>";
		
		return view;
	}
	
	public String getWorkStationName(String wsCode, OutputFormatter of, boolean testSystem) throws SQLException
	{
		PostgreSql postgreSql = new PostgreSql(testSystem); 
		Locale language = of.getLocale();
		String[] stationSplit = wsCode.split("!");
		String command, field;
		
		switch (language.getLanguage()) {
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
