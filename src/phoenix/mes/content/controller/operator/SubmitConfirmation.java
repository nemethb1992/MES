package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.content.AppBuild;

/**
 * Servlet implementation class SubmitConfirmation
 */
@WebServlet("/SubmitConfirmation")
public class SubmitConfirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected String quantity;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppBuild build = new AppBuild(request);
		if(!build.isStabile()){
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("finished");
			return;
		}

		HttpSession session = request.getSession();
		AbasConnection<EDPSession> abasConnection = null;
		String responseStr = "null";
		BigDecimal finishedQty = new BigDecimal(request.getParameter("finishedQty"));
	}

}
