package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.Task.BomElement;
import phoenix.mes.content.Dictionary;
import phoenix.mes.content.Dictionary.Entry;


public class DataSheet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected Dictionary dict;
	protected String workstation;
	protected Task.Details taskDetails;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		dict = (Dictionary)session.getAttribute("Dictionary");
		String username=(String)session.getAttribute("username");
		String pass=(String)session.getAttribute("pass");
		String tab = request.getParameter("tabNo");
		// 	    String workstation=(String)session.getAttribute("workstation");
		//		String[] stationSplit = workstation.split("!");
		workstation="234PG!1";
		AbasConnection<EDPSession> abasConnection = null;

		String view = "";

		//			int stationNo = Integer.parseInt(stationSplit[1]);
		//        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dictionary.getLanguage(), true);
		//        	Task nextTask = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0],stationNo, abasConnection).getNextExecutableTask(abasConnection);

		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dict.getLanguage(), true);
			taskDetails = (Task.Details)session.getAttribute("Task");

			if(taskDetails == null)
			{
				Task nextTask = AbasObjectFactory.INSTANCE.createTask(new IdImpl("(7896209,9,0)"), abasConnection);
				taskDetails = nextTask.getDetails(abasConnection);
				session.setAttribute("Task", taskDetails);
			}
			
	    	List<BomElement> bom = taskDetails.getBom();
	    	
			switch (tab) {
			case "1":
				view = getDataSheet(taskDetails);
				break;
			case "2":
				view = getDocuments(taskDetails);
				break;
			case "3":
				view = getBom(bom);
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
			//TODO Jelezni a felhasználó felé.
		}finally
		{
			abasConnection.close();
		}

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view); 
	}
	
	protected String getDataSheet(Task.Details taskDetails)
	{
		
		 String[] stationSplit = workstation.split("!");
		 String view ="						<div class='container-fluid'>\r\n" + 
    			"								<div class='row'>\r\n" + 
    			"									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.WORKSTATION)+"</p>\r\n" +  //Munkaállomás
    			"											<input class='px-2 w-100' type='text' value='"+stationSplit[0]+" - "+stationSplit[1]+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.WORKSHEET_NO)+"</p>\r\n" +  //Munkalapszám
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getWorkSlipNo()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.ARTICLE)+"</p>\r\n" +  //Cikkszám
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductIdNo()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.SEARCH_WORD)+"</p>\r\n" +  //Keresőszó
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductSwd()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.OPERATION_NUMEBER)+"</p>\r\n" +  //Műveleti azonosító
    			"											<input class='px-2 w-100'  type='text' value='"+taskDetails.getOperationIdNo()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.SETTING_TIME)+"</p>\r\n" +  //Beállítási idő
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getSetupTime()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"									</div>\r\n" + 
    			"									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.NAME)+"</p>\r\n" +  //Megnevezés
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationDescription()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.PLACE_OF_USE)+"</p>\r\n" +  //Felhasználás
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getUsage()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.SEARCH_WORD)+"</p>\r\n" +  //Keresőszó
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationSwd()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.NAME)+"</p>\r\n" +  //Megnevezés
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductDescription()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.TIME_FOR_PCS)+"</p>\r\n" +  //Darabidő
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getUnitTime()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
    			"											<p>"+dict.getWord(Entry.OPEN_QUANTITY)+"</p>\r\n" +  //Nyitott mennyiség
    			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOutstandingQuantity()+"'/>\r\n" + 
    			"										</div>\r\n" + 
    			"									</div>\r\n" + 
    			"									<div class='col-12 px-0'>\r\n" + 
    			"										<div class='inputContainer BigTextInput cc_element mx-2 mx-lg-3 my-2'>\r\n" + 
    			"											<p class='mb-0'>"+dict.getWord(Entry.PRODUCTION_INFO)+"</p>" +  //Gyártási információ
    			"											<textarea>"+taskDetails.getOperationReservationText()+"</textarea>\r\n" + 
    			"										</div>\r\n" + 
    			"									</div>\r\n" + 
    			"								</div>\r\n" + 
    			"							</div>"; 
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
	
	protected String getBom(List<BomElement> li)
	{
    	String view = "				<table class=\"table table-striped mytable\">\r\n" + 
    			"  								<thead>\r\n" + 
    			"  								  <tr>\r\n" + 
    			"     								 <th scope=\"col\">"+dict.getWord(Entry.ARTICLE)+"</th>\r\n" + //Cikkszám
    			"     								 <th scope=\"col\">"+dict.getWord(Entry.SEARCH_WORD)+"</th>\r\n" +  //Kereszöszó
    			"   									 <th scope=\"col\">"+dict.getWord(Entry.NAME)+" 1</th>\r\n" +  //Megnevezés 1
    			"  									 <th scope=\"col\">"+dict.getWord(Entry.NAME)+" 2</th>\r\n" +   //Megnevezés 2
    			"  									 <th scope=\"col\">"+dict.getWord(Entry.PLUG_IN_QUANTITY)+"</th>\r\n" +  //Beépülö menny.
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
	
	protected String getDescription(Task.Details data)
	{
		String view = "<div class='tab4_element inputContainer BigTextInput cc_element h-50'>\r\n" + 
    			"	<p>"+dict.getWord(Entry.INFO_ARTICLE)+" 1</p><textarea></textarea></div>\r\n" + 
    			"<div class='tab4_element inputContainer BigTextInput cc_element h-50'>\r\n" + 
    			"	<p>"+dict.getWord(Entry.INFO_ARTICLE)+" 2</p><textarea></textarea></div>\r\n" + 
    			"	";
		return view;
	}
}
