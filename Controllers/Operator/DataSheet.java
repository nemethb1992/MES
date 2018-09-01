package Operator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.google.gson.Gson;


import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Database.dbEntities;
import Language.langsrc;
import Session.Session_Datas;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumLanguageCode;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.util.ContextHelper;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;


public class DataSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Session_Datas sess = new Session_Datas();
	String station;
	String lng;
	String ws;
	langsrc l = new langsrc();
	EnumLanguageCode language;

    public DataSheet() {
        super();
    }
	String Word(int index)
	{
		return l.LanguageSelector(lng, index);
	}
    	// TODO Azonosítani az aktuális gépet.
    private ArrayList<String> DataSheet_Layout(String cookieLang, String ws)
    {
    	System.out.println(ws);
    
    	ArrayList<String> layouts = new ArrayList<String>();
    	int stationNo;
    	AbasConnection<EDPSession> abasConnection = null;
//    	System.out.println(l.LanguageSelector(lng, 10));
//    	System.out.println(Word(10));

    	
    	try {
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(Session_Datas.getUsername(), Session_Datas.getPassword(), langsrc.getAbasLanguage(), true);
        	System.out.println(sess.getWS_Group() + sess.getWS_No());
 //       	Task nextTask = AbasObjectFactory.INSTANCE.createWorkStation("234PG",1, abasConnection).getNextExecutableTask(abasConnection);
 //       	Task nextTask = AbasObjectFactory.INSTANCE.createWorkStation(sess.getWS_Group(),sess.getWS_No(), abasConnection).getNextExecutableTask(abasConnection);
       	Task nextTask = AbasObjectFactory.INSTANCE.createTask(new IdImpl("(7984316,9,0)"), abasConnection);
        	final Task.Details taskDetails = nextTask.getDetails(abasConnection);
      		
        	// Tab 1
        	layouts.add("						<div class='container'>\r\n" + 
        			"								<div class='row px-0'>\r\n" + 
        			"									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(4)+"</p>\r\n" +  //Munkaállomás
        			"											<input class='px-2 w-100' type='text' value='"+Session_Datas.getWS_Group() +" - "+Session_Datas.getWS_No()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(5)+"</p>\r\n" +  //Munkalapszám
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getWorkSlipNo()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(6)+"/p>\r\n" +  //Cikkszám
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductIdNo()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(7)+"</p>\r\n" +  //Keresőszó
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getProductSwd()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(22)+"</p>\r\n" +  //Műveleti azonosító
        			"											<input class='px-2 w-100'  type='text' value='"+taskDetails.getOperationIdNo()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(26)+"</p>\r\n" +  //Beállítási idő
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getSetupTime()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"									</div>\r\n" + 
        			"									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(8)+"</p>\r\n" +  //Megnevezés
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationDescription()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(9)+"</p>\r\n" +  //Felhasználás
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getUsage()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(7)+"</p>\r\n" +  //Keresőszó
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationSwd()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(8)+"</p>\r\n" +  //Megnevezés
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOperationDescription()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(27)+"</p>\r\n" +  //Darabidő
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getUnitTime()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"										<div class='inputContainer cc_element mt-2 mx-2 mx-lg-3'>\r\n" + 
        			"											<p>"+Word(28)+"</p>\r\n" +  //Nyitott mennyiség
        			"											<input class='px-2 w-100' type='text' value='"+taskDetails.getOutstandingQuantity()+"'/>\r\n" + 
        			"										</div>\r\n" + 
        			"									</div>\r\n" + 
        			"									<div class='col-12 px-0'>\r\n" + 
        			"										<div class='inputContainer BigTextInput cc_element mx-2 mx-lg-3 my-2'>\r\n" + 
        			"											<p class='mb-0'>"+Word(29)+"</p>" +  //Gyártási információ
        			"											<textarea>"+taskDetails.getOperationReservationText()+"</textarea>\r\n" + 
        			"										</div>\r\n" + 
        			"									</div>\r\n" + 
        			"								</div>\r\n" + 
        			"							</div>");       	

        	// Tab 2
        	layouts.add(""
        			+ "<button type='button' class='list-group-item list-group-item-action active disabled'>"+Word(23)+"</button>"
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
        			"     								 <th scope=\"col\">Cikkszám</th>\r\n" + 
        			"     								 <th scope=\"col\">Kereszöszó</th>\r\n" + 
        			"   									 <th scope=\"col\">Megnevezés 1</th>\r\n" + 
        			"  									 <th scope=\"col\">Megnevezés 2</th>\r\n" + 
        			"  									 <th scope=\"col\">Beépülö menny.</th>\r\n" + 
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
        			"	<p>Szöveg 1</p><textarea></textarea></div>\r\n" + 
        			"<div class='tab4_element inputContainer BigTextInput cc_element h-50'>\r\n" + 
        			"	<p>Szöveg 2</p><textarea></textarea></div>\r\n" + 
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
//		String data = "nem";
//   		final ConnectionProperties connectionProperties = new ConnectionProperties("abasdb.pmhu.local", ConnectionProperties.DEFAULT_EDP_PORT, "/mes".equals("/dmes") ? "pmk" : "dpmk", "MES");
//   		DbContext abasSession;
//		try {
//			abasSession = ObjectFactory.startAbasSession(Session_Datas.getUsername(), Session_Datas.getPassword(), EnumLanguageCode.Hungarian, true);
//	        final String testText = abasSession.load(Product.class, IdImpl.valueOf("(1620705,2,0)")).getDescr();
//	        data = testText;
//		} catch (EDPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//dbEntities dbE = new dbEntities();
// ArrayList li = dbE.SQLQueryRead();
// for(int i = 0; i < li.size(); i++)
// {
// 	System.out.println(li.get(i));
		
//        for (Cookie c : request.getCookies()) {
//            if (c.getName().equals("Language"))
//            	lng = c.getValue();
//            }
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
         for (Cookie cookie : cookies) {
           if (cookie.getName().equals("language")) {
        	   lng = cookie.getValue();
            }
          }
        }
//        System.out.print(lng + " - ");
	    String json = new Gson().toJson(DataSheet_Layout(lng, ws));
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}


}
