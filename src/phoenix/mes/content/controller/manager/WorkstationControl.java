package phoenix.mes.content.controller.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.utility.RenderView;

/**
 * Servlet implementation class StationControl
 */
public class WorkstationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String level = "0";
		if(request.getParameter("level") != null){
			level = request.getParameter("level");
		}
		String value = request.getParameter("element");
		request.setAttribute("level", level);
		request.setAttribute("value", value);

		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(RenderView.render("/Views/Manager/Todo/Partial/WorkstationList.jsp", request, response));
	}
}
