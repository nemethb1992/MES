package phoenix.mes.content.controller.operator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

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
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;


public class DataSheetLoader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// TODO split
		String workstation = (String)session.getAttribute("operatorWorkstation");
		// TODO
		OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
//		workstation="234PG!1";
		String view = "";
		
		boolean testSystem = new AppBuild(request).isTest();
		        	
		if(workstation != null)
		{
			String wsName = "";
			try {
				wsName = getStationName(workstation, request);
			} catch (SQLException e) {
				wsName = " - ";
			}
			
			String username = (String)session.getAttribute("username");
			String pass = (String)session.getAttribute("pass");
			AbasConnection<EDPSession> abasConnection = null;
			
			try {
				abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, pass, of.getLocale(), testSystem);
				Task task = (Task)session.getAttribute("Task");

				if(task == null)
				{
					doGet(request,response);
					return;
				}
				Task.Details taskDetails = task.getDetails(abasConnection);		
				request.setAttribute("taskDetails", taskDetails);	
				String partialUrl = null;
				switch (request.getParameter("tabNo")) {
				case "1":
					request.setAttribute("ws", workstation);
					request.setAttribute("wsName", wsName);
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
				default:
					break;
				}
				view = (null != partialUrl ? RenderView.render(partialUrl, request, response) : "");
			}catch(LoginException e)
			{
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
		}
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(view); 
	}
	public String getStationName(String workstation, HttpServletRequest request) throws SQLException
	{
		PostgreSql postgreSql = new PostgreSql(new AppBuild(request).isTest()); 
		Locale language = ((OutputFormatter)request.getSession().getAttribute("OutputFormatter")).getLocale();
		String[] stationSplit = workstation.split("!");
		String command, field;
		
		switch (language.getLanguage()) {
		case "en":
			command = "SELECT nev_en FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_en";
			break;
		case "de":
			command = "SELECT nev_de FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_de";
			break;
		case "hu":
		default:
			command = "SELECT nev_hu FROM stations WHERE csoport = '"+stationSplit[0]+"' AND sorszam = "+stationSplit[1];
			field = "nev_hu";
			break;
		}
		
		return postgreSql.sqlSingleQuery(command, field);
	}
}
