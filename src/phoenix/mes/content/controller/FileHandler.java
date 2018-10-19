package phoenix.mes.content.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		String url = request.getParameter("file");
		try
		{
			if(open(url))
			{
				result = "Megnyitás sikeres!";
			}
		}catch(Exception e)
		{
			result = "Megnyitás sikertelen!";
		}
	    response.getWriter().write(result); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected static boolean open(String url) throws Exception
	{

				File pdfFile = new File(url);
				if (pdfFile.exists()) {

					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pdfFile);
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}


	}

}
