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
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.OutputFormatter.DictionaryEntry;

public class Enter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		AppBuild ab = new AppBuild(request);
		ab.setSessionListener();
		Workstation ws;
		String username = request.getParameter("username");
		String paramStation = request.getParameter("workstation");
		String pass = request.getParameter("password");
		String layout = (String)session.getAttribute("Layout");
		System.out.println(paramStation);
		try {
			User user = new User(request,username,pass);
			new AbasAuthentication().bind(username, pass, request);
			if(!user.hasAccess())
			{
				throw new LoginException();
			}
			String nextPage = null;
			if("operator".equals(layout)) {
				ws = new Workstation(request,ab.isOperator(),paramStation);
				String workStation = ws.getOperatingStation();
				if (null == workStation || !workStation.equals(paramStation)) {
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals("workstation")) {
								
								workStation = OutputFormatter.isStation(cookie.getValue());
								if (null == workStation) {
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
			else if("manager".equals(layout) && user.isModifier())
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