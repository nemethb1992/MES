/*
 * Osztálykönyvtár az AJO interfészhez.
 *
 * Created on Aug 21, 2018
 */

package phoenix.mes.abas.impl.ajo;

import de.abas.ceks.jedp.EDPCredentialsProvider;
import de.abas.ceks.jedp.EDPFTextMode;
import de.abas.ceks.jedp.EDPLockBehavior;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.EDPSessionOptions;
import de.abas.ceks.jedp.EDPVariableLanguage;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.internal.ClientContext;

import java.util.Locale;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.impl.EdpBasedAbasConnection;

/**
 * Abas-kapcsolatot reprezentáló osztály, AJO-ban implementálva.
 * @author szizo
 */
public class AjoConnection extends EdpBasedAbasConnection<DbContext> {

	/**
	 * Az AJO-környezet.
	 */
	protected final DbContext ajoContext;

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Az Abas-kapcsolathoz tartozó AJO-környezet.
	 * @throws IllegalArgumentException Ha az Abas-kapcsolat nem AJO-típusú.
	 */
	public static DbContext getAjoContext(AbasConnection<?> abasConnection) {
		if (abasConnection instanceof AjoConnection) {
			return ((AjoConnection)abasConnection).getConnectionObject();
		}
		throw new IllegalArgumentException("Nem megfelelő Abas-kapcsolattípus: " + abasConnection.getClass().getName());
	}

	/**
	 * @return Az EDP-munkamenet beállításai.
	 * @see de.abas.erp.db.internal.impl.jedp.MyJOISession#setPropertiesAndFlags(EDPSession)
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
	 * Konstruktor.
	 * @param edpCredentialsProvider A bejelentkezési adatok.
	 * @param locale A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelvvel történik a kapcsolódás).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	public AjoConnection(EDPCredentialsProvider edpCredentialsProvider, Locale locale, boolean testSystem) throws LoginException {
		this(startEdpSession(edpCredentialsProvider, locale, testSystem, getEdpSessionOptions()));
	}

	/**
	 * Konstruktor.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected AjoConnection(EDPSession edpSession) {
		super(edpSession);
		try {
			final ClientContext ajoContext = new ClientContext(edpSession);
			// @see de.abas.erp.db.internal.AbstractContext#initSession()
			edpSession.setErrorMessageListener(ajoContext);
			edpSession.setStatusMessageListener(ajoContext);
			edpSession.setTextMessageListener(ajoContext);
			this.ajoContext = ajoContext;
		} catch (RuntimeException e) {
			endEdpSessionQuietly(edpSession);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasConnection#getConnectionObject()
	 */
	@Override
	public DbContext getConnectionObject() {
		return ajoContext;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasConnection#close()
	 */
	@Override
	public void close() {
		if (null != ajoContext) {
			ajoContext.close();
		}
	}

}
