package phoenix.mes.content.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.erp.common.type.enums.EnumLanguageCode;
import phoenix.mes.content.Dictionary;

public class LanguageSetter extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String language;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		language = request.getParameter("language");
		HttpSession session = request.getSession();
		session.removeAttribute("Dictionary");

		switch (language) {
		case "de": {
			session.setAttribute("Dictionary", new Dictionary(EnumLanguageCode.German));
			break;
		}
		case "en":  {
			session.setAttribute("Dictionary", new Dictionary(EnumLanguageCode.English));
			break;
		}
		case "hu": 
		default:{
			session.setAttribute("Dictionary", new Dictionary(EnumLanguageCode.Hungarian));
			break;
		}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
