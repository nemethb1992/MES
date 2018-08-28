/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Aug 21, 2018
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumLanguageCode;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.edp.EdpObjectFactory;

/**
 * Gyár típus az Abas-interfész objektumainak létrehozásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public interface AbasObjectFactory<C> {

	/**
	 * Az Abas-interfész alapértelmezett gyár objektuma.
	 */
	AbasObjectFactory<EDPSession> INSTANCE = new EdpObjectFactory();

	/**
	 * Kapcsolódás az Abashoz a megadott bejelentkezési adatokkal, a felhasználónál beállított kezelőnyelvvel.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return A megnyitott Abas-kapcsolat.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	default AbasConnection<C> openAbasConnection(String userName, String password, boolean testSystem) throws LoginException {
		return openAbasConnection(userName, password, null, testSystem);
	}

	/**
	 * Kapcsolódás az Abashoz a megadott bejelentkezési adatokkal.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param operatingLanguage A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelv).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return A megnyitott Abas-kapcsolat.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	AbasConnection<C> openAbasConnection(String userName, String password, EnumLanguageCode operatingLanguage, boolean testSystem) throws LoginException;

	/**
	 * A megadott azonosítókkal rendelkező munkaállomást reprezentáló objektum létrehozása.
	 * @param workCenterIdNo Az Abas-beli gépcsoport hivatkozási száma.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új munkaállomás-objektum.
	 */
	WorkStation createWorkStation(String workCenterIdNo, int workStationNumber, AbasConnection<C> abasConnection);

	/**
	 * Adott munkalap által leírt gyártási feladatot reprezentáló objektum létrehozása.
	 * @param workSlipId A munkalap azonosítója.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új feladat-objektum.
	 */
	Task createTask(Id workSlipId, AbasConnection<C> abasConnection);

}
