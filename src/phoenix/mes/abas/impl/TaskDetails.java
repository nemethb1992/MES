/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.schema.units.UnitTime;
import de.abas.erp.db.type.AbasUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import phoenix.mes.abas.GenericAbasConnection;
import phoenix.mes.abas.GenericTask;
import phoenix.mes.abas.GenericTask.BomElement;
import phoenix.mes.abas.GenericTask.Operation;
import phoenix.mes.abas.GenericTask.Status;

/**
 * Alaposztály gyártási feladatok részleteit leíró osztályok implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class TaskDetails<C> extends LanguageDependentCache<C> implements GenericTask.Details {

	/**
	 * Konstans, hatékonysági okból gyorsítótárazva.
	 */
	public static final BigDecimal BIG_DECIMAL_60 = new BigDecimal(60);

	/**
	 * Segédosztály a gyártási feladat alapadatainak tárolásához.
	 * @author szizo
	 */
	public class BasicData {

		/**
		 * A munkalap száma.
		 */
		public String workSlipNo;

		/**
		 * A gyártási feladat elkezdésének (tervezett) napja.
		 */
		public AbasDate startDate;

		/**
		 * A gyártási feladat végrehajtási állapota.
		 */
		public Status status;

		/**
		 * A termék cikkszáma.
		 */
		public String productIdNo;

		/**
		 * A termék keresőszava.
		 */
		public String productSwd;

		/**
		 * A termék megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		public String productDescription;

		/**
		 * A termék második megnevezése.
		 */
		public String productDescription2;

		/**
		 * A termék Felhasználás-hivatkozása.
		 */
		public String usage;

		/**
		 * A beállítási idő.
		 */
		public BigDecimal setupTime;

		/**
		 * A beállítási idő mértékegysége.
		 */
		public String setupTimeUnit;

		/**
		 * A darabidő.
		 */
		public BigDecimal unitTime;

		/**
		 * A darabidő mértékegysége.
		 */
		public String unitTimeUnit;

		/**
		 * Hányszor kell végrehajtani a műveletet?
		 */
		public BigDecimal numberOfExecutions;

		/**
		 * A nyitott mennyiség.
		 */
		public BigDecimal outstandingQuantity;

		/**
		 * A mennyiségi egység (raktáregység).
		 */
		public String stockUnit;

		/**
		 * A kalkulált gyártási átfutási idő (normaidő) munkaórában.
		 */
		public BigDecimal calculatedProductionTime;

	}

	/**
	 * Segédosztály a művelet adatainak tárolásához.
	 * @author szizo
	 */
	public class OperationData {

		/**
		 * A művelet hivatkozási száma.
		 */
		public String operationIdNo;

		/**
		 * A művelet keresőszava.
		 */
		public String operationSwd;

		/**
		 * A művelet megnevezése az aktuálisan beállított kezelőnyelven.
		 */
		public String operationDescription;

		/**
		 * A műveletfoglalás tételszövege.
		 */
		public String operationReservationText;

		/**
		 * A nyitott lejelentési mennyiség.
		 */
		public BigDecimal outstandingConfirmationQuantity;

	}

	/**
	 * Segédosztály a gyártási feladathoz kapcsolódó vevői megbízás adatainak tárolásához.
	 * @author szizo
	 */
	public class SalesOrderData {

		/**
		 * A kapcsolódó vevői rendeléstétel szabadszövege.
		 */
		public String salesOrderItemText;

		/**
		 * A kapcsolódó vevői rendeléstétel második szabadszövege.
		 */
		public String salesOrderItemText2;

	}

	/**
	 * Segédosztály a gyártási feladat gyártási listája adatainak tárolásához.
	 * @author szizo
	 */
	public class ProductionListData {

		/**
		 * A megelőző munkalap lejelentett mennyisége (null, ha ez az első gyártási feladat).
		 */
		public BigDecimal yieldOfPrecedingWorkSlip;

		/**
		 * A gyártási feladathoz kapcsolódó darabjegyzék.
		 */
		public List<BomElement> bom;

	}

	/**
	 * Az Abas-kapcsolat objektuma.
	 */
	protected C abasConnectionObject;

	/**
	 * A gyártási feladat alapadatait gyorsítótárazó objektum.
	 */
	protected BasicData basicData = null;

	/**
	 * A művelet adatait gyorsítótárazó objektum.
	 */
	protected OperationData operationData = null;

	/**
	 * A gyártási feladathoz kapcsolódó vevői megbízás adatait gyorsítótárazó objektum.
	 */
	protected SalesOrderData salesOrderData = null;

	/**
	 * A gyártási feladat gyártási listájának adatait gyorsítótárazó objektum.
	 */
	protected ProductionListData productionListData = null;

	/**
	 * A gyártási feladatot követő műveletek listáját gyorsítótárazó objektum.
	 */
	protected List<Operation> followingOperations = null;

	/**
	 * Konstruktor.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	protected TaskDetails(GenericAbasConnection<C> abasConnection) {
		super(abasConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.impl.LanguageDependentCache#resetCacheIfLanguageDoesNotMatch(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	protected void resetCacheIfLanguageDoesNotMatch(GenericAbasConnection<C> abasConnection) {
		super.resetCacheIfLanguageDoesNotMatch(abasConnection);
		abasConnectionObject = abasConnection.getConnectionObject();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.impl.LanguageDependentCache#resetCache(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	protected void resetCache(GenericAbasConnection<C> abasConnection) {
		resetCache();
	}

	/**
	 * A gyorsítótár alaphelyzetbe hozása.
	 */
	protected void resetCache() {
		clearBasicDataCache();
		clearOperationDataCache();
		clearProductionListDataCache();
		clearFollowingOperationsCache();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#clearCache()
	 */
	@Override
	public void clearCache() {
		resetCache();
		clearSalesOrderDataCache();
	}

	/**
	 * Adott műveleti idő bruttósítása.
	 * @param netTime A nettó műveleti idő.
	 * @param timeUnit A műveleti idő időegysége.
	 * @param secondsPerTimeUnit Az időegység bruttósítva hány másodpercből áll (szorzótényező)?
	 * @return A műveleti idő, a megadott szorzótényezőnek megfelelően bruttósítva.
	 */
	protected BigDecimal calculateGrossTime(BigDecimal netTime, AbasUnit timeUnit, BigDecimal secondsPerTimeUnit) {
		return netTime.multiply(secondsPerTimeUnit).divide(convertToSeconds(timeUnit), RoundingMode.HALF_UP);
	}

	/**
	 * A megadott időegység átváltása másodpercbe.
	 * @param timeUnit Az időegység.
	 * @return Az időegységnek megfelelő másodpercek száma.
	 */
	protected BigDecimal convertToSeconds(AbasUnit timeUnit) {
		if (UnitTime.MIN == timeUnit) {
			return BIG_DECIMAL_60;
		}
		if (UnitTime.SEC == timeUnit) {
			return BigDecimal.ONE;
		}
		if (UnitTime.HUR == timeUnit) {
			return new BigDecimal(3600);
		}
		if (UnitTime.DAY == timeUnit) {
			return new BigDecimal(86400);
		}
		throw new RuntimeException("Ismeretlen időegység: " + timeUnit);
	}

	/**
	 * @return A gyártási feladat alapadatait gyorsítótárazó objektum.
	 */
	protected BasicData getBasicData() {
		if (null == basicData) {
			basicData = newBasicData();
		}
		return basicData;
	}

	/**
	 * @return A gyártási feladat alapadatait gyorsítótárazó, újonnan létrehozott objektum.
	 */
	protected abstract BasicData newBasicData();

	/**
	 * A gyártási feladat alapadatait tartalmazó gyorsítótár kiürítése.
	 */
	public void clearBasicDataCache() {
		basicData = null;
	}

	/**
	 * @return A művelet adatait gyorsítótárazó objektum.
	 */
	protected OperationData getOperationData() {
		if (null == operationData) {
			operationData = newOperationData();
		}
		return operationData;
	}

	/**
	 * @return A művelet adatait gyorsítótárazó, újonnan létrehozott objektum.
	 */
	protected abstract OperationData newOperationData();

	/**
	 * A művelet adatait tartalmazó gyorsítótár kiürítése.
	 */
	public void clearOperationDataCache() {
		operationData = null;
	}

	/**
	 * @return A gyártási feladathoz kapcsolódó vevői megbízás adatait gyorsítótárazó objektum.
	 */
	protected SalesOrderData getSalesOrderData() {
		if (null == salesOrderData) {
			salesOrderData = newSalesOrderData();
		}
		return salesOrderData;
	}

	/**
	 * @return A gyártási feladathoz kapcsolódó vevői megbízás adatait gyorsítótárazó, újonnan létrehozott objektum.
	 */
	protected abstract SalesOrderData newSalesOrderData();

	/**
	 * A gyártási feladathoz kapcsolódó vevői megbízás adatait tartalmazó gyorsítótár kiürítése.
	 */
	public void clearSalesOrderDataCache() {
		salesOrderData = null;
	}

	/**
	 * @return A gyártási feladat gyártási listájának adatait gyorsítótárazó objektum.
	 */
	protected ProductionListData getProductionListData() {
		if (null == productionListData) {
			productionListData = newProductionListData();
		}
		return productionListData;
	}

	/**
	 * @return A gyártási feladat gyártási listájának adatait gyorsítótárazó, újonnan létrehozott objektum.
	 */
	protected abstract ProductionListData newProductionListData();

	/**
	 * A gyártási feladat gyártási listájának adatait tartalmazó gyorsítótár kiürítése.
	 */
	public void clearProductionListDataCache() {
		productionListData = null;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getWorkSlipNo()
	 */
	@Override
	public String getWorkSlipNo() {
		return getBasicData().workSlipNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getStartDate()
	 */
	@Override
	public AbasDate getStartDate() {
		return getBasicData().startDate;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getStatus()
	 */
	@Override
	public Status getStatus() {
		Status status = getBasicData().status;
		if (null == status) {
			clearBasicDataCache();
			status = getBasicData().status;
		}
		return status;
	}

	/**
	 * A gyártási feladat végrehajtási állapotát tartalmazó gyorsítótár kiürítése.
	 */
	public void clearStatusCache() {
		if (null != basicData) {
			basicData.status = null;
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getProductIdNo()
	 */
	@Override
	public String getProductIdNo() {
		return getBasicData().productIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getProductSwd()
	 */
	@Override
	public String getProductSwd() {
		return getBasicData().productSwd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getProductDescription()
	 */
	@Override
	public String getProductDescription() {
		return getBasicData().productDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getProductDescription2()
	 */
	@Override
	public String getProductDescription2() {
		return getBasicData().productDescription2;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getUsage()
	 */
	@Override
	public String getUsage() {
		return getBasicData().usage;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getSalesOrderItemText()
	 */
	@Override
	public String getSalesOrderItemText() {
		return getSalesOrderData().salesOrderItemText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getSalesOrderItemText2()
	 */
	@Override
	public String getSalesOrderItemText2() {
		return getSalesOrderData().salesOrderItemText2;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getOperationIdNo()
	 */
	@Override
	public String getOperationIdNo() {
		return getOperationData().operationIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getOperationSwd()
	 */
	@Override
	public String getOperationSwd() {
		return getOperationData().operationSwd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getOperationDescription()
	 */
	@Override
	public String getOperationDescription() {
		return getOperationData().operationDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getOperationReservationText()
	 */
	@Override
	public String getOperationReservationText() {
		return getOperationData().operationReservationText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getSetupTime()
	 */
	@Override
	public BigDecimal getSetupTime() {
		return getBasicData().setupTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getSetupTimeUnit()
	 */
	@Override
	public String getSetupTimeUnit() {
		return getBasicData().setupTimeUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getUnitTime()
	 */
	@Override
	public BigDecimal getUnitTime() {
		return getBasicData().unitTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getUnitTimeUnit()
	 */
	@Override
	public String getUnitTimeUnit() {
		return getBasicData().unitTimeUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getNumberOfExecutions()
	 */
	@Override
	public BigDecimal getNumberOfExecutions() {
		return getBasicData().numberOfExecutions;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getYieldOfPrecedingWorkSlip()
	 */
	@Override
	public BigDecimal getYieldOfPrecedingWorkSlip() {
		return getProductionListData().yieldOfPrecedingWorkSlip;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getOutstandingQuantity()
	 */
	@Override
	public BigDecimal getOutstandingQuantity() {
		return getBasicData().outstandingQuantity;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getOutstandingConfirmationQuantity()
	 */
	@Override
	public BigDecimal getOutstandingConfirmationQuantity() {
		return getOperationData().outstandingConfirmationQuantity;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getStockUnit()
	 */
	@Override
	public String getStockUnit() {
		return getBasicData().stockUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getCalculatedProductionTime()
	 */
	@Override
	public BigDecimal getCalculatedProductionTime() {
		return getBasicData().calculatedProductionTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getBom()
	 */
	@Override
	public List<BomElement> getBom() {
		return getProductionListData().bom;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Details#getFollowingOperations()
	 */
	@Override
	public List<Operation> getFollowingOperations() {
		if (null == followingOperations) {
			followingOperations = newFollowingOperations();
		}
		return followingOperations;
	}

	/**
	 * @return A gyártási feladatot követő műveletek listáját gyorsítótárazó, újonnan létrehozott objektum.
	 */
	protected abstract List<Operation> newFollowingOperations();

	/**
	 * A gyártási feladatot követő műveletek listáját tartalmazó gyorsítótár kiürítése.
	 */
	public void clearFollowingOperationsCache() {
		followingOperations = null;
	}

}
