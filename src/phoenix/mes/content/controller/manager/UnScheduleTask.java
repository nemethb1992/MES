package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;

/**
 * Servlet implementation class UnScheduleTask
 */
public class UnScheduleTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Logout").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if (!ab.isStabile()) {
			doGet(request, response);
			return;
		}

		HttpSession session = request.getSession();
		String workSlipId = request.getParameter("workSlipId");
		OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
		AbasConnection abasConnection = null;

		try {
			User user = new User(request);
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(),
					of.getLocale(), ab.isTest());
			Task task = AbasObjectFactory.INSTANCE.createTask(IdImpl.valueOf(workSlipId), abasConnection);
			task.unschedule(abasConnection);
		} catch (LoginException | SQLException e) {
			try {
				new Log(request).logFaliure(FaliureType.TASK_LIST_NAVIGATION, e.getMessage());
			} catch (SQLException exc) {
			}
		} catch (AbasFunctionException e) {
			int errorCode = e.getErrorCode();
			if (errorCode != 6 && errorCode != 7 && errorCode != 8) {
				try {
					OperatingWorkstation ws = new OperatingWorkstation(request);
					String workstation = "";
					if (ws != null) {
						workstation = ws.group + " - " + ws.no;
					}
					new Log(request).logFaliure(FaliureType.TASK_SUBMIT, Log.getErrorText(errorCode), workstation);
				} catch (SQLException exc) {
				}
			}
		} finally {
			try {
				abasConnection.close();
			} catch (Exception e) {
			}
		}
	}

}
