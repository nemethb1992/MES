/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.edp;

import de.abas.ceks.jedp.EDPConstants;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.StandardEDPSelection;
import de.abas.ceks.jedp.StandardEDPSelectionCriteria;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.field.StringField;
import de.abas.erp.db.field.UnitField;
import de.abas.erp.db.schema.company.TableOfUnits;
import de.abas.erp.db.schema.operation.Operation;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.SelectablePart;
import de.abas.erp.db.schema.purchasing.Reservations;
import de.abas.erp.db.schema.purchasing.SelectablePurchasing;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.type.AbasUnit;

import java.math.BigDecimal;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbstractTask;
import phoenix.mes.abas.TaskDetails;

/**
 * Gyártási feladat osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class TaskEdpImpl extends AbstractTask<EDPSession> {

	/**
	 * Segédosztály a munkalap adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class WorkSlipQuery extends EdpQueryExecutor {

		/**
		 * A munkalap adatainak lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			public static final String ID_NO = WorkOrders.META.idno.getName();

			public static final String START_DATE = WorkOrders.META.startDateDay.getName();

			public static final String USAGE = WorkOrders.META.usage.getName();

			public static final String SETUP_TIME_UNIT = WorkOrders.META.setupTimeUnit.getName();

			public static final String SETUP_TIME = WorkOrders.META.setupTime.getName();

			public static final String SETUP_TIME_SEC = WorkOrders.META.setupTimeSec.getName();

			public static final String UNIT_TIME_UNIT = WorkOrders.META.timeUnit.getName();

			public static final String UNIT_TIME = WorkOrders.META.timeLimUnit.getName();

			public static final String UNIT_TIME_SEC = WorkOrders.META.unitTimeSec.getName();

			public static final String NUMBER_OF_EXECUTIONS = WorkOrders.META.elemQty.getName();

			public static final String OUTSTANDING_QUANTITY = WorkOrders.META.unitQty.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final WorkSlipQuery EXECUTOR = new WorkSlipQuery();

		/**
		 * Konstruktor.
		 */
		private WorkSlipQuery() {
			super(Field.class);
		}

	}

	/**
	 * Segédosztály a termék adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class ProductQuery extends EdpQueryExecutor {

		/**
		 * A termék adatainak lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			public static final String ID_NO = WorkOrders.META.product.join(castToSelectablePart(Product.META.idno)).getName();

			public static final String SWD = WorkOrders.META.product.join(castToSelectablePart(Product.META.swd)).getName();

			public static final String DESCRIPTION = WorkOrders.META.product.join(castToSelectablePart(Product.META.descrOperLang)).getName();

			public static final String DESCRIPTION2 = WorkOrders.META.product.join(castToSelectablePart(Product.META.yname2)).getName();

			public static final String STOCK_UNIT = WorkOrders.META.product.join(castToSelectablePart(Product.META.SU)).getName();

			@SuppressWarnings("unchecked")
			private static StringField<? super SelectablePart> castToSelectablePart(StringField<? extends SelectablePart> field) {
				return (StringField<? super SelectablePart>)field;
			}

			@SuppressWarnings("unchecked")
			private static <U extends AbasUnit> UnitField<? super SelectablePart, U> castToSelectablePart(UnitField<? extends SelectablePart, U> field) {
				return (UnitField<? super SelectablePart, U>)field;
			}

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final ProductQuery EXECUTOR = new ProductQuery();

		/**
		 * Konstruktor.
		 */
		private ProductQuery() {
			super(Field.class);
		}

	}

	/**
	 * Segédosztály a művelet adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class OperationQuery extends EdpQueryExecutor {

		/**
		 * A művelet adatainak lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			public static final String ID_NO = WorkOrders.META.seriesOfOperations.join(Operation.META.idno).getName();

			public static final String SWD = WorkOrders.META.seriesOfOperations.join(Operation.META.swd).getName();

			public static final String DESCRIPTION = WorkOrders.META.seriesOfOperations.join(Operation.META.descrOperLang).getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final OperationQuery EXECUTOR = new OperationQuery();

		/**
		 * Konstruktor.
		 */
		private OperationQuery() {
			super(Field.class);
		}

	}

	/**
	 * Segédosztály a műveletfoglalás adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class OperationReservationQuery extends EdpQueryExecutor {

		/**
		 * A műveletfoglalás adatainak lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			public static final String ITEM_TEXT = WorkOrders.META.lastReservation.join(castToSelectablePurchasing(Reservations.META.itemText)).getName();

			@SuppressWarnings("unchecked")
			private static StringField<? super SelectablePurchasing> castToSelectablePurchasing(StringField<? extends SelectablePurchasing> field) {
				return (StringField<? super SelectablePurchasing>)field;
			}

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final OperationReservationQuery EXECUTOR = new OperationReservationQuery();

		/**
		 * Konstruktor.
		 */
		private OperationReservationQuery() {
			super(Field.class);
		}

	}

	/**
	 * Gyártási feladat részleteit leíró osztály, EDP-ben implementálva.
	 * @author szizo
	 */
	protected class DetailsEdpImpl extends TaskDetails<EDPSession> {

		/**
		 * Konstruktor.
		 * @param edpSession Az EDP-munkamenet.
		 */
		protected DetailsEdpImpl(EDPSession edpSession) {
			super(edpSession);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.TaskDetails#getUnitName(de.abas.erp.db.type.AbasUnit)
		 */
		@Override
		protected String getUnitName(AbasUnit unit) {
			final StandardEDPSelectionCriteria criteria = new StandardEDPSelectionCriteria();
			criteria.setAliveFlag(EDPConstants.ALIVEFLAG_ALIVE);
			criteria.set(TableOfUnits.Row.META.unitCode.getName() + "==" + unit);
			final EDPQuery edpQuery = abasConnectionObject.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.startQuery(new StandardEDPSelection(unitsTableName, criteria), TableOfUnits.Row.META.unitOperLang.getName());
				if (edpQuery.getNextRecord()) {
					return edpQuery.getField(1);
				}
				throw new EDPRuntimeException("Ismeretlen mértékegység: " + unit);
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			}
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.TaskDetails#loadDataFromWorkSlip()
		 */
		@Override
		protected void loadDataFromWorkSlip() {
			final EDPQuery edpQuery = WorkSlipQuery.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			workSlipNo = edpQuery.getField(WorkSlipQuery.Field.ID_NO);
			startDate = AbasDate.valueOf(edpQuery.getField(WorkSlipQuery.Field.START_DATE));
			usage = edpQuery.getField(WorkSlipQuery.Field.USAGE);
			final AbasUnit setupTimeAbasUnit = AbasUnit.UNITS.valueOf(edpQuery.getField(WorkSlipQuery.Field.SETUP_TIME_UNIT));
			setupTime = calculateGrossTime(new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.SETUP_TIME)), setupTimeAbasUnit, new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.SETUP_TIME_SEC)));
			setupTimeUnit = unitNamesRepository.getUnitName(setupTimeAbasUnit);
			final AbasUnit unitTimeAbasUnit = AbasUnit.UNITS.valueOf(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME_UNIT));
			unitTime = calculateGrossTime(new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME)), unitTimeAbasUnit, new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME_SEC)));
			unitTimeUnit = unitNamesRepository.getUnitName(unitTimeAbasUnit);
			numberOfExecutions = new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.NUMBER_OF_EXECUTIONS));
			outstandingQuantity = new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.OUTSTANDING_QUANTITY));
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.TaskDetails#loadDataFromProduct()
		 */
		@Override
		protected void loadDataFromProduct() {
			final EDPQuery edpQuery = ProductQuery.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			productIdNo = edpQuery.getField(ProductQuery.Field.ID_NO);
			productSwd = edpQuery.getField(ProductQuery.Field.SWD);
			productDescription = edpQuery.getField(ProductQuery.Field.DESCRIPTION);
			productDescription2 = edpQuery.getField(ProductQuery.Field.DESCRIPTION2);
			stockUnit = unitNamesRepository.getUnitName(AbasUnit.UNITS.valueOf(edpQuery.getField(ProductQuery.Field.STOCK_UNIT)));
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.TaskDetails#loadDataFromOperation()
		 */
		@Override
		protected void loadDataFromOperation() {
			final EDPQuery edpQuery = OperationQuery.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			operationIdNo = edpQuery.getField(OperationQuery.Field.ID_NO);
			operationSwd = edpQuery.getField(OperationQuery.Field.SWD);
			operationDescription = edpQuery.getField(OperationQuery.Field.DESCRIPTION);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.TaskDetails#loadDataFromOperationReservation()
		 */
		@Override
		protected void loadDataFromOperationReservation() {
			final EDPQuery edpQuery = OperationQuery.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			operationReservationText = edpQuery.getField(OperationReservationQuery.Field.ITEM_TEXT);
		}

	}

	/**
	 * A mértékegységek adattáblájának neve (hatékonysági okokból gyorsítótárazva).
	 */
	protected static final String unitsTableName = TableOfUnits.Row.META.tableDesc().toString();

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -8646130599768362963L;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy munkalapot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param edpSession Az EDP-munkamenet.
	 * @return Abas-munkalapazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező munkalap azonosítója.
	 */
	protected static Id workSlipIdFilter(Id abasObjectId, EDPSession edpSession) {
		if (9 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		final EDPQuery edpQuery = edpSession.createQuery();
		try {
			edpQuery.enableQueryMetaData(false);
			if (!edpQuery.readRecord(abasObjectId.toString(), WorkOrders.META.type.getName())
					|| !EnumWorkOrderType.WorkSlips.toString().equals(edpQuery.getField(1))) {
				throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId, e);
		}
		return abasObjectId;
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 * @param edpSession Az EDP-munkamenet.
	 */
	public TaskEdpImpl(Id workSlipId, EDPSession edpSession) {
		this(workSlipIdFilter(workSlipId, edpSession));
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 */
	TaskEdpImpl(Id workSlipId) {
		super(workSlipId, EDPSession.class);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getStartDate(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public AbasDate getStartDate(AbasConnection<?> abasConnection) {
		return getDetails(abasConnection).getStartDate();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbstractTask#getDetails(java.lang.Object)
	 */
	@Override
	protected DetailsEdpImpl getDetails(EDPSession edpSession) {
		return (new DetailsEdpImpl(edpSession));
	}

}
