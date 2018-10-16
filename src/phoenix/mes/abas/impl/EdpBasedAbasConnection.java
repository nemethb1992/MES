/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl;

import de.abas.ceks.jedp.CantBeginSessionException;
import de.abas.ceks.jedp.CantChangeSettingException;
import de.abas.ceks.jedp.CantReadSettingException;
import de.abas.ceks.jedp.EDPConnectionProperties;
import de.abas.ceks.jedp.EDPCredentialsProvider;
import de.abas.ceks.jedp.EDPException;
import de.abas.ceks.jedp.EDPFactory;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.EDPSessionOptions;

import javax.security.auth.login.LoginException;

import phoenix.mes.OperatingLanguage;
import phoenix.mes.abas.AbasConnection;

/**
 * Alaposztály EDP-alapú Abas-kapcsolatokat reprezentáló osztályok készítéséhez.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class EdpBasedAbasConnection<C> implements AbasConnection<C> {

	/**
	 * EDP-munkamenet megnyitása a megadott bejelentkezési adatokkal és beállításokkal.
	 * @param edpCredentialsProvider A bejelentkezési adatok.
	 * @param operatingLanguage A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelv).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @param edpSessionOptions Az EDP-munkamenet beállításai.
	 * @return Az EDP-munkamenet.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	protected static EDPSession startEdpSession(EDPCredentialsProvider edpCredentialsProvider, OperatingLanguage operatingLanguage, boolean testSystem, EDPSessionOptions edpSessionOptions) throws LoginException {
		final EDPSession edpSession = EDPFactory.createEDPSession();
		try {
			// @see de.abas.erp.db.internal.impl.jedp.MyJOISession#beginSession(EDPSession, ConnectionProperties, CredentialsProvider)
			edpSession.beginSession(new EDPConnectionProperties(SERVER_NAME, EDPConnectionProperties.DEFAULT_EDP_PORT, testSystem ? TEST_CLIENT_PATH : PRODUCTION_CLIENT_PATH, "JOI"),
					edpCredentialsProvider);
			if (null != operatingLanguage) {
				edpSession.setEKSLanguage(operatingLanguage.name());
			}
			configureEdpSession(edpSession, edpSessionOptions);
			return edpSession;
		} catch (CantBeginSessionException e) {
			endEdpSessionQuietly(edpSession);
			throw new LoginException(e.getMessage());
		} catch (EDPException e) {
			endEdpSessionQuietly(edpSession);
			throw new EDPRuntimeException(e);
		} catch (RuntimeException e) {
			endEdpSessionQuietly(edpSession);
			throw e;
		}
	}

	/**
	 * A megadott EDP-munkamenet beállítása.
	 * @param edpSession Az EDP-munkamenet.
	 * @param edpSessionOptions Az EDP-munkamenet beállításai.
	 * @throws CantChangeSettingException Ha hiba történt az EDP-munkamenet beállítása során.
	 */
	protected static void configureEdpSession(EDPSession edpSession, EDPSessionOptions edpSessionOptions) throws CantChangeSettingException {
		edpSession.setSessionOptions(edpSessionOptions);
		// @see de.abas.erp.db.internal.impl.jedp.MyJOISession#setPropertiesAndFlags(EDPSession)
		edpSession.setAutoRound(true);
		edpSession.setFlag(305, true);
	}

	/**
	 * A megadott EDP-munkamenet lezárása, kivételek dobása nélkül.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected static void endEdpSessionQuietly(EDPSession edpSession) {
		try {
			edpSession.endSession();
		} catch (Exception e) {
		}
	}

	/**
	 * A kezelőnyelv.
	 */
	protected final OperatingLanguage operatingLanguage;

	/**
	 * Konstruktor.
	 * @param edpSession Az EDP-munkamenet.
	 */
	protected EdpBasedAbasConnection(EDPSession edpSession) {
		try {
			this.operatingLanguage = OperatingLanguage.valueOf(edpSession.getLanguageISO());
		} catch (CantReadSettingException e) {
			throw new EDPRuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasConnection#getOperatingLanguage()
	 */
	@Override
	public OperatingLanguage getOperatingLanguage() {
		return operatingLanguage;
	}

}
