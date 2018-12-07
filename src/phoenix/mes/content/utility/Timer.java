package phoenix.mes.content.utility;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.controller.User;

/**
 * Servlet implementation class Timer
 */
public class Timer extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/*
	 *
	 * Másodpercben visszaadja az aktuális gyártási idő mértéként.
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

		HttpSession session = request.getSession();
		BigDecimal inSecondTime = BigDecimal.ZERO;
		Task task = (Task)session.getAttribute("Task");
		if(null != task)
		{
			AbasConnection<EDPSession> abasConnection = null;
			try {
				User user = new User(request);
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), true);
				Task.Details taskDetails = task.getDetails(abasConnection);
				taskDetails.clearCache();
				BigDecimal rawTime = taskDetails.getCalculatedProductionTime();

				inSecondTime = rawTime.multiply(OutputFormatter.BIG_DECIMAL_3600);
			} catch (Throwable t) {

			} finally {
				if (null != abasConnection) {
					try {
						abasConnection.close();
					} catch (Throwable t2) {
					}
				}
			}
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(inSecondTime.toPlainString());
	}


}
