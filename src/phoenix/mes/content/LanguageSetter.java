package phoenix.mes.content;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.abas.erp.common.type.enums.EnumLanguageCode;

/**
 * Servlet implementation class LanguageSetter
 */
public class LanguageSetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       LanguageSource l = new LanguageSource();
       String lang;
    public LanguageSetter() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		lang = request.getParameter("language");
 	    HttpSession session = request.getSession();
 	    session.removeAttribute("language");
 	    session.setAttribute("language", lang);
 	    System.out.println(session.getAttribute("language"));
		
        switch (lang) {
        case "hu":  session.setAttribute("abasLanguageType", EnumLanguageCode.Hungarian);
                 break;
        case "de":  session.setAttribute("abasLanguageType", EnumLanguageCode.German);
                 break;
        case "en":   session.setAttribute("abasLanguageType", EnumLanguageCode.English);
                 break;
        default:   session.setAttribute("abasLanguageType", EnumLanguageCode.Hungarian);
                 break;
    }
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
