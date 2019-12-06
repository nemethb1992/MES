package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class DataSheetLoader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String taskId = request.getParameter("taskId");	
		String page = null;
		String view = "";
		String state = "";
		String errorText = "";
		User user = null;
		try {
			HttpSession session = request.getSession();

			
			boolean isTest = new AppBuild(request).isTest();
			

			user = new User(request);
			OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
			Task.Details taskDetails = null;
			
			Id abasId = null;

			
			AbasConnection abasConnection = null;
			Task task = null;
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), isTest);
				if(taskId != null && taskId != "") {
					abasId = IdImpl.valueOf(taskId);
					task = AbasObjectFactory.INSTANCE.createTask(abasId,abasConnection);
				}else {
					task = (Task)session.getAttribute("Task");
				}
				taskDetails =  task.getDetails(abasConnection);
				taskId = task.getWorkSlipId().toString();
				if((Status.INTERRUPTED).equals(taskDetails.getStatus()))
				{
					state = "interrupted";
					PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
					List<Map<String, String>> errorStr = pg.sqlQuery("select text from log where workslipno='" + taskDetails.getWorkSlipNo() + "' ORDER BY id DESC LIMIT 1", "text");
					errorText = errorStr.get(0).get("text");
				}
			}catch(LoginException e)
			{			
				try {
					new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_DATA_LOAD, e.toString(),taskId);
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
			
			if(task == null)
			{
				doGet(request,response);
				return;
			}
			request.setAttribute("Task", task);	
			request.setAttribute("User", user);	
			request.setAttribute("isTest", isTest);	
			request.setAttribute("taskId", taskId);	
			request.setAttribute("OutputFormatter", of);
			

			
			String tab = request.getParameter("tabNo") == null ? "1" : request.getParameter("tabNo");
			switch (tab) {
			case "1":
				page = "/Views/Operator/DataSheet/Partial/Sheet.jsp";
				break;
			case "2":
				page = "/Views/Operator/DataSheet/Partial/Documents.jsp";
				break;
			case "3":
				page = "/Views/Operator/DataSheet/Partial/BomList.jsp";
				break;
			case "4":
				page = "/Views/Operator/DataSheet/Partial/ItemText.jsp";
				break;
			case "5":
				page = "/Views/Operator/DataSheet/Partial/RelatedOperations.jsp";
				break;
			case "6":
				page = "/Views/Operator/DataSheet/Partial/FollowingTasks.jsp";
				break;
			case "7":
				page = "/Views/Operator/DataSheet/Partial/TechnicalManual.jsp";
				break;
			case "8":
				page = "/Views/Operator/DataSheet/Partial/Operation.jsp";
				break;
			default:
				break;
			}
			view = (null != page ? RenderView.render(page, request, response) : "");

		}catch(SQLException e)
		{			
			try {
				new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_DATA_LOAD, e.toString(),taskId);
			}catch(SQLException exc) {
			}
		}
		String[] returnObject = new String[4];
		returnObject[0] = view;
		returnObject[1] = state;
		returnObject[2] = taskId;
		returnObject[3] = errorText;
		String json = new Gson().toJson(returnObject);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
