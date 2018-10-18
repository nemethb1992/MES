package phoenix.mes.content.controller;
import java.io.IOException;

//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.Dictionary;
import phoenix.mes.content.Dictionary.Entry;

public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username,pass;
		String layout;

		username = request.getParameter("username");
		pass = request.getParameter("password");
		//		workstation = request.getParameter("workstation");
		layout = (String)session.getAttribute("Layout");
		if("operator".equals(layout))
		{
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("workstation")) {
						session.setAttribute("operatorWorkstation", cookie.getValue());
					}
				}
			}
		}


		session.setAttribute("username",username);
		session.setAttribute("pass",pass);
		Dictionary dict  = (Dictionary)session.getAttribute("Dictionary");
		
		if(ActiveDirectoryLogin.activeDirectoryConn(username, pass))
		{
			if("operator".equals(layout) && session.getAttribute("operatorWorkstation") != null) {
				getServletContext().getRequestDispatcher("/Views/TaskView/taskViewPage.jsp").forward(request, response);
			}			
			else if("manager".equals(layout)) {
				getServletContext().getRequestDispatcher("/Views/TaskManage/Main/Main.jsp").forward(request, response);
			}
			else {
//				request.setAttribute("infoTitle", dict.getWord(Entry.LOGIN_FAILED_MISSING_WS));
				getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
			}

		}
		else
		{
			request.setAttribute("infoTitle", dict.getWord(Entry.LOGIN_FAILED));
			getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);

		}
	}       
}
