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
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;


public class Submit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected String quantity;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if(!ab.isStable()){
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("finished");
			return;
		}

		HttpSession session = request.getSession();
		AbasConnection<EDPSession> abasConnection = null;
		String responseStr = "null";
		BigDecimal finishedQty = new BigDecimal(request.getParameter("finishedQty"));
		BigDecimal scrapQty = new BigDecimal(request.getParameter("scrapQty"));
		
		try {
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), ab.isTest());
			Task task = (Task)session.getAttribute("Task");
			if(task != null)
			{
				Task.Details taskDetails = task.getDetails(abasConnection);
				task.postCompletionConfirmation(finishedQty, scrapQty, abasConnection);
				if(finishedQty.intValue() >= taskDetails.getOutstandingQuantity().intValue()){
					session.removeAttribute("Task");
					responseStr = "finished";
				}else {
					taskDetails.clearCache();
					session.setAttribute("Task", task);
				}

			}
		}catch(LoginException e)
		{
			session.removeAttribute("Task");
			responseStr = "finished";
			System.out.println(e);
		}finally
		{
			try {
				if (null != abasConnection) {
					abasConnection.close();
				}
			} catch (Throwable t) {
			}
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(responseStr); 
	}

}
