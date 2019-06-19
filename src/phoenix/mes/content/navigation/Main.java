package phoenix.mes.content.navigation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SelectPanel
 */
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String encodedURL = response.encodeRedirectURL("/Views/WelcomePage/WelcomePage.jsp");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String encodedURL = response.encodeRedirectURL("/Views/Manager/Main/Main.jsp");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
	}

}

