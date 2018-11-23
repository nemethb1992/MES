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
		 * @return A gyártási feladat végrehajtási állapota.
		 */
		Status getStatus();

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
		 * @return A gyártási feladatot követő műveletek listája.
		 */
		List<Operation> getFollowingOperations();

		/**
		 * Az adatokat tartalmazó gyorsítótár kiürítése.
		 */
		void clearCache();

	}

	/**
	 * Gyártási feladat végrehajtási állapota.
	 * @author szizo
	 */
	enum Status {

		WAITING,
		IN_PROGRESS,
		INTERRUPTED,
		SUSPENDED;

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
		 * @return A beépülési mennyiség egységének (raktáregység) neve.
		 */
		String getStockUnit();

		/**
		 * @return A tételszöveg.
		 */
		String getItemText();

	}

	/**
	 * Gyártási tevékenységet (műveletet) leíró típus.
	 * @author szizo
	 */
	interface Operation extends Serializable {

		/**
		 * @return A művelet hivatkozási száma.
		 */
		String getIdNo();

		/**
		 * @return A művelet keresőszava.
		 */
		String getSwd();

		/**
		 * @return A művelet megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		String getDescription();

		/**
		 * @return A művelethez rendelt gépcsoport hivatkozási száma.
		 */
		String getWorkCenterIdNo();

		/**
		 * @return A művelethez rendelt gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		String getWorkCenterDescription();

		/**
		 * @return A műveletsor tételszövege a gyártási listában.
		 */
		String getItemText();

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
	 * A gyártási feladat végrehajtásának félbeszakítása.
	 * @param abasConnection
	 */
	void interrupt(AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat végrehajtásának folytatása.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void resume(AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat lejelentése.
	 * @param yield Az elkészült jó mennyiség.
	 * @param scrapQuantity A keletkezett selejt mennyisége.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void postCompletionConfirmation(BigDecimal yield, BigDecimal scrapQuantity, AbasConnection<?> abasConnection);

	/**
	 * A gyártási feladat felfüggesztése.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	void suspend(AbasConnection<?> abasConnection);

}
