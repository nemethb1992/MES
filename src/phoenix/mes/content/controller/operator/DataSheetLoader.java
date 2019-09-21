package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.GenericTask.Status;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class DataSheetLoader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		OperatingWorkstation ws = new OperatingWorkstation(request);
		
		String taskId = request.getParameter("taskId");

		if(ws.getGroup().equals(null))
		{
			return;
		}
		String partialUrl = null;
		String view = "";
		String state = "";
		AbasConnection abasConnection = null;
		try {
			User user = new User(request);
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(),  new AppBuild(request).isTest());
			Task task = null;
			if(taskId != null && taskId != "") {
				Id AbasId = IdImpl.valueOf(taskId);
				task = AbasObjectFactory.INSTANCE.createTask(AbasId,abasConnection);
			}else {
				Id AbasId = IdImpl.valueOf((String)session.getAttribute("TaskId"));
				task = AbasObjectFactory.INSTANCE.createTask(AbasId,abasConnection);
			}
			if(task == null)
			{
				doGet(request,response);
				return;
			}
			Task.Details taskDetails = task.getDetails(abasConnection);		
			request.setAttribute("taskDetails", taskDetails);	
			String tab = request.getParameter("tabNo") == null ? "1" : request.getParameter("tabNo");
			switch (tab) {
			case "1":
				request.setAttribute("Workstation", ws);
				partialUrl = "/Views/Operator/DataSheet/Partial/Sheet.jsp";
				break;
			case "2":
				partialUrl = "/Views/Operator/DataSheet/Partial/Documents.jsp";
				break;
			case "3":
				request.setAttribute("bomdata", taskDetails.getBom());
				partialUrl = "/Views/Operator/DataSheet/Partial/BomList.jsp";
				break;
			case "4":
				partialUrl = "/Views/Operator/DataSheet/Partial/ItemText.jsp";
				break;
			case "5":
				request.setAttribute("operationdata", taskDetails.getFollowingOperations());
				partialUrl = "/Views/Operator/DataSheet/Partial/RelatedOperations.jsp";
				break;
			case "6":

				List<Task> taskList = (List<Task>)AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getScheduledTasks(abasConnection);
				request.setAttribute("StationList", taskList);
				request.setAttribute("abasConnection", abasConnection);
				partialUrl = "/Views/Operator/DataSheet/Partial/FollowingTasks.jsp";
				break;
			case "7":
				partialUrl = "/Views/Operator/DataSheet/Partial/TechnicalManual.jsp";
				break;
			case "8":
				partialUrl = "/Views/Operator/DataSheet/Partial/Operation.jsp";
				break;
			default:
				break;
			}
			view = (null != partialUrl ? RenderView.render(partialUrl, request, response) : "");
			if((Status.INTERRUPTED).equals(taskDetails.getStatus()))
			{
				state = "interrupted";
				request.setAttribute("error-text", "Hiba!");
			}
		}catch(LoginException | SQLException e)
		{			
			try {
				String workstation = "";
				if(ws != null) {
					workstation = ws.group + " - " + ws.no;
				}
				new Log(request).logFaliure(FaliureType.TASK_DATA_LOAD, e.getMessage(),workstation);
			}catch(SQLException exc) {
			}
		}finally
		{
			try {
				if (null != abasConnection) {
					abasConnection.close();
				}
			} catch (Throwable t) {
			}
		}
		String[] returnObject = new String[2];
		returnObject[0] = view;
		returnObject[1] = state;
		String json = new Gson().toJson(returnObject);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
