/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.Id;

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

	}

	/**
	 * @return A gépcsoport Abas-beli azonosítója.
	 */
	Id getId();

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gépcsoport adatait leíró (gyorsítótárazott) objektum.
	 */
	Details getDetails(GenericAbasConnection<C> abasConnection);

}
