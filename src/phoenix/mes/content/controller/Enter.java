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
		
		String username = request.getParameter("username").toLowerCase();
		String paramStation = request.getParameter("workstation");
		String shownPassword = request.getParameter("shownPassword");
		String pass = request.getParameter("password");
		String layout = (String)session.getAttribute("Layout");

		try {
			boolean abasAccess = new AbasAuthentication().bind(username, pass, request);
			if(!abasAccess || "".equals(shownPassword))
			{
				throw new LoginException();
			}
			User user = new User(request,username,pass);
			if(!user.hasAccess())
			{
				throw new LoginException();
			}
			String nextPage = null;

			if("operator".equals(layout)) {
				String workstation = "";
				if(!paramStation.equals("null")) {
					OperatingWorkstation.setOperatingStation(request,OutputFormatter.isStation(paramStation));
				}
				OperatingWorkstation ws = new OperatingWorkstation(request);
				workstation = ws.getOperatingStation();
				if ("".equals(workstation) || !workstation.equals(paramStation)) {
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals("workstation")) {
								
								OperatingWorkstation.setOperatingStation(request,OutputFormatter.isStation(cookie.getValue()));

								
								if ("".equals(ws.getOperatingStation())) {
									request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.EMPTY_STATION_ID));
									nextPage = "/Views/Login/loginPage.jsp";
									break;
								}
							}
						}
					}
					if("".equals(ws.getOperatingStation())) {

						request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.EMPTY_STATION_ID));
						nextPage = "/Views/Login/loginPage.jsp";
					}
				}
				workstation = ws.getOperatingStation();
				if (!"".equals(ws.getOperatingStation())) {
					nextPage = "/OpenTask";
				}
			}
			else if("manager".equals(layout) && user.isModifier())
			{
				nextPage = "/Views/Manager/Main/Main.jsp";
			}
			else if("manager".equals(layout) && !user.isModifier()) {
				request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.LOGIN_FAILED));
				nextPage = "/Views/Login/loginPage.jsp";
			}
			getServletContext().getRequestDispatcher(null == nextPage ? "/Views/WelcomePage/WelcomePage.jsp" : nextPage).forward(request, response);
		} catch ( NamingException | LoginException | SQLException t) {
			System.out.println(t);
			request.setAttribute("infoTitle", ((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.LOGIN_FAILED));
			getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
		}
	}

}