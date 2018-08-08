package Manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class BuildUp
 */
public class BuildUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildUp() {
        super();
        // TODO Auto-generated constructor stub
    }

    private ArrayList<String> DataSheet_Layout()
    {
    	String station ="",leftList="",rightList="";
    	ArrayList<String> li1 = new ArrayList<String>();
    	ArrayList<String> li2 = new ArrayList<String>();
    	ArrayList<String> li3 = new ArrayList<String>();
    	li1.add("Station 1");
    	li1.add("Station 2");
    	li1.add("Station 3");
    	li1.add("Station 4");
    	li2.add("Abas1");
    	li2.add("Abas2");
    	li2.add("Abas3");
    	li2.add("Abas4");
    	li3.add("Task1");
    	li3.add("Task2");
    	li3.add("Task3");
    	li3.add("Task4");
    	int itemCount_li1 = li1.size();
    	int itemCount_li2 = li2.size();
    	int itemCount_li3 = li3.size();
    	ArrayList<String> layouts = new ArrayList<String>();
    	for (int i = 0; i < itemCount_li1; i++) {
    		station += "<div class='tmts_stationBtnDivCont'><input disabled class='si1'value='"+li1.get(i)+"'></div>";
    	}
      	for (int i = 0; i < itemCount_li2; i++) {
      		leftList += "			<div class='dnd-container' value='3' onClick='Station_Select(this)'><div class='icon-form dnd-icon pass-item'></div>\r\n" + 
        			"					<div class='dnd-input-container'>\r\n" + 
        			"						<div class='dnd-upper'>\r\n" + 
        			"							<div class='dnd-input-div'>\r\n" + 
        			"								<p>Input neve</p>\r\n" + 
        			"								<input disabled class='dnd-input dnd-in1' value='input value'>\r\n" + 
        			"							</div>\r\n" + 
        			"							<div class='dnd-input-div'>\r\n" + 
        			"								<p>Input neve</p>\r\n" + 
        			"								<input disabled class='dnd-input dnd-in2' value='input value'>\r\n" + 
        			"							</div>\r\n" + 
        			"							<div class='dnd-input-div'>\r\n" + 
        			"								<p>Input neve</p>\r\n" + 
        			"								<input disabled class='dnd-input dnd-in3' value='input value'>\r\n" + 
        			"							</div>\r\n" + 
        			"						</div>\r\n" + 
        			"						<div class='dnd-downer'>\r\n" + 
        			"							<div class='dnd-input-div'>\r\n" + 
        			"								<p>Input neve</p>\r\n" + 
        			"								<input disabled class='dnd-input dnd-in4' value='input value'>\r\n" + 
        			"							</div>\r\n" + 
        			"							<div class='dnd-input-div'>\r\n" + 
        			"								<p>Input neve</p>\r\n" + 
        			"								<input disabled class='dnd-input dnd-in5' value='input value'>\r\n" + 
        			"							</div>\r\n" + 
        			"							<div class='dnd-input-div'>\r\n" + 
        			"								<p>Input neve</p>\r\n" + 
        			"								<input disabled class='dnd-input dnd-in6' value='input value'>\r\n" + 
        			"							</div>\r\n" + 
        			"						</div>\r\n" + 
        			"					</div>\r\n" + 
        			"				</div>";
      	}
      	for (int i = 0; i < itemCount_li3; i++) {
      		rightList += "			<div class='dnd-container'><div class='icon-form dnd-icon pass-item'></div>\r\n" + 
      				"					<div class='dnd-input-container'>\r\n" + 
      				"						<div class='dnd-upper'>\r\n" + 
      				"							<div class='dnd-input-div'>\r\n" + 
      				"								<p>Input neve</p>\r\n" + 
      				"								<input disabled class='dnd-input dnd-in1' value='input value'>\r\n" + 
      				"							</div>\r\n" + 
      				"							<div class='dnd-input-div'>\r\n" + 
      				"								<p>Input neve</p>\r\n" + 
      				"								<input disabled class='dnd-input dnd-in2' value='input value'>\r\n" + 
      				"							</div>\r\n" + 
      				"							<div class='dnd-input-div'>\r\n" + 
      				"								<p>Input neve</p>\r\n" + 
      				"								<input disabled class='dnd-input dnd-in3' value='input value'>\r\n" + 
      				"							</div>\r\n" + 
      				"						</div>\r\n" + 
      				"						<div class='dnd-downer'>\r\n" + 
      				"							<div class='dnd-input-div'>\r\n" + 
      				"								<p>Input neve</p>\r\n" + 
      				"								<input disabled class='dnd-input dnd-in4' value='input value'>\r\n" + 
      				"							</div>\r\n" + 
      				"							<div class='dnd-input-div'>\r\n" + 
      				"								<p>Input neve</p>\r\n" + 
      				"								<input disabled class='dnd-input dnd-in5' value='input value'>\r\n" + 
      				"							</div>\r\n" + 
      				"							<div class='dnd-input-div'>\r\n" + 
      				"								<p>Input neve</p>\r\n" + 
      				"								<input disabled class='dnd-input dnd-in6' value='input value'>\r\n" + 
      				"							</div>\r\n" + 
      				"						</div>\r\n" + 
      				"					</div>\r\n" + 
      				"				</div>";
      	}

    	layouts.add(station);
    	layouts.add(leftList);
    	layouts.add(rightList);

    	return layouts;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String json = new Gson().toJson(DataSheet_Layout());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
}
