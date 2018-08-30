/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.schema.units.UnitTime;
import de.abas.erp.db.type.AbasUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Alaposztály gyártási feladatok részleteit leíró osztályok implementálásához.
 * @author szizo
 */
public abstract class TaskDetails<C> implements Task.Details {

	/**
	 * Segédosztály a mértékegységek neveinek kíírásához.
	 * @author szizo
	 */
	protected class UnitNamesRepository {

// FIXME Nyelvfüggővé kell tenni!

		/**
		 * Mértékegység -> név összerendelés.
		 */
		protected final Map<AbasUnit, String> unitNames = new HashMap<>(5);

		/**
		 * @param unit Az Abas-mértékegység.
		 * @return Az Abas-mértékegység neve az aktuálisan beállított kezelőnyelven.
		 */
		public String getUnitName(AbasUnit unit) {
			String unitName = unitNames.get(unit);
			if (null == unitName) {
				unitName = TaskDetails.this.getUnitName(unit);
				unitNames.put(unit, unitName);
			}
			return unitName;
		}

	}

	/**
	 * Segédobjektum a mértékegységek neveinek kíírásához.
	 */
	protected final UnitNamesRepository unitNamesRepository = new UnitNamesRepository();

	/**
	 * Az Abas-kapcsolat objektuma.
	 */
	protected C abasConnectionObject;

	/**
	 * A munkalap száma.
	 */
	protected String workSlipNo = null;

	/**
	 * A feladat elkezdésének (tervezett) napja.
	 */
	protected AbasDate startDate = null;

	/**
	 * A termék cikkszáma.
	 */
	protected String productIdNo = null;

	/**
	 * A termék keresőszava.
	 */
	protected String productSwd = null;

	/**
	 * A termék megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	protected String productDescription = null;

	/**
	 * A termék második megnevezése.
	 */
	protected String productDescription2 = null;

	/**
	 * A termék Felhasználás-hivatkozása.
	 */
	protected String usage = null;

	/**
	 * A kapcsolódó vevői rendeléstétel szabadszövege.
	 */
	protected String salesOrderItemText = null;

	/**
	 * A kapcsolódó vevői rendeléstétel második szabadszövege.
	 */
	protected String salesOrderItemText2 = null;

	/**
	 * A művelet hivatkozási száma.
	 */
	protected String operationIdNo = null;

	/**
	 * A művelet keresőszava.
	 */
	protected String operationSwd = null;

	/**
	 * A művelet megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	protected String operationDescription = null;

	/**
	 * A műveletfoglalás tételszövege.
	 */
	protected String operationReservationText = null;

	/**
	 * A beállítási idő.
	 */
	protected BigDecimal setupTime = null;

	/**
	 * A beállítási idő mértékegysége.
	 */
	protected String setupTimeUnit = null;

	/**
	 * A darabidő.
	 */
	protected BigDecimal unitTime = null;

	/**
	 * A darabidő mértékegysége.
	 */
	protected String unitTimeUnit = null;

	/**
	 * Hányszor kell végrehajtani a műveletet?
	 */
	protected BigDecimal numberOfExecutions = null;

	/**
	 * A nyitott mennyiség.
	 */
	protected BigDecimal outstandingQuantity = null;

	/**
	 * A mennyiségi egység (raktáregység).
	 */
	protected String stockUnit = null;

	/**
	 * Konstruktor.
	 * @param abasConnectionObject Az Abas-kapcsolat objektuma.
	 */
	protected TaskDetails(C abasConnectionObject) {
		this.abasConnectionObject = abasConnectionObject;
	}

	/**
	 * @return Az Abas-kapcsolat objektuma.
	 */
	protected C getAbasConnectionObject() {
		return abasConnectionObject;
	}

	/**
	 * @param abasConnectionObject Az Abas-kapcsolat objektuma.
	 */
	protected void setAbasConnectionObject(C abasConnectionObject) {
		this.abasConnectionObject = abasConnectionObject;
	}

	/**
	 * @param unit Az Abas-mértékegység.
	 * @return Az Abas-mértékegység neve az aktuálisan beállított kezelőnyelven.
	 */
	protected abstract String getUnitName(AbasUnit unit);

	/**
	 * A munkalaphoz kapcsolódó tagváltozók kitöltése.
	 */
	protected abstract void loadDataFromWorkSlip();

	/**
	 * A termékhez kapcsolódó tagváltozók kitöltése.
	 */
	protected abstract void loadDataFromProduct();

	/**
	 * A művelethez kapcsolódó tagváltozók kitöltése.
	 */
	protected abstract void loadDataFromOperation();

	/**
	 * A műveletfoglaláshoz kapcsolódó tagváltozók kitöltése.
	 */
	protected abstract void loadDataFromOperationReservation();

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
			return (new BigDecimal(60));
		}
		if (UnitTime.SEC == timeUnit) {
			return BigDecimal.ONE;
		}
		if (UnitTime.HUR == timeUnit) {
			return (new BigDecimal(3600));
		}
		if (UnitTime.DAY == timeUnit) {
			return (new BigDecimal(86400));
		}
		throw new RuntimeException("Ismeretlen időegység: " + timeUnit);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getWorkSlipNo()
	 */
	@Override
	public String getWorkSlipNo() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return workSlipNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getStartDate()
	 */
	@Override
	public AbasDate getStartDate() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return startDate;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductIdNo()
	 */
	@Override
	public String getProductIdNo() {
		if (null == productIdNo) {
			loadDataFromProduct();
		}
		return productIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductSwd()
	 */
	@Override
	public String getProductSwd() {
		if (null == productIdNo) {
			loadDataFromProduct();
		}
		return productSwd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductDescription()
	 */
	@Override
	public String getProductDescription() {
		if (null == productIdNo) {
			loadDataFromProduct();
		}
		return productDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductDescription2()
	 */
	@Override
	public String getProductDescription2() {
		if (null == productIdNo) {
			loadDataFromProduct();
		}
		return productDescription2;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getUsage()
	 */
	@Override
	public String getUsage() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return usage;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSalesOrderItemText()
	 */
	@Override
	public String getSalesOrderItemText() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSalesOrderItemText2()
	 */
	@Override
	public String getSalesOrderItemText2() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationIdNo()
	 */
	@Override
	public String getOperationIdNo() {
		if (null == operationIdNo) {
			loadDataFromOperation();
		}
		return operationIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationSwd()
	 */
	@Override
	public String getOperationSwd() {
		if (null == operationIdNo) {
			loadDataFromOperation();
		}
		return operationSwd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationDescription()
	 */
	@Override
	public String getOperationDescription() {
		if (null == operationIdNo) {
			loadDataFromOperation();
		}
		return operationDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationReservationText()
	 */
	@Override
	public String getOperationReservationText() {
		if (null == operationReservationText) {
			loadDataFromOperationReservation();
		}
		return operationReservationText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSetupTime()
	 */
	@Override
	public BigDecimal getSetupTime() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return setupTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSetupTimeUnit()
	 */
	@Override
	public String getSetupTimeUnit() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return setupTimeUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getUnitTime()
	 */
	@Override
	public BigDecimal getUnitTime() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return unitTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getUnitTimeUnit()
	 */
	@Override
	public String getUnitTimeUnit() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return unitTimeUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getNumberOfExecutions()
	 */
	@Override
	public BigDecimal getNumberOfExecutions() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return numberOfExecutions;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOutstandingQuantity()
	 */
	@Override
	public BigDecimal getOutstandingQuantity() {
		if (null == workSlipNo) {
			loadDataFromWorkSlip();
		}
		return outstandingQuantity;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getStockUnit()
	 */
	@Override
	public String getStockUnit() {
		if (null == productIdNo) {
			loadDataFromProduct();
		}
		return stockUnit;
	}

}
