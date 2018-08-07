package Operator;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class DataSheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataSheet() {
        super();
    }
    
    	// TODO Azonosítani az aktuális gépet.
    private ArrayList<String> DataSheet_Layout()
    {
    	ArrayList<String> li = new ArrayList<String>();
    	li.add("proba");
    	ArrayList<String> layouts = new ArrayList<String>();
    	// Tab 1
    	layouts.add("<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><input type='text' value='"+li.get(0)+"'/></div>\r\n" + 
    						"<div class='inputContainer BigTextInput cc_element'>\r\n" + 
    						"	<p>Cikkszám</p><textarea>"+li.get(0)+"</textarea></div>");
    	
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
    			"			<th id=\"th_2\">Keresöszó</th> \r\n" + 
    			"			<th id=\"th_3\">Megnevezés</th>\r\n" + 
    			"			<th id=\"th_4\">Megnevezés</th>\r\n" + 
    			"			<th id=\"th_5\">Beépítendö menny.</th>\r\n" + 
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
    			"	<p>Szöveg 1</p><textarea>BigTextInputBigTextInputBigTextInputBigTextInputTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInput</textarea></div>\r\n" + 
    			"<div class='tab4_element inputContainer BigTextInput cc_element'>\r\n" + 
    			"	<p>Szöveg 2</p><textarea>BigTextInputBigTextInputBigTextInputBigTextInputTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpTextInputBigTextInputBigTextInputBigTextInpBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInputBigTextInput</textarea></div>\r\n" + 
    			"	");
    	return layouts;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	    String json = new Gson().toJson(DataSheet_Layout());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}


}
