package phoenix.mes.content.navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.content.PostgreSqlOperationsMES;

/**
 * Servlet implementation class Settings
 */
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setAttribute("Pc", getPc());
			request.setAttribute("Group", getGroup());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/Views/TaskManage/Settings/Settings.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected Collection<String> getPc() throws SQLException
	{
		PostgreSqlOperationsMES pg = new PostgreSqlOperationsMES(true);
		return pg.sqlQuery("SELECT * FROM profitcenter", "long");
	}
	
	protected Collection<String> getGroup() throws SQLException
	{
		PostgreSqlOperationsMES pg = new PostgreSqlOperationsMES(true);
		return pg.sqlQuery("SELECT * FROM stations GROUP BY csoport", "csoport");
	}
}
