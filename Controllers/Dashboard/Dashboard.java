package Dashboard;
import java.io.IOException;
import java.util.ArrayList;

//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import ActiveDirectory.ActiveDirectoryLogin;
import Database.dbEntities;
import Session.Session_Datas;
import de.abas.erp.common.ConnectionProperties;
import de.abas.erp.common.DefaultCredentialsProvider;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.util.ContextHelper;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ActiveDirectoryLogin ac = new ActiveDirectoryLogin();
       public void asd() {
    	   System.out.println(Session_Datas.getUsername());
       }
       boolean way;
       String username = "";
       String pass = "";
       int layout = 0;
       Session_Datas sess = new Session_Datas();
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		//request.setAttribute("books", bookRepo.listBooks());

    	   username = request.getParameter("username");
    	   pass = request.getParameter("pass");
    	   layout = Integer.parseInt(request.getParameter("layout"));
   	}
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	   Session_Datas.setUsername(username);
    	   Session_Datas.setPassword(pass);

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

   		
//    if(username.equals(null) || username.equals(""))
//    {
//    	getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
//    }
//     if(username.equals("1"))
//    {
//    		getServletContext().getRequestDispatcher("/Views/TaskView/taskViewPage.jsp").forward(request, response);
//    		}
//    if(username.equals("2"))
//    {
//    		getServletContext().getRequestDispatcher("/Views/TaskManage/taskManagePage.jsp").forward(request, response);
//     }

   	}  
       
}
