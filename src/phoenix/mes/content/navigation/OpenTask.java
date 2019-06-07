package phoenix.mes.content.navigation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OpenTask
 */
public class OpenTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  		getServletContext().getRequestDispatcher("/Logout").forward(request, response);
	}

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.encodeUrl("/Views/Operator/OpenTask/OpenTask.jsp");
		getServletContext().getRequestDispatcher("/Views/Operator/OpenTask/OpenTask.jsp").forward(request, response);
	}

}
