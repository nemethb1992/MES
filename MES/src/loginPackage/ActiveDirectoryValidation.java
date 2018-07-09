package loginPackage;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ActiveDirectoryValidation {
	
	public boolean AD_UserValider(String user, String pwd) {
        boolean validation = false;
        Hashtable<String, String> env = new Hashtable<>(11);

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.144.21:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@pmhu.local");
        env.put(Context.SECURITY_CREDENTIALS, pwd);

        try {
            if (!"".equals(user) && !"".equals(pwd)) {
                DirContext ctx = new InitialDirContext(env);
                validation = true;
                ctx.close();
            }

        } catch (NamingException e) {
            validation = false;
        }
        return validation;
    }
}
