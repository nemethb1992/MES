package phoenix.mes.content.navigation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
 	    HttpSession session = request.getSession();
 	    String layout = (String)session.getAttribute("Layout");
		String encodedURL = response.encodeRedirectURL((layout == null || "".equals(layout) ? "/" : (layout == "manager" ? "/Manager" : "/Operator")));
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
// 	    session.invalidate();
// 	    RequestDispatcher rd = request.getRequestDispatcher((layout == null || "".equals(layout) ? "/" : (layout == "manager" ? "/Manager" : "/Operator")));
// 	    rd.forward(request, response);
//  		getServletContext().getRequestDispatcher((layout == null || "".equals(layout) ? "/" : (layout == "manager" ? "/Manager" : "/Operator"))).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
