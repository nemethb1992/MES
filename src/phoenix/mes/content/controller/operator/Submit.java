package phoenix.mes.content.controller.operator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Submit
 */
public class Submit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    String quantity;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		quantity = request.getParameter("quantity");
		System.out.println(quantity);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
