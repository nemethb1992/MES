package phoenix.mes.content.controller;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ActiveDirectoryLogin {
    public static boolean activeDirectoryConn(String user, String pwd) {
        Hashtable<String, String> env = new Hashtable<>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.144.21:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@pmhu.local");
        env.put(Context.SECURITY_CREDENTIALS, pwd);
        try {
            if (!"".equals(user) && !"".equals(pwd)) {
                DirContext ctx = new InitialDirContext(env);
                ctx.close();
                return true;
            }
        } catch (NamingException e) {
            return false;
        }
        return false;
    }
}
