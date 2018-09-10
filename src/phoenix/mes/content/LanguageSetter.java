package phoenix.mes.content;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		l.setLng_type(lang);
		
        switch (lang) {
        case "hu":  l.setAbasLanguage(EnumLanguageCode.Hungarian);
                 break;
        case "de": l.setAbasLanguage(EnumLanguageCode.German);
                 break;
        case "en":  l.setAbasLanguage(EnumLanguageCode.English);
                 break;

        default:  l.setAbasLanguage(EnumLanguageCode.Hungarian);
                 break;
    }
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
