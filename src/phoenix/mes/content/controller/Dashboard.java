package phoenix.mes.content.controller;
import java.io.IOException;

//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Dashboard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username,pass,workstation;
		String layout;
		
		username = request.getParameter("username");
		pass = request.getParameter("password");
		workstation = request.getParameter("workstation");
		layout = (String)session.getAttribute("Layout");
		session.setAttribute("username",username);
		session.setAttribute("pass",pass);
		session.setAttribute("operatorWorkstation", workstation);
		
		if(ActiveDirectoryLogin.activeDirectoryConn(username, pass))
		{
			if("operator".equals(layout))
				getServletContext().getRequestDispatcher("/Views/TaskView/taskViewPage.jsp").forward(request, response);
			if("manager".equals(layout))
				getServletContext().getRequestDispatcher("/Views/TaskManage/taskManagePage.jsp").forward(request, response);
		}
		else
		{
			getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
		}
	}       
}
