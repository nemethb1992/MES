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
		
		if(!(new AppBuild(null)).isStable(request)){
			response.setContentType("text/plain"); 
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write("finished");
			return;
		}
		BigDecimal finishedQty = new BigDecimal(request.getParameter("finishedQty"));
		BigDecimal scrapQty = new BigDecimal(request.getParameter("scrapQty"));

		HttpSession session = request.getSession();

		String responseStr = "null";
		
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

		String username=(String)session.getAttribute("username");
		String pass=(String)session.getAttribute("pass");

		AbasConnection<EDPSession> abasConnection = null;
		
		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), new AppBuild(request).isTest());
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
