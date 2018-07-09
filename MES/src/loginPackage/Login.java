package loginPackage;
import java.io.IOException;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
        String appName = request.getContextPath();
//        ActiveDirectoryValidation ADV = new ActiveDirectoryValidation();
//        Boolean validation = false;
//        try
//        {
//            validation = ADV.AD_UserValider(username, password);
//        	
//        }finally{
//        	validation = false;
//        }
        
        
        String encodedURL;
        
        
//        if (validation) {
            encodedURL = response.encodeRedirectURL(appName + "/Views/TaskManage/taskManagePage.jsp");
            response.sendRedirect(encodedURL);
//        } else {
//
////            String log_fail = "Hibás felhasználónév vagy jelszó!" + " -- " + validation;
////            request.setAttribute("log_fail2", log_fail);
//            RequestDispatcher rd = getServletContext().getRequestDispatcher(appName + "/Views/Login/loginPage.jsp");
//            rd.include(request, response);
//        }
        
        
	}

}
