package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.abas.Task;

/**
 * Servlet implementation class Timer
 */
public class Timer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Task.Details task = (Task.Details)session.getAttribute("Task");
		
		BigDecimal rawTime = task.getCalculatedProductionTime();
		
		BigDecimal inSecondTime = rawTime.multiply(new BigDecimal(3600));
		
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(inSecondTime.toPlainString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
