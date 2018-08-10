package Operator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Database.dbEntities;
import Session.Session_Datas;
import de.abas.ceks.jedp.EDPException;
import de.abas.erp.common.ConnectionProperties;
import de.abas.erp.common.DefaultCredentialsProvider;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumLanguageCode;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.util.ContextHelper;
import phoenix.mes.abas.ObjectFactory;
import phoenix.mes.abas.Task;


public class DataSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Session_Datas sess = new Session_Datas();
	String station;
    public DataSheet() {
        super();
    }
    
    	// TODO Azonosítani az aktuális gépet.
    private ArrayList<String> DataSheet_Layout()
    {

    	ArrayList<String> layouts = new ArrayList<String>();
           
    	int stationNo;
    	DbContext abasSession = null;
    	try {
        	abasSession = ObjectFactory.startAbasSession(Session_Datas.getUsername(), Session_Datas.getPassword(), true);
        	System.out.println("check 1");
        	Task nextTask = ObjectFactory.createWorkStation("234PG",1, abasSession).getNextExecutableTask(abasSession);
        	System.out.println("check 2");
        	final Task.Details taskDetails = nextTask.getDetails(abasSession);
        	System.out.println("check 3");
      		
        	// Tab 1
        	layouts.add("<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Munkaállomás</p><input type='text' value=''/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Munkalapszám</p><input type='text' value='"+taskDetails.getWorkSlipNo()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Cikkszám</p><input type='text' value='"+taskDetails.getProductIdNo()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Keresőszó</p><input type='text' value='"+taskDetails.getProductSwd()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Megnevezés</p><input type='text' value='"+taskDetails.getProductDescription()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Felhasználás</p><input type='text' value='"+taskDetails.getUsage()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Művleti azonosító</p><input type='text' value='"+taskDetails.getOperationIdNo()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Keresőszó</p><input type='text' value='"+taskDetails.getOperationSwd()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Megnevezés</p><input type='text' value='"+taskDetails.getOperationDescription()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Beállítási idő</p><input type='text' value='"+taskDetails.getSetupTime()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Darabidő</p><input type='text' value='"+taskDetails.getUnitTime()+"'/></div>\r\n" + 
        						"<div class='inputContainer cc_element'>\r\n" + 
        						"	<p>Nyitott mennyiség</p><input type='text' value='"+taskDetails.getOutstandingQuantity()+"'/></div>\r\n" + 
        						"<div class='inputContainer BigTextInput cc_element'>\r\n" + 
        						"	<p>Gyártási információ</p><textarea>"+taskDetails.getOperationReservationText()+"</textarea></div>");

        	System.out.println("check 4");
        	// Tab 2
        	layouts.add("<div id='documentContainer' class='inputContainer cc_element'>\r\n" + 
        			"	<p>Dokumentumok:</p>\r\n" + 
        			"	<div id='documentFrame'>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"		<div class='documentBlock cc_element'></div>\r\n" + 
        			"	</div>\r\n" + 
        			"</div>");
        	// Tab 3
        	layouts.add("<table class=\"table cc_element\" id=\"tab3_table\">\r\n" + 
        			"		<tr>\r\n" + 
        			"			<th id=\"th_1\">Cikkszám</th>\r\n" + 
        			"			<th id=\"th_2\">Kereszőszó</th> \r\n" + 
        			"			<th id=\"th_3\">Megnevezés 1</th>\r\n" + 
        			"			<th id=\"th_4\">Megnevezés 2</th>\r\n" + 
        			"			<th id=\"th_5\">Beépülő menny.</th>\r\n" + 
        			"		</tr>\r\n" + 
        			"	<tbody>\r\n" + 
        			"\r\n" + 
        			"		<tr>\r\n" + 
        			"			<td>cell1</td>\r\n" + 
        			"			<td>cell2</td>\r\n" + 
        			"			<td>cell3</td>\r\n" + 
        			"			<td>cell4</td>\r\n" + 
        			"			<td>cell5</td>\r\n" + 
        			"		</tr>\r\n" + 
        			"	</tbody>\r\n" + 
        			"</table>");
        	// Tab 4
        	layouts.add("<div class='tab4_element inputContainer BigTextInput cc_element'>\r\n" + 
        			"	<p>Szöveg 1</p><textarea></textarea></div>\r\n" + 
        			"<div class='tab4_element inputContainer BigTextInput cc_element'>\r\n" + 
        			"	<p>Szöveg 2</p><textarea></textarea></div>\r\n" + 
        			"	");
      		
    	}catch(EDPException e)
    	{
    		System.out.println(e);
    	}finally
    	{
    		try
    		{
        		abasSession.close();

            	System.out.println("check last");
    		}
    		catch(Exception e)
    		{}
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
 
		
	    String json = new Gson().toJson(DataSheet_Layout());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}


}
