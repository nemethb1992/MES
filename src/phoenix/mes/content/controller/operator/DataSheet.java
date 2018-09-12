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
import de.abas.erp.common.type.enums.EnumLanguageCode;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.LanguageSource;


public class DataSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static EnumLanguageCode language;
	String layout,station,username,pass,ws_group;
	int ws_no;
	
	static String word(int index)
	{
		return LanguageSource.getWord(language, index);
	}
    private ArrayList<String> DataSheet_Layout()
    {
    	ArrayList<String> layouts = new ArrayList<String>();
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, language, true);
        	Task nextTask = AbasObjectFactory.INSTANCE.createWorkStation(ws_group,ws_no, abasConnection).getNextExecutableTask(abasConnection);
//        	Task nextTask = AbasObjectFactory.INSTANCE.createTask(new IdImpl("(7984316,9,0)"), abasConnection);
        	final Task.Details taskDetails = nextTask.getDetails(abasConnection);
        	
        	// Tab 1
        	layouts.add("						<div class='container'>\r\n" + 
        			"								<div class='row px-0'>\r\n" + 
        			"									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(4)+"</p>\r\n" +  //Munkaállomás
        			"											<input class='px-2 w-100' type='text' value='"+ws_group+" - "+ws_no+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(5)+"</p>\r\n" +  //Munkalapszám
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getWorkSlipNo()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(6)+"</p>\r\n" +  //Cikkszám
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductIdNo()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(7)+"</p>\r\n" +  //Keresőszó
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductSwd()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(22)+"</p>\r\n" +  //Műveleti azonosító
        			"											<input class='px-2 w-100'  type='text' value='"+taskDetails.getOperationIdNo()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(26)+"</p>\r\n" +  //Beállítási idő
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getSetupTime()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"									</div>\r\n" + 
        			"									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(8)+"</p>\r\n" +  //Megnevezés
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationDescription()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(9)+"</p>\r\n" +  //Felhasználás
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getUsage()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(7)+"</p>\r\n" +  //Keresőszó
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationSwd()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(8)+"</p>\r\n" +  //Megnevezés
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationDescription()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(27)+"</p>\r\n" +  //Darabidő
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getUnitTime()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+word(28)+"</p>\r\n" +  //Nyitott mennyiség
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOutstandingQuantity()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"									</div>\r\n" + 
        			"									<div class='col-12 px-0'>\r\n" + 
        			"										<div class='inputContainer BigTextInput cc_element mx-2 mx-lg-3 my-2'>\r\n" + 
        			"											<p class='mb-0'>"+word(29)+"</p>" +  //Gyártási információ
        			"											<textarea>"+taskDetails.getOperationReservationText()+"</textarea>\r\n" + 
        			"										</div>\r\n" + 
        			"									</div>\r\n" + 
        			"								</div>\r\n" + 
        			"							</div>");       	

        	// Tab 2
        	layouts.add(""
        			+ "<button type='button' class='list-group-item list-group-item-action active disabled'>"+word(23)+"</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 2</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 3</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 4</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 5</button>"
        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 6</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 7</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 5</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 6</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 7</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 5</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 6</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 7</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 5</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 6</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 7</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 5</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 6</button>"
//        			+ "<button type='button' class='list-group-item list-group-item-action'>Dokumentum 7</button>"
        			+ "");
        	// Tab 3
        	String darabjegyzek = "				<table class=\"table table-striped\">\r\n" + 
        			"  								<thead>\r\n" + 
        			"  								  <tr>\r\n" + 
        			"     								 <th scope=\"col\">"+word(6)+"</th>\r\n" + //Cikkszám
        			"     								 <th scope=\"col\">"+word(7)+"</th>\r\n" +  //Kereszöszó
        			"   									 <th scope=\"col\">"+word(8)+" 1</th>\r\n" +  //Megnevezés 1
        			"  									 <th scope=\"col\">"+word(8)+" 2</th>\r\n" +   //Megnevezés 2
        			"  									 <th scope=\"col\">"+word(31)+"</th>\r\n" +  //Beépülö menny.
        			"   								  </tr>\r\n" + 
        			"  								</thead>\r\n" + 
        			"  								<tbody class='darabjegyz-tbody'>";
        	darabjegyzek +=""+
        			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Proba_1</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Proba_1</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Proba_1</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Proba_1</th>\r\n" + 
        			"      <td>Proba_2</td>\r\n" + 
        			"      <td>Proba_3</td>\r\n" + 
        			"      <td>Proba_4</td>\r\n" + 
        			"      <td>Proba_5</td>\r\n" + 
        			"    </tr>" +
           			"	<tr>\r\n" + 
        			"      <th scope=\"row\">Proba_1</th>\r\n" + 
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
        			"	<p>"+word(42)+" 1</p><textarea></textarea></div>\r\n" + 
        			"<div class='tab4_element inputContainer BigTextInput cc_element h-50'>\r\n" + 
        			"	<p>"+word(42)+" 2</p><textarea></textarea></div>\r\n" + 
        			"	");
      		
    	}catch(LoginException e)
    	{
    		System.out.println(e);
    	}finally
    	{
        		abasConnection.close();
    	}

    	return layouts;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	    HttpSession session = request.getSession();
 	    username=(String)session.getAttribute("username");
 	    pass=(String)session.getAttribute("pass");
 	    ws_group=(String)session.getAttribute("ws_group");
 	    ws_no=(int)session.getAttribute("ws_no");
		language = (EnumLanguageCode)session.getAttribute("abasLanguageType");
		
	    String json = new Gson().toJson(DataSheet_Layout());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}


}
