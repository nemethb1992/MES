package phoenix.mes.content.controller;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.AccessControl;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.OutputFormatter.DictionaryEntry;

public class Enter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		AppBuild app = new AppBuild(null);
		app.setSessionListener(request);
		
		String username = request.getParameter("username");
		String paramStation = request.getParameter("workstation");
		String pass = request.getParameter("password");
		String layout = (String)session.getAttribute("Layout");

		session.setAttribute("username",username);
		session.setAttribute("pass",pass);
		
		try {
			new Authentication().bind(username, pass, request);
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
					nextPage = "/OpenTask";
				}
			}
			else if("manager".equals(layout) && new AccessControl(request, username).isModifier())
			{
				nextPage = "/Views/Manager/Main/Main.jsp";
			}
			getServletContext().getRequestDispatcher(null == nextPage ? "/Views/WelcomePage/WelcomePage.jsp" : nextPage).forward(request, response);
		} catch ( NamingException | LoginException | SQLException t) {
			System.out.println(t);
			request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.LOGIN_FAILED));
			getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
		}
	}

}
//catch (Throwable t) {
//	request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.LOGIN_FAILED));
//	getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
//}