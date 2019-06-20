/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.CantChangeSettingException;
import de.abas.ceks.jedp.CantReadSettingException;
import de.abas.ceks.jedp.DefaultEDPCredentialsProvider;
import de.abas.ceks.jedp.EDPCredentialsProvider;
import de.abas.ceks.jedp.EDPFTextMode;
import de.abas.ceks.jedp.EDPLockBehavior;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.EDPSessionOptions;
import de.abas.ceks.jedp.EDPVariableLanguage;
import de.abas.erp.db.schema.company.Password;

import java.util.Locale;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.impl.EdpBasedAbasConnection;

/**
 * Abas-kapcsolatot reprezentáló osztály, EDP-ben implementálva.
 * @author szizo
 */
public class EdpConnection extends EdpBasedAbasConnection<EDPSession> {

	/**
	 * Segédosztály a bejelentkezett felhasználó nevének lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class UserDisplayNameQuery extends EdpQueryExecutor {

		/**
		 * Egyke objektum.
		 */
		public static final UserDisplayNameQuery EXECUTOR = new UserDisplayNameQuery();

		/**
		 * Konstruktor.
		 */
		private UserDisplayNameQuery() {
			super(new String[] {Password.META.descrOperLang.getName()});
		}

	}

	/**
	 * Az EDP-munkamenet.
	 */
	protected final EDPSession edpSession;

	/**
	 * EDP-munkamenet megnyitása a megadott bejelentkezési adatokkal.
	 * @param edpCredentialsProvider A bejelentkezési adatok.
	 * @param locale A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelvvel történik a kapcsolódás).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return Az EDP-munkamenet.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	protected static EDPSession startEdpSession(EDPCredentialsProvider edpCredentialsProvider, Locale locale, boolean testSystem) throws LoginException {
		return startEdpSession(edpCredentialsProvider, locale, testSystem, getEdpSessionOptions());
	}

	/**
	 * @return Az EDP-munkamenet beállításai.
	 */
	protected static EDPSessionOptions getEdpSessionOptions() {
		final EDPSessionOptions edpSessionOptions = new EDPSessionOptions(EDPSession.DISPLAYMODE_UNIVERSAL);
		edpSessionOptions.enableLongLockMessages(true);
		edpSessionOptions.enableNotes(true);
		edpSessionOptions.enableTextMessages(true);
		edpSessionOptions.setFTextMode(EDPFTextMode.FTEXTMODE_EXT);
		edpSessionOptions.setLineBreak(EDPSession.LINEBREAK_LF);
		edpSessionOptions.setLockBehavior(new EDPLockBehavior(EDPLockBehavior.WAIT, 1));
		edpSessionOptions.setVariableLanguage(EDPVariableLanguage.ENGLISH);
		return edpSessionOptions;
	}

	/**
	 * A megadott EDP-munkamenet beállítása.
	 * @param edpSession Az EDP-munkamenet.
	 * @param edpSessionOptions Az EDP-munkamenet beállításai.
	 * @throws CantChangeSettingException Ha hiba történt az EDP-munkamenet beállítása során.
	 */
	protected static void configureEdpSession(EDPSession edpSession, EDPSessionOptions edpSessionOptions) throws CantChangeSettingException {
		EdpBasedAbasConnection.configureEdpSession(edpSession, edpSessionOptions);
	}

	/**
	 * A megadott EDP-munkamenet lezárása, kivételek dobása nélkül.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected static void endEdpSessionQuietly(EDPSession edpSession) {
		EdpBasedAbasConnection.endEdpSessionQuietly(edpSession);
	}

	/**
	 * Konstruktor.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param locale A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelvvel történik a kapcsolódás).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	protected EdpConnection(String userName, String password, Locale locale, boolean testSystem) throws LoginException {
		this(new DefaultEDPCredentialsProvider(userName, password), locale, testSystem);
	}

	/**
	 * Konstruktor.
	 * @param edpCredentialsProvider A bejelentkezési adatok.
	 * @param locale A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelvvel történik a kapcsolódás).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	protected EdpConnection(EDPCredentialsProvider edpCredentialsProvider, Locale locale, boolean testSystem) throws LoginException {
		this(startEdpSession(edpCredentialsProvider, locale, testSystem));
	}

	/**
	 * Konstruktor.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected EdpConnection(EDPSession edpSession) {
		super(edpSession);
		this.edpSession = edpSession;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasConnection#getConnectionObject()
	 */
	@Override
	public EDPSession getConnectionObject() {
		return edpSession;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasConnection#getUserDisplayName()
	 */
	@Override
	public String getUserDisplayName() {
		try {
			return UserDisplayNameQuery.EXECUTOR.readRecord(edpSession.getPasswordRef(), edpSession).getField(1);
		} catch (CantReadSettingException e) {
			throw new EDPRuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasConnection#close()
	 */
	@Override
	public void close() {
		edpSession.endSession();
	}

}
