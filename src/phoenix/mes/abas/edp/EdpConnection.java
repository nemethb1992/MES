/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.edp;

import de.abas.ceks.jedp.CantChangeSettingException;
import de.abas.ceks.jedp.EDPCredentialsProvider;
import de.abas.ceks.jedp.EDPFTextMode;
import de.abas.ceks.jedp.EDPLockBehavior;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.EDPSessionOptions;
import de.abas.ceks.jedp.EDPVariableLanguage;
import de.abas.erp.common.type.enums.EnumLanguageCode;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.EdpBasedAbasConnection;

/**
 * Abas-kapcsolatot reprezentáló osztály, EDP-ben implementálva.
 * @author szizo
 */
public class EdpConnection extends EdpBasedAbasConnection<EDPSession> {

	/**
	 * Az EDP-munkamenet.
	 */
	protected final EDPSession edpSession;

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Az Abas-kapcsolathoz tartozó EDP-munkamenet.
	 * @throws IllegalArgumentException Ha az Abas-kapcsolat nem EDP-típusú.
	 */
	public static EDPSession getEdpSession(AbasConnection<?> abasConnection) {
		if (abasConnection instanceof EdpConnection) {
			return ((EdpConnection)abasConnection).getConnectionObject();
		}
		throw new IllegalArgumentException("Nem megfelelő Abas-kapcsolattípus: " + abasConnection.getClass().getName());
	}

	/**
	 * EDP-munkamenet megnyitása a megadott bejelentkezési adatokkal.
	 * @param edpCredentialsProvider A bejelentkezési adatok.
	 * @param operatingLanguage A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelv).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return Az EDP-munkamenet.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	protected static EDPSession startEdpSession(EDPCredentialsProvider edpCredentialsProvider, EnumLanguageCode operatingLanguage, boolean testSystem) throws LoginException {
		return startEdpSession(edpCredentialsProvider, operatingLanguage, testSystem, getEdpSessionOptions());
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
	 * @param edpCredentialsProvider A bejelentkezési adatok.
	 * @param operatingLanguage A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelv).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	public EdpConnection(EDPCredentialsProvider edpCredentialsProvider, EnumLanguageCode operatingLanguage, boolean testSystem) throws LoginException {
		this(startEdpSession(edpCredentialsProvider, operatingLanguage, testSystem));
	}

	/**
	 * Konstruktor.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected EdpConnection(EDPSession edpSession) {
		this.edpSession = edpSession;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasConnection#getConnectionObject()
	 */
	@Override
	public EDPSession getConnectionObject() {
		return edpSession;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasConnection#close()
	 */
	@Override
	public void close() {
		edpSession.endSession();
	}

}
