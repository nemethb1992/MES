package phoenix.mes.content;

public class SessionData {

	private static String username;
	public static void setUsername(String value)
	{
		username = value;
	}
	public static String getUsername()
	{
	    return username;
	}	
	
	private static String pass;
	public static void setPassword(String value)
	{
		pass = value;
	}
	public static String getPassword()
	{
	    return pass;
	}
	
	private static String language;
	public static void setLanguage(String value)
	{
		language = value;
	}
	public static String getLanguage()
	{
	    return language;
	}
	

	private static String WS_Group;
	public static void setWS_Group(String value)
	{
		WS_Group = value;
	}
	public static String getWS_Group()
	{
	    return WS_Group;
	}
	private static int WS_No;
	public static void setWS_No(int value)
	{
		WS_No = value;
	}
	public static int getWS_No()
	{
	    return WS_No;
	}
}
