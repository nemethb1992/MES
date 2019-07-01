/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas;

import java.io.Serializable;

/**
 * Gépcsoport adattípusa.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public interface GenericWorkCenter<C> extends Serializable {

	/**
	 * A gépcsoport adatait leíró típus.
	 * @author szizo
	 */
	interface Details {

		/**
		 * @return A gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		String getDescription();

		/**
		 * @return A gyártási feladatok külön jóváhagyás nélkül is felfüggeszthetők?
		 */
		boolean getSuspendWithoutApproval();

	}

	/**
	 * @return A gépcsoport hivatkozási száma.
	 */
	String getIdNo();

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gépcsoport adatait leíró (gyorsítótárazott) objektum.
	 */
	Details getDetails(GenericAbasConnection<C> abasConnection);

}
