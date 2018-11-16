package phoenix.mes.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AppBuild {
	
	boolean test;
	String name;
	
	public AppBuild(HttpServletRequest request)
	{
		if(request != null)
		{
			String url = request.getRequestURI();
			name = url.split("/")[1];
			if((name.toLowerCase()).equals("mes"))
				test = false;
			else{
				test = true;
			}
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isTest()
	{
		return test;
	}
	
	public boolean isStable(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		return (session.getAttribute("OutputFormatter") != null ? true : false);
	}
	
	public void setSessionListener(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute("ping", true);
	}
}
