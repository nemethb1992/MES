package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.Dictionary;
import phoenix.mes.content.Dictionary.Entry;


public class DataSheet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected Dictionary dict;
	
    private ArrayList<String> DataSheet_Layout(String username, String pass, String workstation)
    {
		String[] stationSplit = workstation.split("!");
    	ArrayList<String> layouts = new ArrayList<String>();
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
			int stationNo = Integer.parseInt(stationSplit[1]);
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dict.getLanguage(), true);
//        	Task nextTask = AbasObjectFactory.INSTANCE.createWorkStation(stationSplit[0],stationNo, abasConnection).getNextExecutableTask(abasConnection);
        	Task nextTask = AbasObjectFactory.INSTANCE.createTask(new IdImpl("(7999422,9,0)"), abasConnection);
        	final Task.Details taskDetails = nextTask.getDetails(abasConnection);
        	
        	// Tab 1
        	layouts.add("						<div class='container'>\r\n" + 
        			"								<div class='row px-0'>\r\n" + 
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
        			"							</div>");       	

        	// Tab 2
        	layouts.add(""
        			+ "<button type='button' class='list-group-item list-group-item-action active disabled'>"+dict.getWord(Entry.DOCUMENTS)+"</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 2</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 3</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 4</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 5</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 6</button>"
        			+ "");
        	// Tab 3
        	String darabjegyzek = "				<table class=\"table table-striped\">\r\n" + 
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
        	darabjegyzek +=""+
        			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Example-1</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Example-2</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Example-3</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Example-4</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Example-5</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
        			"";
        	darabjegyzek += "</tbody></table>";
        	layouts.add(darabjegyzek);
        	// Tab 4
        	layouts.add("<div class='tab4_element inputContainer BigTextInput cc_element h-50'>\r\n" + 
        			"	<p>"+dict.getWord(Entry.INFO_ARTICLE)+" 1</p><textarea></textarea></div>\r\n" + 
        			"<div class='tab4_element inputContainer BigTextInput cc_element h-50'>\r\n" + 
        			"	<p>"+dict.getWord(Entry.INFO_ARTICLE)+" 2</p><textarea></textarea></div>\r\n" + 
        			"	");
      		
    	}catch(LoginException e)
    	{
//    		System.out.println(e);
			//TODO Jelezni a felhasználó felé.
    	}finally
    	{
    		abasConnection.close();
    	}

    	return layouts;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	    HttpSession session = request.getSession();
 	    String username=(String)session.getAttribute("username");
 	    String pass=(String)session.getAttribute("pass");
// 	    String workstation=(String)session.getAttribute("workstation");
 	    String workstation="234PG!1";
		dict  = (Dictionary)session.getAttribute("Dictionary");
		
	    String json = new Gson().toJson(DataSheet_Layout(username, pass, workstation));
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}


}
