package phoenix.mes.content.controller;


import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.OutputFormatter.DictionaryEntry;

public class Authentication {
	
    public static void bind(String user, String pwd, HttpServletRequest request) throws NamingException, LoginException {

    	HttpSession session = request.getSession();
    	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
        if (user.isEmpty() || pwd.isEmpty()) {
        	throw new AuthenticationException(of.getWord(DictionaryEntry.LOGIN_FAILED_EMPTY_CREDENTIALS));
        }
        AbasConnection<EDPSession> abasConnection = null;
    	try {
        	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user, pwd, of.getLocale(), new AppBuild(request).isTest());
        	if(abasConnection.getConnectionObject().isConnected())
        	{
            	session.setAttribute("displayname", abasConnection.getUserDisplayName()); 
        	}
    	} catch (Throwable t) {
    		
    	}finally
		{			
			try {
			abasConnection.close();
		} catch (Throwable t2) {
		}
		}
    }
}
