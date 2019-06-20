package phoenix.mes.content.controller;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.OutputFormatter.DictionaryEntry;

public class AbasAuthentication {
	
    public boolean bind(String username, String password, HttpServletRequest request) throws NamingException, LoginException {

    	HttpSession session = request.getSession();
    	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
    	
    	if (username.isEmpty() || password.isEmpty()) {
    		throw new AuthenticationException(of.getWord(DictionaryEntry.LOGIN_FAILED_EMPTY_CREDENTIALS));
    	}
    	AbasConnection abasConnection = null;
    	try {
    		boolean isTest = new AppBuild(request).isTest();
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(username, password, of.getLocale(),isTest );
    		if(abasConnection.getConnectionObject().isConnected())
    		{
    			session.setAttribute("displayname", abasConnection.getUserDisplayName());
    		}
    	} catch (Throwable t) {
    		
    		System.out.println(t.getMessage());
			return false;

    	}finally
    	{			
    		try {
    			abasConnection.close();
    		} catch (Throwable t2) {
    		}
    	}
    	return true;
    }
    
}
