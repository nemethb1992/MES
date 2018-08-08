/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Aug 7, 2018
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPConnectionProperties;
import de.abas.ceks.jedp.EDPException;
import de.abas.ceks.jedp.EDPFTextMode;
import de.abas.ceks.jedp.EDPFactory;
import de.abas.ceks.jedp.EDPLockBehavior;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.EDPVariableLanguage;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumLanguageCode;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.internal.ClientContext;
import de.abas.erp.db.schema.capacity.WorkCenter;
import de.abas.erp.db.util.QueryUtil;

/**
 * Gyár osztály az Abas-interfész objektumainak létrehozásához.
 * @author szizo
 */
public final class ObjectFactory {

	/**
	 * Új Abas-munkamenet indítása a megadott bejelentkezési adatokkal, a felhasználónál beállított kezelőnyelvvel.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return Az Abas-munkamenet.
	 * @throws CantBeginSessionException Ha hiba történt a bejelentkezés során.
	 * @throws EDPException Ha hiba történt az Abas-munkamenet beállítása során.
	 */
	public static DbContext startAbasSession(String userName, String password, boolean testSystem) throws EDPException {
		return startAbasSession(userName, password, null, testSystem);
	}

	/**
	 * Új Abas-munkamenet indítása a megadott bejelentkezési adatokkal.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param operatingLanguage A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelv).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return Az Abas-munkamenet.
	 * @throws CantBeginSessionException Ha hiba történt a bejelentkezés során.
	 * @throws EDPException Ha hiba történt az Abas-munkamenet beállítása során.
	 */
	public static DbContext startAbasSession(String userName, String password, EnumLanguageCode operatingLanguage, boolean testSystem) throws EDPException {
		final EDPSession edpSession = EDPFactory.createEDPSession();
		edpSession.beginSessionUser("abasdb.pmhu.local", EDPConnectionProperties.DEFAULT_EDP_PORT, "/Abas/" + (testSystem ? "dpmk" : "pmk"), userName, password, "JOI");
		if (null != operatingLanguage) {
			edpSession.setEKSLanguage(operatingLanguage.getDisplayString());
		}
		setEDPSessionProperties(edpSession);
		final ClientContext abasSession = new ClientContext(edpSession);
		// @see de.abas.erp.db.internal.AbstractContext#initSession()
		edpSession.setErrorMessageListener(abasSession);
		edpSession.setStatusMessageListener(abasSession);
		edpSession.setTextMessageListener(abasSession);
		return abasSession;
	}

	/**
	 * A megadott EDP-munkamenet paramétereinek a beállítása.
	 * @param edpSession Az EDP-munkamenet.
	 * @throws EDPException Ha hiba történt az EDP-munkamenet beállítása során.
	 * @see de.abas.erp.db.internal.impl.jedp.MyJOISession#setPropertiesAndFlags(EDPSession)
	 */
	protected static void setEDPSessionProperties(EDPSession edpSession) throws EDPException {
		edpSession.setDisplayMode(EDPSession.DISPLAYMODE_OPERATING);
		edpSession.enableLongLockMessages(true);
		edpSession.enableNotes(true);
		edpSession.enableTextMessages(true);
		edpSession.setFTMode(EDPFTextMode.FTEXTMODE_EXT);
		edpSession.setLineBreak(EDPSession.LINEBREAK_LF);
		edpSession.setLockBehavior(new EDPLockBehavior(EDPLockBehavior.WAIT, 1));
		edpSession.setAutoRound(true);
		edpSession.setFlag(305, true);
		edpSession.setVariableLanguage(EDPVariableLanguage.ENGLISH);
	}

	/**
	 * A megadott azonosítókkal rendelkező munkaállomást reprezentáló objektum létrehozása.
	 * @param workCenterIdNo Az Abas-beli gépcsoport hivatkozási száma.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param abasSession Az Abas-munkamenet.
	 * @return Egy új munkaállomás-objektum.
	 */
	public static WorkStation createWorkStation(String workCenterIdNo, int workStationNumber, DbContext abasSession) {
		return (new WorkStationAjoImpl(QueryUtil.getFirstByIdNo(abasSession, workCenterIdNo, WorkCenter.class), workStationNumber));
	}

	/**
	 * Adott munkalap által leírt gyártási feladatot reprezentáló objektum létrehozása.
	 * @param workSlipId A munkalap azonosítója.
	 * @param abasSession Az Abas-munkamenet.
	 * @return Egy új feladat-objektum.
	 */
	public static Task createTask(Id workSlipId, DbContext abasSession) {
		return (new TaskAjoImpl(workSlipId, abasSession));
	}

	/**
	 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
	 */
	private ObjectFactory() {
	}

}
