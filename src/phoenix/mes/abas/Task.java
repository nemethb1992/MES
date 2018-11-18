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
import java.util.List;

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
		 * @return A gyártási feladat elkezdésének (tervezett) napja.
		 */
		AbasDate getStartDate();

		/**
		 * @return A gyártási feladat fel van függesztve?
		 */
		boolean isSuspendedTask();

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

		/**
		 * @return A kalkulált gyártási átfutási idő (normaidő) munkaórában.
		 */
		BigDecimal getCalculatedProductionTime();

		/**
		 * @return A gyártási feladathoz kapcsolódó darabjegyzék.
		 */
		List<BomElement> getBom();

		/**
		 * Az adatokat tartalmazó gyorsítótár kiürítése.
		 */
		void clearCache();

	}

	/**
	 * Gyártási feladathoz kapcsolódó darabjegyzék elemeit leíró típus.
	 * @author szizo
	 */
	interface BomElement extends Serializable {

		/**
		 * @return A beépülő cikk hivatkozási száma.
		 */
		String getIdNo();

		/**
		 * @return A beépülő cikk keresőszava.
		 */
		String getSwd();

		/**
		 * @return A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		String getDescription();

		/**
		 * @return A beépülő cikk második megnevezése.
		 */
		String getDescription2();

		/**
		 * @return A beépülési mennyiség (egy késztermékre vonatkozóan).
		 */
		BigDecimal getQuantityPerProduct();

		/**
		 * @return A mennyiségi egység (raktáregység).
		 */
		String getStockUnit();

	}

	/**
	 * @return A gyártási feladathoz tartozó munkalap azonosítója.
	 */
	Id getWorkSlipId();

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gyártási feladat részleteit leíró (gyorsítótárazott) objektum.
	 * @throws IllegalArgumentException Ha az Abas-kapcsolat nem megfelelő típusú.
	 */
	Details getDetails(AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gyártási feladat végrehajtása folyamatban van?
	 */
	boolean isInProgress(AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat beütemezése egy konkrét munkaállomásra.
	 * @param workStation A munkaállomás.
	 * @param precedingWorkSlipId A munkaállomáson közvetlenül ez előtt a gyártási feladat előtt végrehajtandó munkalap azonosítója (Id.NULLREF, ha ez a gyártási feladat az első a végrehajtási listában).
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void schedule(WorkStation workStation, Id precedingWorkSlipId, AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat beütemezésének visszavonása.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void unSchedule(AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat felfüggesztése.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void suspend(AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat lejelentése.
	 * @param yield Az elkészült jó mennyiség.
	 * @param scrapQuantity A keletkezett selejt mennyisége.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void postCompletionConfirmation(BigDecimal yield, BigDecimal scrapQuantity, AbasConnection<?> abasConnection);

}
