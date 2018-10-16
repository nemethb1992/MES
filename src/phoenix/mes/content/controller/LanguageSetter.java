package phoenix.mes.content.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.OperatingLanguage;
import phoenix.mes.content.Dictionary;

public class LanguageSetter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OperatingLanguage language;
		try {
			language = OperatingLanguage.valueOf(request.getParameter("language"));
		} catch (IllegalArgumentException e) {
			language = OperatingLanguage.hu;
		}
		request.getSession().setAttribute("Dictionary", new Dictionary(language));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public OperatingLanguage languageSetup(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		OperatingLanguage Language = OperatingLanguage.hu;
		
		if(session.getAttribute("Dictionary") == null)
		{
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
			 for (Cookie cookie : cookies) {
			   if (cookie.getName().equals("language")) {
				   Language = OperatingLanguage.valueOf(cookie.getValue());
			    }
			  }
			}
			else
			{
				Locale browserLocale = request.getLocale();
				String language = browserLocale.toString().split("_")[0];
				if(language == "hu" || language == "en" || language =="de")
				{
					Language = OperatingLanguage.valueOf(language);
				}
			}
			session.setAttribute("Dictionary", new Dictionary(Language));
		}
		return Language;
	}

}
