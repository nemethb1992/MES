/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 7, 2018
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.db.DbContext;

import java.io.Serializable;

/**
 * Gyártási feladat típusa.
 * @author szizo
 */
public interface Task extends Serializable {

	/**
	 * Gyártási feladat részleteit leíró típus.
	 * @author szizo
	 */
	interface Details {
		
		String getProductIdNo();
		
		String getProductSwd();

		String getProductDescription1();

	}

	/**
	 * @return A feladathoz tartozó munkalap azonosítója.
	 */
	Id getWorkSlipId();

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A gyártási feladat részleteit leíró objektum.
	 */
	Details getDetails(DbContext abasSession);

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A feladat elkezdésének (tervezett) napja.
	 */
	AbasDate getStartDate(DbContext abasSession);

}
