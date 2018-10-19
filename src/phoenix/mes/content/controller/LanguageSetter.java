package phoenix.mes.content.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.OutputFormatter;

public class LanguageSetter extends HttpServlet {

	public enum OperatingLanguage {

		// A felsoroláselemek az Abasban nyelvkódként használatosak, ezért az írásmódjuk nem követi az általános konvenciót
		hu(new Locale("hu")),
		de(Locale.GERMAN),
		en(Locale.ENGLISH);

		private final Locale locale;

		private OperatingLanguage(Locale locale) {
			this.locale = locale;
		}

		public Locale getLocale() {
			return locale;
		}

	}

	private static final long serialVersionUID = 1L;

	public static void languageSetup(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		if (session.getAttribute("OutputFormatter") != null) {
			return;
		}
		OperatingLanguage language = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("language")) {
					language = languageFromCode(cookie.getValue());
				}
			}
		}
		if (null == language) {
			language = languageFromCode(request.getLocale().getLanguage());
		}
		session.setAttribute("OutputFormatter", new OutputFormatter((null == language ? OperatingLanguage.hu : language).getLocale()));
	}

	protected static OperatingLanguage languageFromCode(String languageCode) {
		try {
			return OperatingLanguage.valueOf(languageCode);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OperatingLanguage language;
		try {
			language = OperatingLanguage.valueOf(request.getParameter("language"));
		} catch (IllegalArgumentException e) {
			language = OperatingLanguage.hu;
		}
		request.getSession().setAttribute("OutputFormatter", new OutputFormatter(language.getLocale()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
