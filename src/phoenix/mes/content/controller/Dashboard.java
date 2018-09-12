package phoenix.mes.content.controller;
import java.io.IOException;

//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.LanguageSource;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ActiveDirectoryLogin ac = new ActiveDirectoryLogin();
	LanguageSource lng = new LanguageSource();
	boolean way;
	String username = "";
	String pass = "";
	String ws_group;
	int ws_no;
	int layout;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = request.getParameter("username");
		pass = request.getParameter("pass");
		ws_group = request.getParameter("ws_group");
		ws_no = Integer.parseInt(request.getParameter("ws_no"));
		layout = Integer.parseInt(request.getParameter("layout"));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		//    	   session.setAttribute("layout", layout);
		session.setAttribute("username",username);
		session.setAttribute("pass",pass);
		session.setAttribute("ws_group",ws_group);
		session.setAttribute("ws_no",ws_no);

		if(ac.activeDirectoryConn(username, pass))
		{
			if(layout == 1)
				getServletContext().getRequestDispatcher("/Views/TaskView/taskViewPage.jsp").forward(request, response);
			if(layout == 2)
				getServletContext().getRequestDispatcher("/Views/TaskManage/taskManagePage.jsp").forward(request, response);
			if(layout == 0)
				getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);

		}
		else
		{
			getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
		}
	}       
}
