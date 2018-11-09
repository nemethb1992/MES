package phoenix.mes.content.controller;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import phoenix.mes.content.OutputFormatter;
import phoenix.mes.content.OutputFormatter.DictionaryEntry;

public class ActiveDirectory {
	
    public static void login(String user, String pwd, HttpServletRequest request) throws NamingException {
    	
        if (user.isEmpty() || pwd.isEmpty()) {
        	HttpSession session = request.getSession();
        	throw new AuthenticationException(((OutputFormatter)session.getAttribute("OutputFormatter")).getWord(DictionaryEntry.LOGIN_FAILED_EMPTY_CREDENTIALS));
        }
        final Hashtable<String, String> env = new Hashtable<>(8);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.144.21:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@pmhu.local");
        env.put(Context.SECURITY_CREDENTIALS, pwd);
    	DirContext ctx = new InitialDirContext(env);
    	try {
        	ctx.close();
    	} catch (Throwable t) {
    	}
    }
}
