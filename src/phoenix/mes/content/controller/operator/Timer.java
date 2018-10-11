package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.math.BigDecimal;

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
import phoenix.mes.content.Dictionary;

/**
 * Servlet implementation class Timer
 */
public class Timer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username=(String)session.getAttribute("username");
		String pass=(String)session.getAttribute("pass");
		Dictionary dict = (Dictionary)session.getAttribute("Dictionary");
		Task task;
		BigDecimal inSecondTime = new BigDecimal(0);
		
		AbasConnection<EDPSession> abasConnection = null;
		
		if(session.getAttribute("Task") != null)
		{
			task = (Task)session.getAttribute("Task");
			
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, dict.getLanguage(), true);
			} catch (LoginException e) {
				
			}
			if(abasConnection != null)
			{
				Task.Details taskDetails = task.getDetails(abasConnection);
				
				BigDecimal rawTime = taskDetails.getCalculatedProductionTime();
				
				inSecondTime = rawTime.multiply(new BigDecimal(3600));
			}
		}
		

		
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(inSecondTime.toPlainString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public static String formatSeconds(int timeInSeconds)
	{
	    int hours = timeInSeconds / 3600;
	    int secondsLeft = timeInSeconds - hours * 3600;
	    int minutes = secondsLeft / 60;
	    int seconds = secondsLeft - minutes * 60;

	    String formattedTime = "";
	    if (hours < 10)
	        formattedTime += "";
	    formattedTime += hours + ":";

	    if (minutes < 10)
	        formattedTime += "0";
	    formattedTime += minutes + ":";

	    if (seconds < 10)
	        formattedTime += "0";
	    formattedTime += seconds ;

	    return formattedTime;
	}
}
