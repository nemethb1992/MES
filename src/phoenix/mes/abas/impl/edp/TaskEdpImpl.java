/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.CantChangeFieldValException;
import de.abas.ceks.jedp.CantReadFieldPropertyException;
import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPEditObject;
import de.abas.ceks.jedp.EDPEditor;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumTypeCommands;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.field.StringField;
import de.abas.erp.db.field.UnitField;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESTASK;
import de.abas.erp.db.schema.operation.Operation;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.SelectablePart;
import de.abas.erp.db.schema.purchasing.Reservations;
import de.abas.erp.db.schema.purchasing.SelectablePurchasing;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.type.AbasUnit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.impl.TaskImpl;
import phoenix.mes.abas.impl.BomElementImpl;
import phoenix.mes.abas.impl.TaskDetails;

/**
 * Gyártási feladat osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class TaskEdpImpl extends TaskImpl<EDPSession> {

	/**
	 * Alaposztály a gyártási feladat adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static abstract class TaskDataQuery extends InfoSystemExecutor {

		/**
		 * @param criteriaFieldNames A szűrőmezők nevei.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @return Az infosystem indításához szükséges mezőbeállítások.
		 */
		protected static EDPEditFieldList getFilterCriteria(String[] criteriaFieldNames, Id workSlipId) {
			try {
				return new EDPEditFieldList(criteriaFieldNames, new String[] {workSlipId.toString(), "1", " "});
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * Konstruktor.
		 * @param headerFieldNamesClass A lekérdezendő fejrészmezők neveit konstansokként tartalmazó osztály.
		 */
		public TaskDataQuery(Class<?> headerFieldNamesClass) {
			super("MESTASK", headerFieldNamesClass, null);
		}

		/**
		 * A lekérdezés végrehajtása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A lekérdezendő mezők értékei.
		 */
		public EDPEditObject executeQuery(Id workSlipId, EDPSession edpSession) {
			return executeQuery(getFilterCriteria(getCriteriaFieldNames(), workSlipId), edpSession);
		}

		/**
		 * @return A szűrőmezők nevei.
		 */
		protected abstract String[] getCriteriaFieldNames();

	}

	/**
	 * Segédosztály a gyártási feladat alapadatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class BasicDataQuery extends TaskDataQuery {

		/**
		 * A lekérdezendő (fejrész)mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A munkalap száma.
			 */
			public static final String ID_NO = InfosysOw1MESTASK.META.ynum9.getName();

			/**
			 * A feladat elkezdésének (tervezett) napja.
			 */
			public static final String START_DATE = InfosysOw1MESTASK.META.ytsterm.getName();

			/**
			 * A termék Felhasználás-hivatkozása.
			 */
			public static final String USAGE = InfosysOw1MESTASK.META.yverw.getName();

			/**
			 * A beállítási idő mértékegysége.
			 */
			public static final String SETUP_TIME_UNIT = InfosysOw1MESTASK.META.yzr.getName();

			/**
			 * A beállítási idő mértékegységének neve.
			 */
			public static final String SETUP_TIME_UNIT_NAME = InfosysOw1MESTASK.META.yzrbspr.getName();

			/**
			 * A beállítási idő.
			 */
			public static final String SETUP_TIME = InfosysOw1MESTASK.META.ytr.getName();

			/**
			 * Egységnyi beállítási idő bruttósítva hány másodpercből áll?
			 */
			public static final String SETUP_TIME_SEC = InfosysOw1MESTASK.META.yzrsek.getName();

			/**
			 * A darabidő mértékegysége.
			 */
			public static final String UNIT_TIME_UNIT = InfosysOw1MESTASK.META.yze.getName();

			/**
			 * A darabidő mértékegységének neve.
			 */
			public static final String UNIT_TIME_UNIT_NAME = InfosysOw1MESTASK.META.yzebspr.getName();

			/**
			 * A darabidő.
			 */
			public static final String UNIT_TIME = InfosysOw1MESTASK.META.yte.getName();

			/**
			 * Egységnyi darabidő bruttósítva hány másodpercből áll?
			 */
			public static final String UNIT_TIME_SEC = InfosysOw1MESTASK.META.yzesek.getName();

			/**
			 * Hányszor kell végrehajtani a műveletet?
			 */
			public static final String NUMBER_OF_EXECUTIONS = InfosysOw1MESTASK.META.yanzahl.getName();

			/**
			 * A nyitott mennyiség.
			 */
			public static final String OUTSTANDING_QUANTITY = InfosysOw1MESTASK.META.ymge.getName();

			/**
			 * A termék cikkszáma.
			 */
			public static final String PRODUCT_ID_NO = InfosysOw1MESTASK.META.ytenum.getName();

			/**
			 * A termék keresőszava.
			 */
			public static final String PRODUCT_SWD = InfosysOw1MESTASK.META.ytesuch.getName();

			/**
			 * A termék megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String PRODUCT_DESCRIPTION = InfosysOw1MESTASK.META.ytenamebspr.getName();

			/**
			 * A termék második megnevezése.
			 */
			public static final String PRODUCT_DESCRIPTION2 = InfosysOw1MESTASK.META.yyname2.getName();

			/**
			 * A mennyiségi egység (raktáregység) neve.
			 */
			public static final String STOCK_UNIT_NAME = InfosysOw1MESTASK.META.ylebspr.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASK.META.yas.getName(), InfosysOw1MESTASK.META.yalap.getName(), InfosysOw1MESTASK.META.start.getName()};

		/**
		 * Egyke objektum.
		 */
		public static final BasicDataQuery EXECUTOR = new BasicDataQuery();

		/**
		 * Konstruktor.
		 */
		private BasicDataQuery() {
			super(Field.class);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.TaskEdpImpl.TaskDataQuery#getCriteriaFieldNames()
		 */
		@Override
		protected String[] getCriteriaFieldNames() {
			return criteriaFieldNames;
		}

	}

	/**
	 * Segédosztály a művelet adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class OperationQuery extends TaskDataQuery {

		/**
		 * A lekérdezendő (fejrész)mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A művelet hivatkozási száma.
			 */
			public static final String ID_NO = InfosysOw1MESTASK.META.yagnum.getName();

			/**
			 * A művelet keresőszava.
			 */
			public static final String SWD = InfosysOw1MESTASK.META.yagsuch.getName();

			/**
			 * A művelet megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = InfosysOw1MESTASK.META.yagnamebspr.getName();

			/**
			 * A műveletfoglalás tételszövege.
			 */
			public static final String ITEM_TEXT = InfosysOw1MESTASK.META.yptext.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASK.META.yas.getName(), InfosysOw1MESTASK.META.ymuv.getName(), InfosysOw1MESTASK.META.start.getName()};

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

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.TaskEdpImpl.TaskDataQuery#getCriteriaFieldNames()
		 */
		@Override
		protected String[] getCriteriaFieldNames() {
			return criteriaFieldNames;
		}

	}

	/**
	 * Segédosztály a gyártási feladathoz kapcsolódó darabjegyzék adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class BomQuery extends InfoSystemTableConverter<BomElement> {

		/**
		 * A lekérdezendő (táblázati) mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A beépülő cikk hivatkozási száma.
			 */
			public static final String ID_NO = InfosysOw1MESTASK.Row.META.ytelemnum.getName();

			/**
			 * A beépülő cikk keresőszava.
			 */
			public static final String SWD = InfosysOw1MESTASK.Row.META.ytelemsuch.getName();

			/**
			 * A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = InfosysOw1MESTASK.Row.META.ytnamebspr.getName();

			/**
			 * A beépülő cikk második megnevezése.
			 */
			public static final String DESCRIPTION2 = InfosysOw1MESTASK.Row.META.ytyname2.getName();

			/**
			 * A beépülési mennyiség (egy késztermékre vonatkozóan).
			 */
			public static final String QUANTITY_PER_PRODUCT = InfosysOw1MESTASK.Row.META.ytmenge.getName();

			/**
			 * A beépülési mennyiség egységének (raktáregység) neve.
			 */
			public static final String STOCK_UNIT_NAME = InfosysOw1MESTASK.Row.META.ytlebspr.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASK.META.yas.getName(), InfosysOw1MESTASK.META.ydbj.getName(), InfosysOw1MESTASK.META.start.getName()};

		/**
		 * Egyke objektum.
		 */
		public static final BomQuery EXECUTOR = new BomQuery();

		/**
		 * Konstruktor.
		 */
		private BomQuery() {
			super("MESTASK", Field.class);
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A darabjegyzék sorainak adatait tartalmazó objektumok listája.
		 */
		public List<BomElement> getRows(Id workSlipId, EDPSession edpSession) {
			final List<BomElement> rows = getRows(TaskDataQuery.getFilterCriteria(criteriaFieldNames, workSlipId), edpSession);
			return (1 < rows.size() ? Collections.unmodifiableList(rows) : rows);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(de.abas.ceks.jedp.EDPEditFieldList)
		 */
		@Override
		protected BomElementImpl createRowObject(EDPEditFieldList rowData) {
			return new BomElementImpl(rowData.getField(Field.ID_NO).getValue(),
					rowData.getField(Field.SWD).getValue(),
					rowData.getField(Field.DESCRIPTION).getValue(),
					rowData.getField(Field.DESCRIPTION2).getValue(),
					new BigDecimal(rowData.getField(Field.QUANTITY_PER_PRODUCT).getValue()),
					rowData.getField(Field.STOCK_UNIT_NAME).getValue());
		}

	}

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

			/**
			 * A munkalap száma.
			 */
			public static final String ID_NO = WorkOrders.META.idno.getName();

			/**
			 * A feladat elkezdésének (tervezett) napja.
			 */
			public static final String START_DATE = WorkOrders.META.startDateDay.getName();

			/**
			 * A termék Felhasználás-hivatkozása.
			 */
			public static final String USAGE = WorkOrders.META.usage.getName();

			/**
			 * A beállítási idő mértékegysége.
			 */
			public static final String SETUP_TIME_UNIT = WorkOrders.META.setupTimeUnit.getName();

			/**
			 * A beállítási idő.
			 */
			public static final String SETUP_TIME = WorkOrders.META.setupTime.getName();

			/**
			 * Egységnyi beállítási idő bruttósítva hány másodpercből áll?
			 */
			public static final String SETUP_TIME_SEC = WorkOrders.META.setupTimeSec.getName();

			/**
			 * A darabidő mértékegysége.
			 */
			public static final String UNIT_TIME_UNIT = WorkOrders.META.timeUnit.getName();

			/**
			 * A darabidő.
			 */
			public static final String UNIT_TIME = WorkOrders.META.timeLimUnit.getName();

			/**
			 * Egységnyi darabidő bruttósítva hány másodpercből áll?
			 */
			public static final String UNIT_TIME_SEC = WorkOrders.META.unitTimeSec.getName();

			/**
			 * Hányszor kell végrehajtani a műveletet?
			 */
			public static final String NUMBER_OF_EXECUTIONS = WorkOrders.META.elemQty.getName();

			/**
			 * A nyitott mennyiség.
			 */
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

			/**
			 * A termék cikkszáma.
			 */
			public static final String ID_NO = WorkOrders.META.product.join(castToSelectablePart(Product.META.idno)).getName();

			/**
			 * A termék keresőszava.
			 */
			public static final String SWD = WorkOrders.META.product.join(castToSelectablePart(Product.META.swd)).getName();

			/**
			 * A termék megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = WorkOrders.META.product.join(castToSelectablePart(Product.META.descrOperLang)).getName();

			/**
			 * A termék második megnevezése.
			 */
			public static final String DESCRIPTION2 = WorkOrders.META.product.join(castToSelectablePart(Product.META.yname2)).getName();

			/**
			 * A mennyiségi egység (raktáregység).
			 */
			public static final String STOCK_UNIT = WorkOrders.META.product.join(castToSelectablePart(Product.META.SU)).getName();

			/**
			 * @param field A szöveges mező.
			 * @return A szöveges mező, a SelectablePart osztály tagjaként láttatva.
			 */
			@SuppressWarnings("unchecked")
			private static StringField<SelectablePart> castToSelectablePart(StringField<? extends SelectablePart> field) {
				return (StringField<SelectablePart>)field;
			}

			/**
			 * @param field A mértékegység-mező.
			 * @return A mértékegység-mező, a SelectablePart osztály tagjaként láttatva.
			 */
			@SuppressWarnings("unchecked")
			private static <U extends AbasUnit> UnitField<SelectablePart, U> castToSelectablePart(UnitField<? extends SelectablePart, U> field) {
				return (UnitField<SelectablePart, U>)field;
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
	protected static final class OperationQuery0 extends EdpQueryExecutor {

		/**
		 * A művelet adatainak lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A művelet hivatkozási száma.
			 */
			public static final String ID_NO = WorkOrders.META.seriesOfOperations.join(Operation.META.idno).getName();

			/**
			 * A művelet keresőszava.
			 */
			public static final String SWD = WorkOrders.META.seriesOfOperations.join(Operation.META.swd).getName();

			/**
			 * A művelet megnevezése az aktuálisan beállított kezelőnyelven.
			 */
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
		public static final OperationQuery0 EXECUTOR = new OperationQuery0();

		/**
		 * Konstruktor.
		 */
		private OperationQuery0() {
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

			/**
			 * A műveletfoglalás tételszövege.
			 */
			public static final String ITEM_TEXT = WorkOrders.META.lastReservation.join(castToSelectablePurchasing(Reservations.META.itemText)).getName();

			/**
			 * @param field A szöveges mező.
			 * @return A szöveges mező, a SelectablePurchasing osztály tagjaként láttatva.
			 */
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
	protected class DetailsEdpImpl0 extends TaskDetails<EDPSession> {

		/**
		 * Konstruktor.
		 * @param abasConnection Az Abas-kapcsolat.
		 */
		protected DetailsEdpImpl0(AbasConnection<EDPSession> abasConnection) {
			super(abasConnection, abasConnectionType);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadBasicData()
		 */
		@Override
		protected void loadBasicData() {
			loadDataFromWorkSlip();
			loadDataFromProduct();
		}

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
			final AbasUnit unitTimeAbasUnit = AbasUnit.UNITS.valueOf(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME_UNIT));
			unitTime = calculateGrossTime(new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME)), unitTimeAbasUnit, new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.UNIT_TIME_SEC)));
			numberOfExecutions = new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.NUMBER_OF_EXECUTIONS));
			outstandingQuantity = new BigDecimal(edpQuery.getField(WorkSlipQuery.Field.OUTSTANDING_QUANTITY));
		}

		protected void loadDataFromProduct() {
			final EDPQuery edpQuery = ProductQuery.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			productIdNo = edpQuery.getField(ProductQuery.Field.ID_NO);
			productSwd = edpQuery.getField(ProductQuery.Field.SWD);
			productDescription = edpQuery.getField(ProductQuery.Field.DESCRIPTION);
			productDescription2 = edpQuery.getField(ProductQuery.Field.DESCRIPTION2);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadOperationData()
		 */
		@Override
		protected void loadOperationData() {
			loadDataFromOperation();
			loadDataFromOperationReservation();
		}

		protected void loadDataFromOperation() {
			final EDPQuery edpQuery = OperationQuery0.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			operationIdNo = edpQuery.getField(OperationQuery0.Field.ID_NO);
			operationSwd = edpQuery.getField(OperationQuery0.Field.SWD);
			operationDescription = edpQuery.getField(OperationQuery0.Field.DESCRIPTION);
		}

		protected void loadDataFromOperationReservation() {
			final EDPQuery edpQuery = OperationReservationQuery.EXECUTOR.readRecord(workSlipId, abasConnectionObject);
			if (null == edpQuery) {
				return;
			}
			operationReservationText = edpQuery.getField(OperationReservationQuery.Field.ITEM_TEXT);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadSalesOrderData()
		 */
		@Override
		protected void loadSalesOrderData() {
			// TODO
			throw new RuntimeException("Not yet implemented!");
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#getBillOfMaterials()
		 */
		@Override
		protected List<BomElement> getBillOfMaterials() {
			final EDPEditor infoSystemQuery = abasConnectionObject.createEditor();
			try {
				infoSystemQuery.beginEditCmd(Integer.toString(EnumTypeCommands.Infosystem.getCode()), "MESTASK");
				infoSystemQuery.setFieldVal(InfosysOw1MESTASK.META.yas.getName(), workSlipId.toString());
				infoSystemQuery.setFieldVal(InfosysOw1MESTASK.META.ydbj.getName(), "1");
				infoSystemQuery.setFieldVal(InfosysOw1MESTASK.META.start.getName(), "");
				final int rowCount = infoSystemQuery.getRowCount();
				switch (rowCount) {
					case 0:
						return Collections.emptyList();
					case 1:
						return Collections.singletonList(newBomElement(infoSystemQuery, 1));
					default:
						final List<BomElement> bom = new ArrayList<>(rowCount);
						for (int i = 1; i <= rowCount; i++) {
							bom.add(newBomElement(infoSystemQuery, i));
						}
						return Collections.unmodifiableList(bom);
				}
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			} finally {
				if (infoSystemQuery.isActive()) {
					infoSystemQuery.endEditCancel();
				}
			}
		}

		/**
		 * A megadott darabjegyzéksor adatait tartalmazó objektum létrehozása.
		 * @param infoSystemQuery A lefuttatott infosystem-lekérdezés.
		 * @param rowNo Az eredménysor száma.
		 * @return A darabjegyzéksor adatait tartalmazó objektum.
		 * @throws CantReadFieldPropertyException Ha hiba történt valamelyik mező értékének beolvasása során.
		 */
		protected BomElement newBomElement(EDPEditor infoSystemQuery, int rowNo) throws CantReadFieldPropertyException {
			return new BomElementImpl(infoSystemQuery.getFieldVal(rowNo, InfosysOw1MESTASK.Row.META.ytelemnum.getName()),
					infoSystemQuery.getFieldVal(rowNo, InfosysOw1MESTASK.Row.META.ytelemsuch.getName()),
					infoSystemQuery.getFieldVal(rowNo, InfosysOw1MESTASK.Row.META.ytnamebspr.getName()),
					infoSystemQuery.getFieldVal(rowNo, InfosysOw1MESTASK.Row.META.ytyname2.getName()),
					new BigDecimal(infoSystemQuery.getFieldVal(rowNo, InfosysOw1MESTASK.Row.META.ytmenge.getName())),
					infoSystemQuery.getFieldVal(rowNo, InfosysOw1MESTASK.Row.META.ytlebspr.getName()));
		}

	}

	/**
	 * Gyártási feladat részleteit leíró osztály, EDP-ben implementálva.
	 * @author szizo
	 */
	protected class DetailsEdpImpl extends TaskDetails<EDPSession> {

		/**
		 * Konstruktor.
		 * @param abasConnection Az Abas-kapcsolat.
		 */
		protected DetailsEdpImpl(AbasConnection<EDPSession> abasConnection) {
			super(abasConnection, abasConnectionType);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadBasicData()
		 */
		@Override
		protected void loadBasicData() {
			final EDPEditFieldList result = BasicDataQuery.EXECUTOR.executeQuery(workSlipId, abasConnectionObject).getHeaderFields();
			workSlipNo = result.getField(BasicDataQuery.Field.ID_NO).getValue();
			startDate = AbasDate.valueOf(result.getField(BasicDataQuery.Field.START_DATE).getValue());
			productIdNo = result.getField(BasicDataQuery.Field.PRODUCT_ID_NO).getValue();
			productSwd = result.getField(BasicDataQuery.Field.PRODUCT_SWD).getValue();
			productDescription = result.getField(BasicDataQuery.Field.PRODUCT_DESCRIPTION).getValue();
			productDescription2 = result.getField(BasicDataQuery.Field.PRODUCT_DESCRIPTION2).getValue();
			usage = result.getField(BasicDataQuery.Field.USAGE).getValue();
			setupTime = calculateGrossTime(new BigDecimal(result.getField(BasicDataQuery.Field.SETUP_TIME).getValue()),
					AbasUnit.UNITS.valueOf(result.getField(BasicDataQuery.Field.SETUP_TIME_UNIT).getValue()),
					new BigDecimal(result.getField(BasicDataQuery.Field.SETUP_TIME_SEC).getValue()));
			setupTimeUnit = result.getField(BasicDataQuery.Field.SETUP_TIME_UNIT_NAME).getValue();
			unitTime = calculateGrossTime(new BigDecimal(result.getField(BasicDataQuery.Field.UNIT_TIME).getValue()),
					AbasUnit.UNITS.valueOf(result.getField(BasicDataQuery.Field.UNIT_TIME_UNIT).getValue()),
					new BigDecimal(result.getField(BasicDataQuery.Field.UNIT_TIME_SEC).getValue()));
			unitTimeUnit = result.getField(BasicDataQuery.Field.UNIT_TIME_UNIT_NAME).getValue();
			numberOfExecutions = new BigDecimal(result.getField(BasicDataQuery.Field.NUMBER_OF_EXECUTIONS).getValue());
			outstandingQuantity = new BigDecimal(result.getField(BasicDataQuery.Field.OUTSTANDING_QUANTITY).getValue());
			stockUnit = result.getField(BasicDataQuery.Field.STOCK_UNIT_NAME).getValue();
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadOperationData()
		 */
		@Override
		protected void loadOperationData() {
			final EDPEditFieldList result = OperationQuery.EXECUTOR.executeQuery(workSlipId, abasConnectionObject).getHeaderFields();
			operationIdNo = result.getField(OperationQuery.Field.ID_NO).getValue();
			operationSwd = result.getField(OperationQuery.Field.SWD).getValue();
			operationDescription = result.getField(OperationQuery.Field.DESCRIPTION).getValue();
			operationReservationText = result.getField(OperationQuery.Field.ITEM_TEXT).getValue();
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadSalesOrderData()
		 */
		@Override
		protected void loadSalesOrderData() {
			// TODO
			throw new RuntimeException("Not yet implemented!");
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#getBillOfMaterials()
		 */
		@Override
		protected List<BomElement> getBillOfMaterials() {
			return BomQuery.EXECUTOR.getRows(workSlipId, abasConnectionObject);
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
	 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
	 * @param edpSession Az EDP-munkamenet.
	 */
	public TaskEdpImpl(Id workSlipId, EDPSession edpSession) {
		this(workSlipIdFilter(workSlipId, edpSession));
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
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
	 * @see phoenix.mes.abas.impl.TaskImpl#newDetails(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	protected DetailsEdpImpl newDetails(AbasConnection<EDPSession> abasConnection) {
		return (new DetailsEdpImpl(abasConnection));
	}

}
