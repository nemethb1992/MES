/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 7, 2018
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;

import java.io.Serializable;
import java.math.BigDecimal;

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

		/**
		 * @return A munkalap száma.
		 */
		String getWorkSlipNo();

		/**
		 * @return A feladat elkezdésének (tervezett) napja.
		 */
		AbasDate getStartDate();

		/**
		 * @return A termék cikkszáma.
		 */
		String getProductIdNo();

		/**
		 * @return A termék keresőszava.
		 */
		String getProductSwd();

		/**
		 * @return A termék megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		String getProductDescription();

		/**
		 * @return A termék második megnevezése.
		 */
		String getProductDescription2();

		/**
		 * @return A termék Felhasználás-hivatkozása.
		 */
		String getUsage();

		/**
		 * @return A kapcsolódó vevői rendeléstétel szabadszövege.
		 */
		String getSalesOrderItemText();

		/**
		 * @return A kapcsolódó vevői rendeléstétel második szabadszövege.
		 */
		String getSalesOrderItemText2();

		/**
		 * @return A művelet hivatkozási száma.
		 */
		String getOperationIdNo();

		/**
		 * @return A művelet keresőszava.
		 */
		String getOperationSwd();

		/**
		 * @return A művelet megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		String getOperationDescription();

		/**
		 * @return A műveletfoglalás tételszövege.
		 */
		String getOperationReservationText();

		/**
		 * @return A beállítási idő.
		 */
		BigDecimal getSetupTime();

		/**
		 * @return A beállítási idő mértékegysége.
		 */
		String getSetupTimeUnit();

		/**
		 * @return A darabidő.
		 */
		BigDecimal getUnitTime();

		/**
		 * @return A darabidő mértékegysége.
		 */
		String getUnitTimeUnit();

		/**
		 * @return Hányszor kell végrehajtani a műveletet?
		 */
		BigDecimal getNumberOfExecutions();

		/**
		 * @return A nyitott mennyiség.
		 */
		BigDecimal getOutstandingQuantity();

		/**
		 * @return A mennyiségi egység (raktáregység).
		 */
		String getStockUnit();

	}

	/**
	 * @return A feladathoz tartozó munkalap azonosítója.
	 */
	Id getWorkSlipId();

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A feladat elkezdésének (tervezett) napja.
	 */
	AbasDate getStartDate(AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gyártási feladat részleteit leíró objektum.
	 */
	Details getDetails(AbasConnection<?> abasConnection);

/*
Feladat indítása
 Bemenet: munkamenet
 Ha van ysts időbélyeg: már fut, hiba
 ysts = G|sekundenz, yszeich = G|bzeichen

Lejelentés
 Bemenet: mennyiség, dolgozó (null is lehet), munkamenet
 Ha nincs ysts időbélyeg: nincs elindítva, hiba
 Anyagkivét?
 ysts = ""

Feladat megszakítása
 Bemenet: munkamenet
 ysts = "", yszeich = G|bzeichen
*/
}
