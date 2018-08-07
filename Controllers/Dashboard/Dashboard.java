package Dashboard;
import java.io.IOException;
//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       public void asd() {
    	   System.out.println("asdadadsadasdasdasdass");
       }
	       
boolean way;
       String username;
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		//request.setAttribute("books", bookRepo.listBooks());
       username = request.getParameter("username");
   	}
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       if(username.equals(null) || username.equals(""))
       {
      		getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp").forward(request, response);
       }
        if(username.equals("1"))
        {
       		getServletContext().getRequestDispatcher("/Views/TaskView/taskViewPage.jsp").forward(request, response);
        }
        if(username.equals("2"))
        {
       		getServletContext().getRequestDispatcher("/Views/TaskManage/taskManagePage.jsp").forward(request, response);
        }


   	}  
       
}
