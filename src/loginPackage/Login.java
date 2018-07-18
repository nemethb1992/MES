package loginPackage;
import java.io.IOException;
//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       public void asd() {
    	   System.out.println("asdadadsadasdasdasdass");
       }
	       
       
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		//request.setAttribute("books", bookRepo.listBooks());
   		getServletContext().getRequestDispatcher("/Views/TaskManage/taskManagePage.jsp").forward(request, response);
//   		String asd = "Hello World!";
   		
   		
//        request.setAttribute("testtest", asd);
////        RequestDispatcher ad = getServletContext().getRequestDispatcher("/Views/Login/loginPage.jsp");
////        ad.include(request, response);
//
//
//        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
//        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
//        response.getWriter().write(asd); 
   	}
        
       
}
