package phoenix.mes.content;

import javax.servlet.http.HttpServletRequest;

public class AppBuild {
	
	boolean test;
	String name;
	
	public AppBuild(HttpServletRequest request)
	{
		String url = request.getRequestURI();
		name = url.split("/")[1];
		if((name.toLowerCase()).equals("mes"))
			test = false;
		else{
			test = true;
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
	
}
