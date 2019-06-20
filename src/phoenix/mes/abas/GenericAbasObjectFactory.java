/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Aug 21, 2018
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.Id;

import java.util.Locale;

import javax.security.auth.login.LoginException;

/**
 * Gyár típus az Abas-interfész objektumainak létrehozásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @param <A> Az Abas-kapcsolat adattípusa.
 * @author szizo
 */
public interface GenericAbasObjectFactory<C, A extends GenericAbasConnection<C>> {

	/**
	 * Kapcsolódás az Abashoz a megadott bejelentkezési adatokkal, a felhasználónál beállított kezelőnyelvvel.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return A megnyitott Abas-kapcsolat.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	default A openAbasConnection(String userName, String password, boolean testSystem) throws LoginException {
		return openAbasConnection(userName, password, null, testSystem);
	}

	/**
	 * Kapcsolódás az Abashoz a megadott bejelentkezési adatokkal.
	 * @param userName A tartományi felhasználónév.
	 * @param password A jelszó.
	 * @param locale A kezelőnyelv (null esetén a felhasználónál beállított kezelőnyelvvel történik a kapcsolódás).
	 * @param testSystem A bejelentkezés a tesztrendszerbe történik?
	 * @return A megnyitott Abas-kapcsolat.
	 * @throws LoginException Ha hiba történt a bejelentkezés során.
	 */
	A openAbasConnection(String userName, String password, Locale locale, boolean testSystem) throws LoginException;

	/**
	 * A megadott hivatkozási számmal rendelkező gépcsoportot reprezentáló objektum létrehozása.
	 * @param idNo A gépcsoport hivatkozási száma.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új gépcsoport-objektum.
	 */
	GenericWorkCenter<C> createWorkCenter(String idNo, A abasConnection);

	/**
	 * A megadott azonosítóval rendelkező gépcsoportot reprezentáló objektum létrehozása.
	 * @param id A gépcsoport Abas-beli azonosítója.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új gépcsoport-objektum.
	 */
	GenericWorkCenter<C> createWorkCenter(Id id, A abasConnection);

	/**
	 * A megadott azonosítókkal rendelkező munkaállomást reprezentáló objektum létrehozása.
	 * @param workCenter A gépcsoport.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új munkaállomás-objektum.
	 */
	default GenericWorkStation<C> createWorkStation(GenericWorkCenter<?> workCenter, int workStationNumber, A abasConnection) {
		return createWorkStation(workCenter.getId(), workStationNumber, abasConnection);
	}

	/**
	 * A megadott azonosítókkal rendelkező munkaállomást reprezentáló objektum létrehozása.
	 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új munkaállomás-objektum.
	 */
	GenericWorkStation<C> createWorkStation(Id workCenterId, int workStationNumber, A abasConnection);

	/**
	 * A megadott azonosítókkal rendelkező munkaállomást reprezentáló objektum létrehozása.
	 * @param workCenterIdNo Az Abas-beli gépcsoport hivatkozási száma.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új munkaállomás-objektum.
	 */
	GenericWorkStation<C> createWorkStation(String workCenterIdNo, int workStationNumber, A abasConnection);

	/**
	 * Adott munkalap által leírt gyártási feladatot reprezentáló objektum létrehozása.
	 * @param workSlipId A munkalap azonosítója.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Egy új feladat-objektum.
	 */
	GenericTask<C> createTask(Id workSlipId, A abasConnection);

}
