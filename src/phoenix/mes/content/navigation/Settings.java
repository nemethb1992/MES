package phoenix.mes.content.navigation;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.PostgreSql;

/**
 * Servlet implementation class Settings
 */
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setAttribute("pcList", new PostgreSql(true).sqlQuery("SELECT long FROM profitcenter","long"));
			request.setAttribute("groupList", new PostgreSql(true).sqlQuery("SELECT csoport FROM stations GROUP BY csoport","csoport"));
		} catch (SQLException e) {
		}		
		getServletContext().getRequestDispatcher("/Views/Manager/Settings/Settings.jsp").forward(request, response);
	}

}
