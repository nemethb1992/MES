package phoenix.mes.content.controller.operator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.controller.OperatingWorkstation;

public class SwitchWorkstation extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AppBuild ab = new AppBuild(request);
		if(!ab.isStabile()){
			doGet(request,response);
			return;
		}

		String paramStation = request.getParameter("workstation");
		new OperatingWorkstation(request,paramStation);
		
	}

}
