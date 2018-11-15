package phoenix.mes.content.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;

public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String paramStation = request.getParameter("workstation");
		String pass = request.getParameter("password");
		String layout = (String)session.getAttribute("Layout");
		
		session.setAttribute("pass",pass);
		session.setAttribute("username",username);
		
		try {
			ActiveDirectory.login(username, pass, request);
			String nextPage = null;
			if("operator".equals(layout)) {
				String workStation = (String)session.getAttribute("operatorWorkstation");
				if (null == workStation || !workStation.equals(paramStation)) {
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals("workstation")) {
								
								workStation = OutputFormatter.isStation(cookie.getValue());
								if (null != workStation) {
									session.setAttribute("operatorWorkstation", workStation);
								}
								else {
									nextPage = "/Views/Login/loginPage.jsp";
									break;
								}
							}
						}
					}
				}
				if (null != workStation) {
					nextPage = "/Views/TaskView/taskViewPage.jsp";
				}
			}
			else if("manager".equals(layout)) {
				nextPage = "/Views/TaskManage/Main/Main.jsp";
			}
			getServletContext().getRequestDispatcher(null == nextPage ? "/Views/WelcomePage/WelcomePage.jsp" : nextPage).forward(request, response);
		} catch (Throwable t) {
			request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.LOGIN_FAILED));
			getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
		}
	}

}
