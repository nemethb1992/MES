package phoenix.mes.content.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DocumentSideView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String encodedURL = response.encodeRedirectURL("/Logout");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("URI", request.getParameter("uri"));
		request.setAttribute("name", request.getParameter("name"));
		
		
		String encodedURL = response.encodeRedirectURL("/Views/Partial/DocumentSideView.jsp");
		request.getRequestDispatcher(encodedURL).forward(request, response);
	}

}
