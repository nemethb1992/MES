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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phoenix.mes.OperatingLanguage;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.Task.BomElement;

/**
 * Alaposztály gyártási feladatok részleteit leíró osztályok implementálásához.
 * @author szizo
 */
public abstract class TaskDetails<C> implements Task.Details {

	/**
	 * Az Abas-kapcsolat objektuma.
	 */
	protected C abasConnectionObject;

	/**
	 * A munkalap száma.
	 */
	protected String workSlipNo = null;

	/**
	 * A gyártási feladat elkezdésének (tervezett) napja.
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
	 * A gyártási feladathoz kapcsolódó darabjegyzék.
	 */
	protected List<BomElement> bom = null;

	/**
	 * Konstruktor.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @param abasConnectionType Az Abas-kapcsolat osztálya.
	 */
	protected TaskDetails(AbasConnection<C> abasConnection, Class<C> abasConnectionType) {
		setAbasConnectionObject(abasConnection, abasConnectionType);
	}

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @param abasConnectionType Az Abas-kapcsolat osztálya.
	 */
	protected void setAbasConnectionObject(AbasConnection<C> abasConnection, Class<C> abasConnectionType) {
		final C abasConnectionObject = AbasConnection.getConnectionObject(abasConnection, abasConnectionType);
		if (!abasConnectionObject.equals(this.abasConnectionObject)) {
			this.abasConnectionObject = abasConnectionObject;
		}
		final OperatingLanguage operatingLanguage = abasConnection.getOperatingLanguage();
		if (null == unitNamesRepository || operatingLanguage != unitNamesRepository.getLanguage()) {
			unitNamesRepository = new UnitNamesRepository(operatingLanguage);
		}

	}

	/**
	 * @return Az Abas-kapcsolat objektuma.
	 */
	protected C getAbasConnectionObject() {
		return abasConnectionObject;
	}

	/**
	 * A gyártási feladat alapadatait jelentő tagváltozók kitöltése.
	 */
	protected abstract void loadBasicData();

	/**
	 * A művelethez kapcsolódó tagváltozók kitöltése.
	 */
	protected abstract void loadOperationData();

	/**
	 * Tagváltozók kitöltése a kapcsolódó vevői megbízás alapján.
	 */
	protected abstract void loadSalesOrderData();

	/**
	 * @return A gyártási feladathoz kapcsolódó darabjegyzék.
	 */
	protected abstract List<BomElement> getBillOfMaterials();

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
			loadBasicData();
		}
		return workSlipNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getStartDate()
	 */
	@Override
	public AbasDate getStartDate() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return startDate;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductIdNo()
	 */
	@Override
	public String getProductIdNo() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return productIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductSwd()
	 */
	@Override
	public String getProductSwd() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return productSwd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductDescription()
	 */
	@Override
	public String getProductDescription() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return productDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getProductDescription2()
	 */
	@Override
	public String getProductDescription2() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return productDescription2;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getUsage()
	 */
	@Override
	public String getUsage() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return usage;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSalesOrderItemText()
	 */
	@Override
	public String getSalesOrderItemText() {
		if (null == salesOrderItemText) {
			loadSalesOrderData();
		}
		return salesOrderItemText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSalesOrderItemText2()
	 */
	@Override
	public String getSalesOrderItemText2() {
		if (null == salesOrderItemText) {
			loadSalesOrderData();
		}
		return salesOrderItemText2;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationIdNo()
	 */
	@Override
	public String getOperationIdNo() {
		if (null == operationIdNo) {
			loadOperationData();
		}
		return operationIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationSwd()
	 */
	@Override
	public String getOperationSwd() {
		if (null == operationIdNo) {
			loadOperationData();
		}
		return operationSwd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationDescription()
	 */
	@Override
	public String getOperationDescription() {
		if (null == operationIdNo) {
			loadOperationData();
		}
		return operationDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOperationReservationText()
	 */
	@Override
	public String getOperationReservationText() {
		if (null == operationIdNo) {
			loadOperationData();
		}
		return operationReservationText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSetupTime()
	 */
	@Override
	public BigDecimal getSetupTime() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return setupTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getSetupTimeUnit()
	 */
	@Override
	public String getSetupTimeUnit() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return setupTimeUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getUnitTime()
	 */
	@Override
	public BigDecimal getUnitTime() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return unitTime;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getUnitTimeUnit()
	 */
	@Override
	public String getUnitTimeUnit() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return unitTimeUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getNumberOfExecutions()
	 */
	@Override
	public BigDecimal getNumberOfExecutions() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return numberOfExecutions;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getOutstandingQuantity()
	 */
	@Override
	public BigDecimal getOutstandingQuantity() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return outstandingQuantity;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getStockUnit()
	 */
	@Override
	public String getStockUnit() {
		if (null == workSlipNo) {
			loadBasicData();
		}
		return stockUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Details#getBom()
	 */
	@Override
	public List<BomElement> getBom() {
		if (null == bom) {
			bom = getBillOfMaterials();
		}
		return bom;
	}

}
