package phoenix.mes.content.controller;


import java.sql.SQLException;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.abas.ceks.jedp.EDPSession;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.utility.AccessControl;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.OutputFormatter.DictionaryEntry;

public class Authentication {
	
    public boolean bind(String user, String pwd, HttpServletRequest request) throws NamingException, LoginException {

    	HttpSession session = request.getSession();
    	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
    	if (user.isEmpty() || pwd.isEmpty()) {
    		throw new AuthenticationException(of.getWord(DictionaryEntry.LOGIN_FAILED_EMPTY_CREDENTIALS));
    	}
    	AbasConnection<EDPSession> abasConnection = null;
    	try {
    		boolean isTest = new AppBuild(request).isTest();
    		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user, pwd, of.getLocale(),isTest );
    		if(abasConnection.getConnectionObject().isConnected())
    		{
    			session.setAttribute("displayname", abasConnection.getUserDisplayName());
    			if(!isExists(user,request))
    			{
    				try {
        				new AccessControl(request,user).insert();
    				}catch(SQLException e){
    					return false;
    				}
    			}

    			PostgreSql pg = new PostgreSql(isTest);
				session.setAttribute("userid", pg.sqlSingleQuery("SELECT users.id FROM users WHERE username='"+user+"'", "id"));
				pg.dbClose();
    		}
    	} catch (Throwable t) {
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
    
    public boolean isExists(String username, HttpServletRequest request) throws SQLException
    {
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String result = pg.sqlSingleQuery("SELECT users.username FROM users WHERE username='"+username+"'", "username");
		pg.dbClose();
		if("".equals(result)){
			return false;
		}
		return true;
    }
}
