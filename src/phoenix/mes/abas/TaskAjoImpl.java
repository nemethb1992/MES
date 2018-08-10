/*
 * Domain típusok könyvtára.
 *
 * Created on Oct 6, 2017
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumFilingModeExtended;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.DatabaseIterator;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.FieldSet;
import de.abas.erp.db.Query;
import de.abas.erp.db.field.TypedField;
import de.abas.erp.db.schema.company.TableOfUnits;
import de.abas.erp.db.schema.operation.Operation;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.SelectablePart;
import de.abas.erp.db.schema.purchasing.Reservations;
import de.abas.erp.db.schema.purchasing.SelectablePurchasing;
import de.abas.erp.db.schema.units.UnitTime;
import de.abas.erp.db.schema.workorder.SelectableWorkorder;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.type.AbasUnit;
import de.abas.erp.db.util.QueryUtil;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Gyártási feladat osztálya, AJO-ban implementálva.
 * @author szizo
 */
public class TaskAjoImpl implements Task {

	/**
	 * Gyártási feladat részleteit leíró osztály, AJO-ban implementálva.
	 * @author szizo
	 */
	protected class DetailsAjoImpl implements Details {

		/**
		 * Segédosztály a mértékegységek neveinek kíírásához.
		 * @author szizo
		 */
		protected class UnitNamesRepository {

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
					final SelectionBuilder<TableOfUnits.Row> criteria = SelectionBuilder.create(TableOfUnits.Row.class);
					criteria.setFilingMode(EnumFilingModeExtended.Active);
					criteria.add(Conditions.eq(TableOfUnits.Row.META.unitCode, unit));
					unitName = QueryUtil.getFirst(abasSession, criteria.build()).getUnitOperLang();
					unitNames.put(unit, unitName);
				}
				return unitName;
			}

		}

		/**
		 * Az Abas-munkamenet.
		 */
		protected final DbContext abasSession;

		/**
		 * Segédobjektum a mértékegységek neveinek kíírásához.
		 */
		protected final UnitNamesRepository unitNamesRepository = new UnitNamesRepository();

		/**
		 * A munkalap száma.
		 */
		protected String workSlipNo = null;

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
		 * @param abasSession Az Abas-munkamenet.
		 */
		protected DetailsAjoImpl(DbContext abasSession) {
			this.abasSession = abasSession;
		}

		/**
		 * A munkalaphoz kapcsolódó tagváltozók kitöltése.
		 */
		protected void loadDataFromWorkSlip() {
			final WorkOrders workSlip = getWorkSlip(abasSession);
			workSlipNo = workSlip.getIdno();
			usage = workSlip.getUsage();
			final AbasUnit setupTimeAbasUnit = workSlip.getSetupTimeUnit();
			setupTime = calculateGrossTime(workSlip.getSetupTime(), setupTimeAbasUnit, workSlip.getSetupTimeSec());
			setupTimeUnit = unitNamesRepository.getUnitName(setupTimeAbasUnit);
			final AbasUnit unitTimeAbasUnit = workSlip.getTimeUnit();
			unitTime = calculateGrossTime(workSlip.getTimeLimUnit(), unitTimeAbasUnit, workSlip.getUnitTimeSec());
			unitTimeUnit = unitNamesRepository.getUnitName(unitTimeAbasUnit);
			numberOfExecutions = workSlip.getElemQty();
			outstandingQuantity = workSlip.getUnitQty();
			loadDataFromProduct(workSlip.getProduct());
			loadDataFromOperation(workSlip.getSeriesOfOperations());
			loadDataFromOperationReservation(workSlip.getLastReservation());
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

		/**
		 * A termékhez kapcsolódó tagváltozók kitöltése.
		 * @param productId A termék azonosítója.
		 */
		protected void loadDataFromProduct(SelectablePart productId) {
			final SelectionBuilder<SelectablePart> criteria = SelectionBuilder.create(SelectablePart.class);
			criteria.add(Conditions.eq(Product.META.id.getName(), productId.toString()));
			final Query<SelectablePart> query = abasSession.createQuery(criteria.build());
			query.setFields(FieldSet.of(Product.META.idno.getName(),
					Product.META.swd.getName(),
					Product.META.descrOperLang.getName(),
					Product.META.yname2.getName(),
					Product.META.SU.getName()));
			query.setLazyLoad(false);
			final DatabaseIterator<SelectablePart> productIterator = query.iterator();
			final SelectablePart product = productIterator.next();
			productIterator.close();
			productIdNo = product.getIdno();
			productSwd = product.getSwd();
			productDescription = product.getString(Product.META.descrOperLang);
			if (product instanceof Product) {
				productDescription2 = ((Product)product).getYname2();
			}
			stockUnit = unitNamesRepository.getUnitName(product.getUnit(Product.META.SU.getName()));
		}

		/**
		 * A művelethez kapcsolódó tagváltozók kitöltése.
		 * @param operationId A művelet azonosítója.
		 */
		protected void loadDataFromOperation(Operation operationId) {
			final SelectionBuilder<Operation> criteria = SelectionBuilder.create(Operation.class);
			criteria.add(Conditions.eq(Operation.META.id, operationId));
			final Query<Operation> query = abasSession.createQuery(criteria.build());
			final Set<TypedField<? super Operation>> fields = new HashSet<>(4);
			fields.add(Operation.META.idno);
			fields.add(Operation.META.swd);
			fields.add(Operation.META.descrOperLang);
			query.setFields(FieldSet.of(fields));
			query.setLazyLoad(false);
			final DatabaseIterator<Operation> operationIterator = query.iterator();
			final Operation operation = operationIterator.next();
			operationIterator.close();
			operationIdNo = operation.getIdno();
			operationSwd = operation.getSwd();
			operationDescription = operation.getDescrOperLang();
		}

		/**
		 * A műveletfoglaláshoz kapcsolódó tagváltozók kitöltése.
		 * @param operationReservationId A műveletfoglalás azonosítója.
		 */
		protected void loadDataFromOperationReservation(SelectablePurchasing operationReservationId) {
			final SelectionBuilder<SelectablePurchasing> criteria = SelectionBuilder.create(SelectablePurchasing.class);
			criteria.add(Conditions.eq(Reservations.META.id.getName(), operationReservationId.toString()));
			final Query<SelectablePurchasing> query = abasSession.createQuery(criteria.build());
			query.setFields(FieldSet.of(Reservations.META.itemText.getName()));
			query.setLazyLoad(false);
			final DatabaseIterator<SelectablePurchasing> operationReservationIterator = query.iterator();
			final Reservations operationReservation = (Reservations)operationReservationIterator.next();
			operationReservationIterator.close();
			operationReservationText = operationReservation.getItemText();
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
		 * @see phoenix.mes.abas.Task.Details#getProductIdNo()
		 */
		@Override
		public String getProductIdNo() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return productIdNo;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.Task.Details#getProductSwd()
		 */
		@Override
		public String getProductSwd() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return productSwd;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.Task.Details#getProductDescription()
		 */
		@Override
		public String getProductDescription() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return productDescription;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.Task.Details#getProductDescription2()
		 */
		@Override
		public String getProductDescription2() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
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
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return operationIdNo;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.Task.Details#getOperationSwd()
		 */
		@Override
		public String getOperationSwd() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return operationSwd;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.Task.Details#getOperationDescription()
		 */
		@Override
		public String getOperationDescription() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return operationDescription;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.Task.Details#getOperationReservationText()
		 */
		@Override
		public String getOperationReservationText() {
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
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
			if (null == workSlipNo) {
				loadDataFromWorkSlip();
			}
			return stockUnit;
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 2168632003172665470L;

	/**
	 * A feladathoz tartozó munkalap azonosítója.
	 */
	protected final Id workSlipId;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy munkalapot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param abasSession Az Abas-munkamenet.
	 * @return Abas-munkalapazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező munkalap azonosítója.
	 */
	public static Id workSlipIdFilter(Id abasObjectId, DbContext abasSession) {
		if (9 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		final WorkOrders workSlip;
		try {
			workSlip = abasSession.load(WorkOrders.class, abasObjectId);
		} catch (Exception e) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId, e);
		}
		if (EnumWorkOrderType.WorkSlips != workSlip.getType()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		return abasObjectId;
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 * @param abasSession Az Abas-munkamenet.
	 */
	public TaskAjoImpl(Id workSlipId, DbContext abasSession) {
		this(workSlipIdFilter(workSlipId, abasSession));
	}

	/**
	 * Konstruktor.
	 * @param workSlip A feladathoz tartozó munkalap.
	 */
	TaskAjoImpl(SelectableWorkorder workSlip) {
		this(new IdImpl(workSlip.getRawString(WorkOrders.META.id.getName())));
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 */
	private TaskAjoImpl(Id workSlipId) {
		this.workSlipId = workSlipId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Task)) {
			return false;
		}
		return workSlipId.equals(((Task)object).getWorkSlipId());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return workSlipId.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskAjoImpl [workSlipId=" + workSlipId + "]";
	}

	/**
	 * Az objektum üres tagváltozókkal nem olvasható vissza.
	 * @throws ObjectStreamException Mindig.
	 * @see java.io.Serializable
	 */
	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("");
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getWorkSlipId()
	 */
	@Override
	public Id getWorkSlipId() {
		return workSlipId;
	}

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A feladathoz tartozó munkalap.
	 */
	public WorkOrders getWorkSlip(DbContext abasSession) {
		return abasSession.load(WorkOrders.class, workSlipId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getStartDate()
	 */
	@Override
	public AbasDate getStartDate(DbContext abasSession) {
		return getWorkSlip(abasSession).getStartDateDay();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getDetails(de.abas.erp.db.DbContext)
	 */
	@Override
	public Details getDetails(DbContext abasSession) {
		return (new DetailsAjoImpl(abasSession));
	}

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
