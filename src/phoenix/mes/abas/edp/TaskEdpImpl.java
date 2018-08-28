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
import de.abas.ceks.jedp.InvalidQueryException;
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
import phoenix.mes.abas.AbstractTaskDetails;

/**
 * Gyártási feladat osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class TaskEdpImpl extends AbstractTask {

	/**
	 * Segédosztály a gyártási feladat részleteinek lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class WorkSlipQuery extends EdpQueryExecutor {

		/**
		 * A gyártási feladat részleteinek lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			public static final String WORK_SLIP_NO = WorkOrders.META.idno.getName();

			public static final String USAGE = WorkOrders.META.usage.getName();

			public static final String SETUP_TIME_UNIT = WorkOrders.META.setupTimeUnit.getName();

			public static final String SETUP_TIME = WorkOrders.META.setupTime.getName();

			public static final String SETUP_TIME_SEC = WorkOrders.META.setupTimeSec.getName();

			public static final String UNIT_TIME_UNIT = WorkOrders.META.timeUnit.getName();

			public static final String UNIT_TIME = WorkOrders.META.timeLimUnit.getName();

			public static final String UNIT_TIME_SEC = WorkOrders.META.unitTimeSec.getName();

			public static final String NUMBER_OF_EXECUTIONS = WorkOrders.META.elemQty.getName();

			public static final String OUTSTANDING_QUANTITY = WorkOrders.META.unitQty.getName();
/*
public static final String PROBA1 = WorkOrders.META.product.getName();
public static final String PROBA2 = WorkOrders.META.seriesOfOperations.getName();
public static final String PROBA3 = WorkOrders.META.lastReservation.getName();
*/
			public static final String PRODUCT_ID_NO = WorkOrders.META.product.join(castToSelectablePart(Product.META.idno)).getName();

			public static final String PRODUCT_SWD = WorkOrders.META.product.join(castToSelectablePart(Product.META.swd)).getName();

			public static final String PRODUCT_DESCRIPTION = WorkOrders.META.product.join(castToSelectablePart(Product.META.descrOperLang)).getName();

			public static final String PRODUCT_DESCRIPTION2 = WorkOrders.META.product.join(castToSelectablePart(Product.META.yname2)).getName();

			public static final String STOCK_UNIT = WorkOrders.META.product.join(castToSelectablePart(Product.META.SU)).getName();

			public static final String OPERATION_ID_NO = WorkOrders.META.seriesOfOperations.join(Operation.META.idno).getName();

			public static final String OPERATION_SWD = WorkOrders.META.seriesOfOperations.join(Operation.META.swd).getName();

			public static final String OPERATION_DESCRIPTION = WorkOrders.META.seriesOfOperations.join(Operation.META.descrOperLang).getName();

			public static final String OPERATION_RESERVATION_TEXT = WorkOrders.META.lastReservation.join(castToSelectablePurchasing(Reservations.META.itemText)).getName();

			/**
			 *
			 * @param field
			 * @return
			 */
			@SuppressWarnings("unchecked")
			private static StringField<? super SelectablePart> castToSelectablePart(StringField<? extends SelectablePart> field) {
				return (StringField<? super SelectablePart>)field;
			}

			@SuppressWarnings("unchecked")
			private static <U extends AbasUnit> UnitField<? super SelectablePart, U> castToSelectablePart(UnitField<? extends SelectablePart, U> field) {
				return (UnitField<? super SelectablePart, U>)field;
			}

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
		public static final WorkSlipQuery EXECUTOR = new WorkSlipQuery();

		/**
		 * Konstruktor.
		 */
		private WorkSlipQuery() {
			super(Field.class);
		}

	}

	/**
	 * Gyártási feladat részleteit leíró osztály, EDP-ben implementálva.
	 * @author szizo
	 */
	protected class DetailsEdpImpl extends AbstractTaskDetails {

		/**
		 * Az EDP-munkamenet.
		 */
		protected final EDPSession edpSession;

		/**
		 * Konstruktor.
		 * @param edpSession Az EDP-munkamenet.
		 */
		protected DetailsEdpImpl(EDPSession edpSession) {
			this.edpSession = edpSession;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.AbstractTaskDetails#getUnitName(de.abas.erp.db.type.AbasUnit)
		 */
		@Override
		protected String getUnitName(AbasUnit unit) {
			final StandardEDPSelectionCriteria criteria = new StandardEDPSelectionCriteria();
			criteria.setAliveFlag(EDPConstants.ALIVEFLAG_ALIVE);
			criteria.set(TableOfUnits.Row.META.unitCode.getName() + "==" + unit);
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.startQuery(new StandardEDPSelection(TableOfUnits.Row.META.tableDesc().toString(), criteria), TableOfUnits.Row.META.unitOperLang.getName());
				if (edpQuery.getNextRecord()) {
					return edpQuery.getField(1);
				}
				throw new EDPRuntimeException("Ismeretlen mértékegység: " + unit);
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			}
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.AbstractTaskDetails#loadFields()
		 */
		@Override
		protected void loadFields() {
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.readRecord(workSlipId.toString(), WorkSlipQuery.EXECUTOR.getFieldNames());
				workSlipNo = edpQuery.getField(WorkSlipQuery.Field.WORK_SLIP_NO);
				usage = edpQuery.getField(WorkSlipQuery.Field.USAGE);
				final AbasUnit setupTimeAbasUnit = AbasUnit.UNITS.valueOf(edpQuery.getField(WorkSlipQuery.Field.SETUP_TIME_UNIT));
				setupTime = calculateGrossTime(new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.SETUP_TIME)), setupTimeAbasUnit, new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.SETUP_TIME_SEC)));
				setupTimeUnit = unitNamesRepository.getUnitName(setupTimeAbasUnit);
				final AbasUnit unitTimeAbasUnit = AbasUnit.UNITS.valueOf(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME_UNIT));
				unitTime = calculateGrossTime(new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME)), unitTimeAbasUnit, new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME_SEC)));
				unitTimeUnit = unitNamesRepository.getUnitName(unitTimeAbasUnit);
				numberOfExecutions = new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.NUMBER_OF_EXECUTIONS));
				outstandingQuantity = new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.OUTSTANDING_QUANTITY));
			} catch (InvalidQueryException e) {
				throw new EDPRuntimeException(e);
			}
			//loadDataFromProduct(edpQuery.getField(WorkOrders.META.product.getName()));
			//loadDataFromOperation(edpQuery.getField(WorkOrders.META.seriesOfOperations.getName()));
			//loadDataFromOperationReservation(edpQuery.getField(WorkOrders.META.lastReservation.getName()));
		}

		/**
		 * A termékhez kapcsolódó tagváltozók kitöltése.
		 * @param productId A termék azonosítója.
		 */
		protected void loadDataFromProduct(String productId) {
			final String[] fieldNames = {Product.META.idno.getName(),
					Product.META.swd.getName(),
					Product.META.descrOperLang.getName(),
					Product.META.yname2.getName(),
					Product.META.SU.getName()};
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.readRecord(productId, fieldNames);
				productIdNo = edpQuery.getField(Product.META.idno.getName());
				productSwd = edpQuery.getField(Product.META.swd.getName());
				productDescription = edpQuery.getField(Product.META.descrOperLang.getName());
				productDescription2 = edpQuery.getField(Product.META.yname2.getName());
				stockUnit = unitNamesRepository.getUnitName(AbasUnit.UNITS.valueOf(edpQuery.getField(Product.META.SU.getName())));
			} catch (InvalidQueryException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * A művelethez kapcsolódó tagváltozók kitöltése.
		 * @param operationId A művelet azonosítója.
		 */
		protected void loadDataFromOperation(String operationId) {
			final String[] fieldNames = {Operation.META.idno.getName(),
					Operation.META.swd.getName(),
					Operation.META.descrOperLang.getName()};
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.readRecord(operationId, fieldNames);
				operationIdNo = edpQuery.getField(Operation.META.idno.getName());
				operationSwd = edpQuery.getField(Operation.META.swd.getName());
				operationDescription = edpQuery.getField(Operation.META.descrOperLang.getName());
			} catch (InvalidQueryException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * A műveletfoglaláshoz kapcsolódó tagváltozók kitöltése.
		 * @param operationReservationId A műveletfoglalás azonosítója.
		 */
		protected void loadDataFromOperationReservation(String operationReservationId) {
			final String[] fieldNames = {Reservations.META.itemText.getName()};
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.readRecord(operationReservationId, fieldNames);
				operationReservationText = edpQuery.getField(Reservations.META.itemText.getName());
			} catch (InvalidQueryException e) {
				throw new EDPRuntimeException(e);
			}
		}

	}

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
		super(workSlipIdFilter(workSlipId, edpSession));
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 */
	TaskEdpImpl(Id workSlipId) {
		super(workSlipId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getStartDate(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public AbasDate getStartDate(AbasConnection<?> abasConnection) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getDetails(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public Details getDetails(AbasConnection<?> abasConnection) {
		return (new DetailsEdpImpl(EdpConnection.getEdpSession(abasConnection)));
	}

}
