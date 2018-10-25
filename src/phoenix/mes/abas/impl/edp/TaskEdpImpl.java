/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.CantChangeFieldValException;
import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESTASK;
import de.abas.erp.db.schema.workorder.WorkOrders;

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
		protected static EDPEditFieldList getFilterCriteria(String[] criteriaFieldNames, String workSlipId) {
			try {
				return new EDPEditFieldList(criteriaFieldNames, new String[] {workSlipId, "1", " "});
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
		 * @return A lekérdezendő (fejrész)mezők értékei.
		 */
		public FieldValues getResultFields(String workSlipId, EDPSession edpSession) {
			return getResultHeaderFields(getFilterCriteria(getCriteriaFieldNames(), workSlipId), edpSession);
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
			 * A gyártási feladat elkezdésének (tervezett) napja.
			 */
			public static final String START_DATE = InfosysOw1MESTASK.META.ytsterm.getName();

			/**
			 * A gyártási feladat fel van függesztve?
			 */
			public static final String SUSPENDED_TASK = InfosysOw1MESTASK.META.ymegszak.getName();

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
			 * A kalkulált gyártási átfutási idő (normaidő) munkaórában.
			 */
			public static final String CALCULATED_PRODUCTION_TIME = InfosysOw1MESTASK.META.yvzeit.getName();

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
	 * Segédosztály a kapcsolódó vevői rendelés adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class SalesOrderQuery extends TaskDataQuery {

		/**
		 * A lekérdezendő (fejrész)mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A kapcsolódó vevői rendeléstétel szabadszövege.
			 */
			public static final String ITEM_TEXT = InfosysOw1MESTASK.META.ypftext.getName();

			/**
			 * A kapcsolódó vevői rendeléstétel második szabadszövege.
			 */
			public static final String ITEM_TEXT2 = InfosysOw1MESTASK.META.yypftext.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASK.META.yas.getName(), InfosysOw1MESTASK.META.ymb.getName(), InfosysOw1MESTASK.META.start.getName()};

		/**
		 * Egyke objektum.
		 */
		public static final SalesOrderQuery EXECUTOR = new SalesOrderQuery();

		/**
		 * Konstruktor.
		 */
		private SalesOrderQuery() {
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
		public List<BomElement> getRows(String workSlipId, EDPSession edpSession) {
			final List<BomElement> rows = getRows(TaskDataQuery.getFilterCriteria(criteriaFieldNames, workSlipId), edpSession);
			return (1 < rows.size() ? Collections.unmodifiableList(rows) : rows);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected BomElementImpl createRowObject(FieldValues rowData) {
			return new BomElementImpl(rowData.getString(Field.ID_NO),
					rowData.getString(Field.SWD),
					rowData.getString(Field.DESCRIPTION),
					rowData.getString(Field.DESCRIPTION2),
					rowData.getBigDecimal(Field.QUANTITY_PER_PRODUCT),
					rowData.getString(Field.STOCK_UNIT_NAME));
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
			final InfoSystemExecutor.FieldValues result = BasicDataQuery.EXECUTOR.getResultFields(workSlipId, abasConnectionObject);
			workSlipNo = result.getString(BasicDataQuery.Field.ID_NO);
			startDate = result.getAbasDate(BasicDataQuery.Field.START_DATE);
			suspendedTask = result.getBoolean(BasicDataQuery.Field.SUSPENDED_TASK);
			productIdNo = result.getString(BasicDataQuery.Field.PRODUCT_ID_NO);
			productSwd = result.getString(BasicDataQuery.Field.PRODUCT_SWD);
			productDescription = result.getString(BasicDataQuery.Field.PRODUCT_DESCRIPTION);
			productDescription2 = result.getString(BasicDataQuery.Field.PRODUCT_DESCRIPTION2);
			usage = result.getString(BasicDataQuery.Field.USAGE);
			setupTime = calculateGrossTime(result.getBigDecimal(BasicDataQuery.Field.SETUP_TIME),
					result.getAbasUnit(BasicDataQuery.Field.SETUP_TIME_UNIT),
					result.getBigDecimal(BasicDataQuery.Field.SETUP_TIME_SEC));
			setupTimeUnit = result.getString(BasicDataQuery.Field.SETUP_TIME_UNIT_NAME);
			unitTime = calculateGrossTime(result.getBigDecimal(BasicDataQuery.Field.UNIT_TIME),
					result.getAbasUnit(BasicDataQuery.Field.UNIT_TIME_UNIT),
					result.getBigDecimal(BasicDataQuery.Field.UNIT_TIME_SEC));
			unitTimeUnit = result.getString(BasicDataQuery.Field.UNIT_TIME_UNIT_NAME);
			numberOfExecutions = result.getBigDecimal(BasicDataQuery.Field.NUMBER_OF_EXECUTIONS);
			outstandingQuantity = result.getBigDecimal(BasicDataQuery.Field.OUTSTANDING_QUANTITY);
			stockUnit = result.getString(BasicDataQuery.Field.STOCK_UNIT_NAME);
			calculatedProductionTime = result.getBigDecimal(BasicDataQuery.Field.CALCULATED_PRODUCTION_TIME);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadOperationData()
		 */
		@Override
		protected void loadOperationData() {
			final InfoSystemExecutor.FieldValues result = OperationQuery.EXECUTOR.getResultFields(workSlipId, abasConnectionObject);
			operationIdNo = result.getString(OperationQuery.Field.ID_NO);
			operationSwd = result.getString(OperationQuery.Field.SWD);
			operationDescription = result.getString(OperationQuery.Field.DESCRIPTION);
			operationReservationText = result.getString(OperationQuery.Field.ITEM_TEXT);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#loadSalesOrderData()
		 */
		@Override
		protected void loadSalesOrderData() {
			final InfoSystemExecutor.FieldValues result = SalesOrderQuery.EXECUTOR.getResultFields(workSlipId, abasConnectionObject);
			salesOrderItemText = result.getString(SalesOrderQuery.Field.ITEM_TEXT);
			salesOrderItemText2 = result.getString(SalesOrderQuery.Field.ITEM_TEXT2);
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
		this(workSlipIdFilter(workSlipId, edpSession).toString());
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
	 */
	TaskEdpImpl(String workSlipId) {
		super(workSlipId, EDPSession.class);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.impl.TaskImpl#newDetails(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	protected DetailsEdpImpl newDetails(AbasConnection<EDPSession> abasConnection) {
		return (new DetailsEdpImpl(abasConnection));
	}

}
