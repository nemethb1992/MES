package phoenix.mes.content.controller;
import java.io.IOException;

//import com.google.gson.Gson;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import phoenix.mes.content.SessionData;
import phoenix.mes.content.LanguageSource;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ActiveDirectoryLogin ac = new ActiveDirectoryLogin();
//       public void asd() {
//    	   System.out.println(Session_Datas.getUsername());
//       }
       boolean way;
       String username = "";
       String pass = "";
       String ws_group;
       int ws_no;
       int layout = 0;
       SessionData sess = new SessionData();
   	   LanguageSource lng = new LanguageSource();
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		//request.setAttribute("books", bookRepo.listBooks());
    	   LanguageSource.setLng(lng.LanguageList());
    	   username = request.getParameter("username");
    	   pass = request.getParameter("pass");
    	   ws_group = request.getParameter("ws_group");
    	   ws_no = Integer.parseInt(request.getParameter("ws_no"));
    	   layout = Integer.parseInt(request.getParameter("layout"));
//    	   System.out.println(ws_group + "test");
//    	   System.out.println(ws_no + "test");
   	}
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	   SessionData.setUsername(username);
    	   SessionData.setPassword(pass);
    	   SessionData.setWS_Group(ws_group);
    	   SessionData.setWS_No(ws_no);

    if(ac.activeDirectoryConn(username, pass))
    {
//    	System.out.println(layout + " layout");
    	if(layout == 2)
   		getServletContext().getRequestDispatcher("/Views/TaskView/taskViewPage.jsp").forward(request, response);
    	if(layout == 1)
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
