/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Jun 16, 2019
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPSession;

import java.util.Locale;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.impl.edp.EdpConnection;

/**
 * Az alapértelmezett típusú Abas-kapcsolatot reprezentáló osztály.
 * @author szizo
 */
public class AbasConnection extends EdpConnection {

	/**
	 * Konstruktor.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param locale A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelvvel történik a kapcsolódás).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	protected AbasConnection(String userName, String password, Locale locale, boolean testSystem) throws LoginException {
		super(userName, password, locale, testSystem);
	}

	/**
	 * Konstruktor.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected AbasConnection(EDPSession edpSession) {
		super(edpSession);
	}

}
