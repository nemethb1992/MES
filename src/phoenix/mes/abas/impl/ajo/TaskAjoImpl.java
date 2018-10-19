/*
 * Domain típusok könyvtára.
 *
 * Created on Oct 6, 2017
 */

package phoenix.mes.abas.impl.ajo;

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
import de.abas.erp.db.schema.workorder.SelectableWorkorder;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.type.AbasUnit;
import de.abas.erp.db.util.QueryUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import phoenix.mes.OperatingLanguage;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.impl.TaskImpl;
import phoenix.mes.abas.impl.TaskDetails;

/**
 * Gyártási feladat osztálya, AJO-ban implementálva.
 * @author szizo
 */
public class TaskAjoImpl extends TaskImpl<DbContext> {

	/**
	 * Gyártási feladat részleteit leíró osztály, AJO-ban implementálva.
	 * @author szizo
	 */
	protected class DetailsAjoImpl extends TaskDetails<DbContext> {

		/**
		 * Segédosztály a mértékegységek neveinek kíírásához.
		 * @author szizo
		 */
		protected class UnitNamesRepository {

			/**
			 * A gyűjtemény nyelvének kódja.
			 */
			protected final String languageCode;

			/**
			 * Mértékegység -> név összerendelés.
			 */
			protected final Map<AbasUnit, String> unitNames = new HashMap<>(5);

			/**
			 * Konstruktor.
			 * @param languageCode A gyűjtemény nyelvének kódja.
			 */
			protected UnitNamesRepository(String languageCode) {
				this.languageCode = languageCode;
			}

			/**
			 * @param unit Az Abas-mértékegység.
			 * @return Az Abas-mértékegység neve.
			 */
			public String getUnitName(AbasUnit unit) {
				String unitName = unitNames.get(unit);
				if (null == unitName) {
					unitName = DetailsAjoImpl.this.getUnitName(unit);
					unitNames.put(unit, unitName);
				}
				return unitName;
			}

			/**
			 * @return A gyűjtemény nyelvének kódja.
			 */
			public String getLanguageCode() {
				return languageCode;
			}

		}

		/**
		 * Segédobjektum a mértékegységek neveinek kíírásához.
		 */
		protected UnitNamesRepository unitNamesRepository;

		/**
		 * A termék.
		 */
		protected SelectablePart product = null;

		/**
		 * A művelet.
		 */
		protected Operation operation = null;

		/**
		 * A műveletfoglalás.
		 */
		protected SelectablePurchasing operationReservation = null;

		/**
		 * Konstruktor.
		 * @param abasConnection Az Abas-kapcsolat.
		 */
		protected DetailsAjoImpl(AbasConnection<DbContext> abasConnection) {
			super(abasConnection, abasConnectionType);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#setAbasConnectionObject(phoenix.mes.abas.AbasConnection, java.lang.Class)
		 */
		@Override
		protected void setAbasConnectionObject(AbasConnection<DbContext> abasConnection, Class<DbContext> abasConnectionType) {
			super.setAbasConnectionObject(abasConnection, abasConnectionType);
			final String operatingLanguageCode = abasConnection.getOperatingLanguageCode();
			if (null == unitNamesRepository || !operatingLanguageCode.equals(unitNamesRepository.getLanguageCode())) {
				unitNamesRepository = new UnitNamesRepository(operatingLanguageCode);
			}
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadBasicData()
		 */
		@Override
		protected void loadBasicData() {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadOperationData()
		 */
		@Override
		protected void loadOperationData() {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadSalesOrderData()
		 */
		@Override
		protected void loadSalesOrderData() {
			// TODO Auto-generated method stub

		}

		/**
		 * @param unit Az Abas-mértékegység.
		 * @return Az Abas-mértékegység neve az aktuálisan beállított kezelőnyelven.
		 */
		protected String getUnitName(AbasUnit unit) {
			final SelectionBuilder<TableOfUnits.Row> criteria = SelectionBuilder.create(TableOfUnits.Row.class);
			criteria.setFilingMode(EnumFilingModeExtended.Active);
			criteria.add(Conditions.eq(TableOfUnits.Row.META.unitCode, unit));
			return QueryUtil.getFirst(abasConnectionObject, criteria.build()).getUnitOperLang();
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadDataFromWorkSlip()
		 */
		@Override
		protected void loadDataFromWorkSlip() {
			final WorkOrders workSlip = getWorkSlip(abasConnectionObject);
			workSlipNo = workSlip.getIdno();
			startDate = workSlip.getStartDateDay();
			usage = workSlip.getUsage();
			final AbasUnit setupTimeAbasUnit = workSlip.getSetupTimeUnit();
			setupTime = calculateGrossTime(workSlip.getSetupTime(), setupTimeAbasUnit, workSlip.getSetupTimeSec());
			setupTimeUnit = unitNamesRepository.getUnitName(setupTimeAbasUnit);
			final AbasUnit unitTimeAbasUnit = workSlip.getTimeUnit();
			unitTime = calculateGrossTime(workSlip.getTimeLimUnit(), unitTimeAbasUnit, workSlip.getUnitTimeSec());
			unitTimeUnit = unitNamesRepository.getUnitName(unitTimeAbasUnit);
			numberOfExecutions = workSlip.getElemQty();
			outstandingQuantity = workSlip.getUnitQty();
			product = workSlip.getProduct();
			operation = workSlip.getSeriesOfOperations();
			operationReservation = workSlip.getLastReservation();
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadDataFromProduct()
		 */
		@Override
		protected void loadDataFromProduct() {
			if (null == product) {
				loadDataFromWorkSlip();
				if (null == product) {
					return;
				}
			}
			final SelectionBuilder<SelectablePart> criteria = SelectionBuilder.create(SelectablePart.class);
			criteria.add(Conditions.eq(Product.META.id.getName(), product.toString()));
			final Query<SelectablePart> query = abasConnectionObject.createQuery(criteria.build());
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

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadDataFromOperation()
		 */
		@Override
		protected void loadDataFromOperation() {
			if (null == operation) {
				loadDataFromWorkSlip();
				if (null == operation) {
					return;
				}
			}
			final SelectionBuilder<Operation> criteria = SelectionBuilder.create(Operation.class);
			criteria.add(Conditions.eq(Operation.META.id, operation));
			final Query<Operation> query = abasConnectionObject.createQuery(criteria.build());
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

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadDataFromOperationReservation()
		 */
		@Override
		protected void loadDataFromOperationReservation() {
			if (null == operationReservation) {
				loadDataFromWorkSlip();
				if (null == operationReservation) {
					return;
				}
			}
			final SelectionBuilder<SelectablePurchasing> criteria = SelectionBuilder.create(SelectablePurchasing.class);
			criteria.add(Conditions.eq(Reservations.META.id.getName(), operationReservation.toString()));
			final Query<SelectablePurchasing> query = abasConnectionObject.createQuery(criteria.build());
			query.setFields(FieldSet.of(Reservations.META.itemText.getName()));
			query.setLazyLoad(false);
			final DatabaseIterator<SelectablePurchasing> operationReservationIterator = query.iterator();
			final Reservations operationReservation = (Reservations)operationReservationIterator.next();
			operationReservationIterator.close();
			operationReservationText = operationReservation.getItemText();
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#getBillOfMaterials()
		 */
		@Override
		protected List<BomElement> getBillOfMaterials() {
			// TODO
			throw new RuntimeException("Not yet implemented!");
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 2168632003172665470L;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy munkalapot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param ajoContext Az AJO-környezet.
	 * @return Abas-munkalapazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező munkalap azonosítója.
	 */
	protected static Id workSlipIdFilter(Id abasObjectId, DbContext ajoContext) {
		if (9 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		final WorkOrders workSlip;
		try {
			workSlip = ajoContext.load(WorkOrders.class, abasObjectId);
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
	 * @param ajoContext Az AJO-környezet.
	 */
	public TaskAjoImpl(Id workSlipId, DbContext ajoContext) {
		super(workSlipIdFilter(workSlipId, ajoContext), DbContext.class);
	}

	/**
	 * Konstruktor.
	 * @param workSlip A feladathoz tartozó munkalap.
	 */
	TaskAjoImpl(SelectableWorkorder workSlip) {
		super(new IdImpl(workSlip.getRawString(WorkOrders.META.id.getName())), DbContext.class);
	}

	/**
	 * @param ajoContext Az AJO-környezet.
	 * @return A feladathoz tartozó munkalap.
	 */
	public WorkOrders getWorkSlip(DbContext ajoContext) {
		return ajoContext.load(WorkOrders.class, workSlipId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getStartDate(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public AbasDate getStartDate(AbasConnection<?> abasConnection) {
		return getWorkSlip(AjoConnection.getAjoContext(abasConnection)).getStartDateDay();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.impl.TaskImpl#newDetails(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	protected DetailsAjoImpl newDetails(AbasConnection<DbContext> abasConnection) {
		return (new DetailsAjoImpl(abasConnection));
	}

}
