package phoenix.mes.content;

import javax.servlet.http.HttpServletRequest;

public class AppBuild {
	
	HttpServletRequest request;
	boolean test;
	String name;
	
	public AppBuild(HttpServletRequest request)
	{
		if(request != null)
		{
			this.request = request;
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
	
	public boolean isStable()
	{
		return (request.getSession().getAttribute("OutputFormatter") != null ? true : false);
	}
	
	public void setSessionListener()
	{
		request.getSession().setAttribute("ping", true);
	}
	
	public boolean isOperator()
	{
		String layout = (String)request.getSession().getAttribute("Layout");
		return ("operator".equals(layout) ? true : false);
	}
}
