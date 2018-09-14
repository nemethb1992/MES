package phoenix.mes.content.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Home extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	    HttpSession session = request.getSession();
 	    session.removeAttribute("username");
 	    session.removeAttribute("pass");
 	    session.removeAttribute("selectedStation");
 	    session.removeAttribute("ws_group");
 	    session.removeAttribute("ws_no");
 	    session.removeAttribute("language");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
	}

}
