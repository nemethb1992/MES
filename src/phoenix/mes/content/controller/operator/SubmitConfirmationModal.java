package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

public class SubmitConfirmationModal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String encodedURL = response.encodeRedirectURL("/Logout");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String taskID = (String) request.getParameter("TaskID");
		String finishedQty = (String) request.getParameter("finishedQty");
		String scrapQty = (String) request.getParameter("scrapQty");
		OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
		Task.Details taskDetails = null;
		Task task = null;
		try {
			User user = new User(request);
			AbasConnection abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(),of.getLocale(), new AppBuild(request).isTest());
			Id AbasId = IdImpl.valueOf(taskID);
			task = AbasObjectFactory.INSTANCE.createTask(AbasId,abasConnection);
			taskDetails = task.getDetails(abasConnection);
		} catch (LoginException | SQLException e) {
			
		}
		request.setAttribute("TaskDetails", taskDetails);
		request.setAttribute("OutputFormatter", of);
		request.setAttribute("finishedQty", finishedQty);
		request.setAttribute("scrapQty", scrapQty);
		request.setAttribute("workSlipId", task.getWorkSlipId().toString());
		String encodedURL = response.encodeRedirectURL("/Views/Operator/DataSheet/Partial/SubmitConfirmationModal.jsp");
		request.getRequestDispatcher(encodedURL).forward(request, response);
	}

}

