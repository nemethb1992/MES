package phoenix.mes.content.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.utility.Pdf;


public class DocumentModal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String encodedURL = response.encodeRedirectURL("/Logout");
		getServletContext().getRequestDispatcher(encodedURL).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pdf = new Pdf(request.getParameter("uri")).getData();
		System.out.println(pdf);
//		if(null == pdf) {
//			return;
//		}
		request.setAttribute("URI", request.getParameter("uri"));
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("pdf", pdf);
		
		
		String encodedURL = response.encodeRedirectURL("/Views/Partial/DocumentModal.jsp");
		request.getRequestDispatcher(encodedURL).forward(request, response);
	}

}
