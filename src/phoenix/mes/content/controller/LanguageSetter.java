package phoenix.mes.content.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.OutputFormatter.OperatingLanguage;

public class LanguageSetter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OperatingLanguage language;
		try {
			language = OperatingLanguage.valueOf(request.getParameter("language"));
		} catch (IllegalArgumentException e) {
			language = OperatingLanguage.hu;
		}
		request.getSession().setAttribute("OutputFormatter", new OutputFormatter(language.getLocale()));

		try {
			User user = new User(request);
			if(user.getUsername() != null)
			new AbasAuthentication().bind(user.getUsername(), user.getPassword(),request);
		} catch (LoginException | NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
