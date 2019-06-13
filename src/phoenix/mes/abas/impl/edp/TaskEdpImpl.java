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
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1FRUECKMELDUNG;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESTASKADMIN;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESTASKDATA;
import de.abas.erp.db.schema.workorder.WorkOrders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.WorkStation;
import phoenix.mes.abas.impl.TaskImpl;
import phoenix.mes.abas.impl.AbasFunctionExecutionException;
import phoenix.mes.abas.impl.BomElementImpl;
import phoenix.mes.abas.impl.InvalidAbasFunctionCallException;
import phoenix.mes.abas.impl.OperationImpl;
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
			super("MESTASKDATA", headerFieldNamesClass, null);
		}

		/**
		 * A lekérdezés végrehajtása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A lekérdezendő (fejrész)mezők értékei (null, ha a munkalap nem létezik).
		 */
		public FieldValues getResultFields(String workSlipId, EDPSession edpSession) {
			final EDPEditFieldList filterCriteria = getFilterCriteria(getCriteriaFieldNames(), workSlipId);
			try {
				return getResultHeaderFields(filterCriteria, edpSession);
			} catch (EDPRuntimeException e) {
				if (e.getCause() instanceof CantChangeFieldValException) {
					return null;
				}
				throw e;
			}
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
			public static final String ID_NO = InfosysOw1MESTASKDATA.META.ynum9.getName();

			/**
			 * A gyártási feladat elkezdésének (tervezett) napja.
			 */
			public static final String START_DATE = InfosysOw1MESTASKDATA.META.ytsterm.getName();

			/**
			 * A gyártási feladat tényleges elkezdésének időpontja.
			 */
			public static final String START_TIME = InfosysOw1MESTASKDATA.META.yysts.getName();

			/**
			 * A gyártási feladat végrehajtása valamilyen zavar miatt félbe lett szakítva?
			 */
			public static final String INTERRUPTED_TASK = InfosysOw1MESTASKDATA.META.yzavar.getName();

			/**
			 * A gyártási feladat fel van függesztve?
			 */
			public static final String SUSPENDED_TASK = InfosysOw1MESTASKDATA.META.ymegszak.getName();

			/**
			 * A termék Felhasználás-hivatkozása.
			 */
			public static final String USAGE = InfosysOw1MESTASKDATA.META.yverw.getName();

			/**
			 * A beállítási idő mértékegysége.
			 */
			public static final String SETUP_TIME_UNIT = InfosysOw1MESTASKDATA.META.yzr.getName();

			/**
			 * A beállítási idő mértékegységének neve.
			 */
			public static final String SETUP_TIME_UNIT_NAME = InfosysOw1MESTASKDATA.META.yzrbspr.getName();

			/**
			 * A beállítási idő.
			 */
			public static final String SETUP_TIME = InfosysOw1MESTASKDATA.META.ytr.getName();

			/**
			 * Egységnyi beállítási idő bruttósítva hány másodpercből áll?
			 */
			public static final String SETUP_TIME_SEC = InfosysOw1MESTASKDATA.META.yzrsek.getName();

			/**
			 * A darabidő mértékegysége.
			 */
			public static final String UNIT_TIME_UNIT = InfosysOw1MESTASKDATA.META.yze.getName();

			/**
			 * A darabidő mértékegységének neve.
			 */
			public static final String UNIT_TIME_UNIT_NAME = InfosysOw1MESTASKDATA.META.yzebspr.getName();

			/**
			 * A darabidő.
			 */
			public static final String UNIT_TIME = InfosysOw1MESTASKDATA.META.yte.getName();

			/**
			 * Egységnyi darabidő bruttósítva hány másodpercből áll?
			 */
			public static final String UNIT_TIME_SEC = InfosysOw1MESTASKDATA.META.yzesek.getName();

			/**
			 * Hányszor kell végrehajtani a műveletet?
			 */
			public static final String NUMBER_OF_EXECUTIONS = InfosysOw1MESTASKDATA.META.yanzahl.getName();

			/**
			 * A nyitott mennyiség.
			 */
			public static final String OUTSTANDING_QUANTITY = InfosysOw1MESTASKDATA.META.ymge.getName();

			/**
			 * A kalkulált gyártási átfutási idő (normaidő) munkaórában.
			 */
			public static final String CALCULATED_PRODUCTION_TIME = InfosysOw1MESTASKDATA.META.yvzeit.getName();

			/**
			 * A termék cikkszáma.
			 */
			public static final String PRODUCT_ID_NO = InfosysOw1MESTASKDATA.META.ytenum.getName();

			/**
			 * A termék keresőszava.
			 */
			public static final String PRODUCT_SWD = InfosysOw1MESTASKDATA.META.ytesuch.getName();

			/**
			 * A termék megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String PRODUCT_DESCRIPTION = InfosysOw1MESTASKDATA.META.ytenamebspr.getName();

			/**
			 * A termék második megnevezése.
			 */
			public static final String PRODUCT_DESCRIPTION2 = InfosysOw1MESTASKDATA.META.yyname2.getName();

			/**
			 * A mennyiségi egység (raktáregység) neve.
			 */
			public static final String STOCK_UNIT_NAME = InfosysOw1MESTASKDATA.META.ylebspr.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.yalap.getName(), InfosysOw1MESTASKDATA.META.start.getName()};

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
			public static final String ITEM_TEXT = InfosysOw1MESTASKDATA.META.ypftext.getName();

			/**
			 * A kapcsolódó vevői rendeléstétel második szabadszövege.
			 */
			public static final String ITEM_TEXT2 = InfosysOw1MESTASKDATA.META.yypftext.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ymb.getName(), InfosysOw1MESTASKDATA.META.start.getName()};

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
			public static final String ID_NO = InfosysOw1MESTASKDATA.META.yagnum.getName();

			/**
			 * A művelet keresőszava.
			 */
			public static final String SWD = InfosysOw1MESTASKDATA.META.yagsuch.getName();

			/**
			 * A művelet megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = InfosysOw1MESTASKDATA.META.yagnamebspr.getName();

			/**
			 * A műveletfoglalás tételszövege.
			 */
			public static final String ITEM_TEXT = InfosysOw1MESTASKDATA.META.yptext.getName();

			/**
			 * A nyitott lejelentési mennyiség.
			 */
			public static final String OUTSTANDING_CONFIRMATION_QUANTITY = InfosysOw1MESTASKDATA.META.ynykonyvmenny.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A szűrőmezők nevei.
		 */
		protected static final String[] criteriaFieldNames = {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ymuv.getName(), InfosysOw1MESTASKDATA.META.start.getName()};

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
	 * Alaposztály gyártásilista-sorok lekérdezéséhez.
	 * @param <R> A táblázatsorokból készített objektumok típusa.
	 * @author szizo
	 */
	protected static abstract class ProductionListQuery<R> extends InfoSystemTableConverter<R> {

		/**
		 * A szűrőmezők nevei.
		 */
		protected final String[] criteriaFieldNames;

		/**
		 * Konstruktor.
		 * @param criteriaFieldNames A szűrőmezők nevei.
		 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály.
		 */
		public ProductionListQuery(String[] criteriaFieldNames, Class<?> tableFieldNamesClass) {
			super("MESTASKDATA", tableFieldNamesClass);
			this.criteriaFieldNames = criteriaFieldNames;
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A szűrésnek megfelelő gyártásilista-sorok adatait tartalmazó objektumok listája (null, ha a munkalap nem létezik).
		 */
		public List<R> getRows(String workSlipId, EDPSession edpSession) {
			final EDPEditFieldList filterCriteria = TaskDataQuery.getFilterCriteria(criteriaFieldNames, workSlipId);
			final List<R> rows;
			try {
				rows = getRows(filterCriteria, edpSession);
			} catch (EDPRuntimeException e) {
				if (e.getCause() instanceof CantChangeFieldValException) {
					return null;
				}
				throw e;
			}
			return (1 < rows.size() ? Collections.unmodifiableList(rows) : rows);
		}

	}

	/**
	 * Segédosztály a gyártási feladathoz kapcsolódó darabjegyzék adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class BomQuery extends ProductionListQuery<BomElement> {

		/**
		 * A lekérdezendő (táblázati) mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A beépülő cikk hivatkozási száma.
			 */
			public static final String ID_NO = InfosysOw1MESTASKDATA.Row.META.ytelemnum.getName();

			/**
			 * A beépülő cikk keresőszava.
			 */
			public static final String SWD = InfosysOw1MESTASKDATA.Row.META.ytelemsuch.getName();

			/**
			 * A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = InfosysOw1MESTASKDATA.Row.META.ytnamebspr.getName();

			/**
			 * A beépülő cikk második megnevezése.
			 */
			public static final String DESCRIPTION2 = InfosysOw1MESTASKDATA.Row.META.ytyname2.getName();

			/**
			 * A beépülési mennyiség (egy késztermékre vonatkozóan).
			 */
			public static final String QUANTITY_PER_PRODUCT = InfosysOw1MESTASKDATA.Row.META.ytmenge.getName();

			/**
			 * A beépülési mennyiség egységének (raktáregység) neve.
			 */
			public static final String STOCK_UNIT_NAME = InfosysOw1MESTASKDATA.Row.META.ytlebspr.getName();

			/**
			 * A tételszöveg.
			 */
			public static final String ITEM_TEXT = InfosysOw1MESTASKDATA.Row.META.ytptext.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final BomQuery EXECUTOR = new BomQuery();

		/**
		 * Konstruktor.
		 */
		private BomQuery() {
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ydbj.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, Field.class);
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
					rowData.getString(Field.STOCK_UNIT_NAME),
					rowData.getString(Field.ITEM_TEXT));
		}

	}

	/**
	 * Segédosztály a gyártási feladatot követő műveletek listájának lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class FollowingOperationsQuery extends ProductionListQuery<Operation> {

		/**
		 * A lekérdezendő (táblázati) mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A művelet hivatkozási száma.
			 */
			public static final String ID_NO = InfosysOw1MESTASKDATA.Row.META.ytelemnum.getName();

			/**
			 * A művelet keresőszava.
			 */
			public static final String SWD = InfosysOw1MESTASKDATA.Row.META.ytelemsuch.getName();

			/**
			 * A művelet megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = InfosysOw1MESTASKDATA.Row.META.ytnamebspr.getName();

			/**
			 * A művelethez rendelt gépcsoport hivatkozási száma.
			 */
			public static final String WORK_CENTER_ID_NO = InfosysOw1MESTASKDATA.Row.META.ytmgrnum.getName();

			/**
			 * A művelethez rendelt gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String WORK_CENTER_DESCRIPTION = InfosysOw1MESTASKDATA.Row.META.ytmgrbspr.getName();

			/**
			 * A műveletsor tételszövege a gyártási listában.
			 */
			public static final String ITEM_TEXT = InfosysOw1MESTASKDATA.Row.META.ytptext.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final FollowingOperationsQuery EXECUTOR = new FollowingOperationsQuery();

		/**
		 * Konstruktor.
		 */
		private FollowingOperationsQuery() {
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ykovmuv.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, Field.class);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected Operation createRowObject(FieldValues rowData) {
			return new OperationImpl(rowData.getString(Field.ID_NO),
					rowData.getString(Field.SWD),
					rowData.getString(Field.DESCRIPTION),
					rowData.getString(Field.WORK_CENTER_ID_NO),
					rowData.getString(Field.WORK_CENTER_DESCRIPTION),
					rowData.getString(Field.ITEM_TEXT));
		}

	}

	/**
	 * Segédosztály a gyártási feladathoz kapcsolódó műveletek elvégzéséhez.
	 * @author szizo
	 */
	protected static final class TaskManager extends InfoSystemExecutor {

		/**
		 * A gyártási feladat beütemezésekor beállítandó mezők nevei.
		 */
		protected static final String[] scheduleInputFieldNames = {InfosysOw1MESTASKADMIN.META.yas.getName(), InfosysOw1MESTASKADMIN.META.ymgr.getName(), InfosysOw1MESTASKADMIN.META.ygepszam.getName(), InfosysOw1MESTASKADMIN.META.yas0.getName(), InfosysOw1MESTASKADMIN.META.ybeterv.getName()};

		/**
		 * A gyártási feladat beütemezésének visszavonásakor ill. felfüggesztésekor beállítandó mezők nevei.
		 */
		protected static final String[] unScheduleInputFieldNames = {InfosysOw1MESTASKADMIN.META.yas.getName(), InfosysOw1MESTASKADMIN.META.ymegszak.getName(), InfosysOw1MESTASKADMIN.META.yvisszavon.getName()};

		/**
		 * A gyártási feladat végrehajtásának félbeszakításakor ill. a félbeszakítás visszavonásakor beállítandó mezők nevei.
		 */
		protected static final String[] interruptInputFieldNames = {InfosysOw1MESTASKADMIN.META.yas.getName(), InfosysOw1MESTASKADMIN.META.yzavar.getName(), InfosysOw1MESTASKADMIN.META.yzavarjel.getName()};

		/**
		 * A gyártási feladat lejelentésekor beállítandó mezők nevei.
		 */
		protected static final String[] completionConfirmationFieldNames = {InfosysOw1FRUECKMELDUNG.META.yas.getName(), InfosysOw1FRUECKMELDUNG.META.ygutmge.getName(), InfosysOw1FRUECKMELDUNG.META.yverlust.getName(), InfosysOw1FRUECKMELDUNG.META.ylejel.getName()};

		/**
		 * A funkcióhívás eredményét jelző mező neve.
		 */
		protected static final String resultFieldName = InfosysOw1MESTASKADMIN.META.yeredmeny.getName();

		/**
		 * A gyártási feladat ütemezését végző objektum.
		 */
		protected static final TaskManager SCHEDULER;

		/**
		 * A gyártási feladat lejelentését végző objektum.
		 */
		protected static final TaskManager CONFIRMER;

		/**
		 * Statikus konstruktor.
		 */
		static {
			final String[] headerFieldNames = new String[] {resultFieldName};
			SCHEDULER = new TaskManager("MESTASKADMIN", headerFieldNames);
			CONFIRMER = new TaskManager("FRUECKMELDUNG", headerFieldNames);
		}

		/**
		 * A megadott gyártási feladat beütemezése egy konkrét munkaállomásra.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param workStation A munkaállomás.
		 * @param precedingWorkSlipId A munkaállomáson közvetlenül a beütemezendő gyártási feladat előtt végrehajtandó munkalap azonosítója (üres (vagy üres azonosító), ha a beütemezendő gyártási feladat az első a végrehajtási listában).
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void scheduleTask(String workSlipId, WorkStation workStation, String precedingWorkSlipId, AbasConnection<?> abasConnection) throws AbasFunctionException {
			SCHEDULER.executeAbasFunction(scheduleInputFieldNames, new String[] {workSlipId, workStation.getWorkCenterId().toString(), Integer.toString(workStation.getNumber()), precedingWorkSlipId, " "}, abasConnection);
		}

		/**
		 * A megadott gyártási feladat beütemezésének visszavonása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void unScheduleTask(String workSlipId, AbasConnection<?> abasConnection) throws AbasFunctionException {
			unScheduleTask(workSlipId, false, abasConnection);
		}

		/**
		 * A megadott gyártási feladat beütemezésének visszavonása ill. felfüggesztése.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param suspend A gyártási feladat felfüggesztésre kerül?
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		protected static void unScheduleTask(String workSlipId, boolean suspend, AbasConnection<?> abasConnection) throws AbasFunctionException {
			SCHEDULER.executeAbasFunction(unScheduleInputFieldNames, new String[] {workSlipId, suspend ? "1" : "0", " "}, abasConnection);
		}

		/**
		 * A megadott gyártási feladat végrehajtásának félbeszakítása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void interruptTask(String workSlipId, AbasConnection<?> abasConnection) throws AbasFunctionException {
			interruptTask(workSlipId, true, abasConnection);
		}

		/**
		 * A megadott gyártási feladat végrehajtásának félbeszakítása ill. a félbeszakítás visszavonása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param interrupt A gyártás feladat végrehajtása félbeszakításra kerül?
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		protected static void interruptTask(String workSlipId, boolean interrupt, AbasConnection<?> abasConnection) throws AbasFunctionException {
			SCHEDULER.executeAbasFunction(interruptInputFieldNames, new String[] {workSlipId, interrupt ? "1" : "0", " "}, abasConnection);
		}

		/**
		 * A megadott gyártási feladat végrehajtásának folytatása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void resumeTask(String workSlipId, AbasConnection<?> abasConnection) throws AbasFunctionException {
			interruptTask(workSlipId, false, abasConnection);
		}

		/**
		 * A megadott gyártási feladat lejelentése.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param yield Az elkészült jó mennyiség.
		 * @param scrapQuantity A keletkezett selejt mennyisége.
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void postCompletionConfirmation(String workSlipId, BigDecimal yield, BigDecimal scrapQuantity, AbasConnection<?> abasConnection) throws AbasFunctionException {
			CONFIRMER.executeAbasFunction(completionConfirmationFieldNames, new String[] {workSlipId, yield.toPlainString(), scrapQuantity.toPlainString(), " "}, abasConnection);
		}

		/**
		 * A megadott gyártási feladat felfüggesztése.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void suspendTask(String workSlipId, AbasConnection<?> abasConnection) throws AbasFunctionException {
			unScheduleTask(workSlipId, true, abasConnection);
		}

		/**
		 * Konstruktor.
		 * @param swd Az infosystem keresőszava.
		 * @param headerFieldNames A lekérdezendő fejrészmezők nevei (null, ha nincs lekérdezendő fejrészmező).
		 */
		private TaskManager(String swd, String[] headerFieldNames) {
			super(swd, new String[] {resultFieldName}, null);
		}

		/**
		 * Funkcióhívás (lekérdezés) végrehajtása.
		 * @param inputFieldNames Az eredmény lekérdezése előtt beállítandó mezők nevei.
		 * @param inputFieldValues Az eredmény lekérdezése előtt beállítandó mezők értékei.
		 * @param abasConnection Az Abas-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		protected void executeAbasFunction(String[] inputFieldNames, String[] inputFieldValues, AbasConnection<?> abasConnection) throws AbasFunctionException {
			try {
				final int errorCode = getResultHeaderFields(new EDPEditFieldList(inputFieldNames, inputFieldValues), EdpConnection.getEdpSession(abasConnection)).getInt(resultFieldName);
				if (0 == errorCode) {
					return;
				}
				if (8 > errorCode) {
					throw new InvalidAbasFunctionCallException(errorCode);
				}
				throw new AbasFunctionExecutionException(errorCode);
			} catch (CantChangeFieldValException e) {
				throw new InvalidAbasFunctionCallException(1, e.getLocalizedMessage(), e);
			}
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
		 * @see phoenix.mes.abas.impl.TaskDetails#newBasicData()
		 */
		@Override
		protected BasicData newBasicData() {
			final InfoSystemExecutor.FieldValues result = BasicDataQuery.EXECUTOR.getResultFields(workSlipId, abasConnectionObject);
			final BasicData basicData = new BasicData();
			if (null == result) {
				basicData.status = Status.DELETED;
			} else {
				basicData.workSlipNo = result.getString(BasicDataQuery.Field.ID_NO);
				basicData.startDate = result.getAbasDate(BasicDataQuery.Field.START_DATE);
				basicData.status = getStatus(result);
				basicData.productIdNo = result.getString(BasicDataQuery.Field.PRODUCT_ID_NO);
				basicData.productSwd = result.getString(BasicDataQuery.Field.PRODUCT_SWD);
				basicData.productDescription = result.getString(BasicDataQuery.Field.PRODUCT_DESCRIPTION);
				basicData.productDescription2 = result.getString(BasicDataQuery.Field.PRODUCT_DESCRIPTION2);
				basicData.usage = result.getString(BasicDataQuery.Field.USAGE);
				basicData.setupTime = calculateGrossTime(result.getBigDecimal(BasicDataQuery.Field.SETUP_TIME),
						result.getAbasUnit(BasicDataQuery.Field.SETUP_TIME_UNIT),
						result.getBigDecimal(BasicDataQuery.Field.SETUP_TIME_SEC));
				basicData.setupTimeUnit = result.getString(BasicDataQuery.Field.SETUP_TIME_UNIT_NAME);
				basicData.unitTime = calculateGrossTime(result.getBigDecimal(BasicDataQuery.Field.UNIT_TIME),
						result.getAbasUnit(BasicDataQuery.Field.UNIT_TIME_UNIT),
						result.getBigDecimal(BasicDataQuery.Field.UNIT_TIME_SEC));
				basicData.unitTimeUnit = result.getString(BasicDataQuery.Field.UNIT_TIME_UNIT_NAME);
				basicData.numberOfExecutions = result.getBigDecimal(BasicDataQuery.Field.NUMBER_OF_EXECUTIONS);
				basicData.outstandingQuantity = result.getBigDecimal(BasicDataQuery.Field.OUTSTANDING_QUANTITY);
				basicData.stockUnit = result.getString(BasicDataQuery.Field.STOCK_UNIT_NAME);
				basicData.calculatedProductionTime = result.getBigDecimal(BasicDataQuery.Field.CALCULATED_PRODUCTION_TIME);
			}
			return basicData;
		}

		/**
		 * @param basicDataQueryResult A gyártási feladat alapadatai lekérdezésének eredménye.
		 * @return A gyártási feladat végrehajtási állapota.
		 */
		protected Status getStatus(InfoSystemExecutor.FieldValues basicDataQueryResult) {
			if (0 <= BigDecimal.ZERO.compareTo(basicDataQueryResult.getBigDecimal(BasicDataQuery.Field.OUTSTANDING_QUANTITY))) {
				return Status.DONE;
			}
			if (basicDataQueryResult.getBoolean(BasicDataQuery.Field.INTERRUPTED_TASK)) {
				return Status.INTERRUPTED;
			}
			if (!basicDataQueryResult.getString(BasicDataQuery.Field.START_TIME).isEmpty()) {
				return Status.IN_PROGRESS;
			}
			if (basicDataQueryResult.getBoolean(BasicDataQuery.Field.SUSPENDED_TASK)) {
				return Status.SUSPENDED;
			}
			return Status.WAITING;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newOperationData()
		 */
		@Override
		protected OperationData newOperationData() {
			final InfoSystemExecutor.FieldValues result = OperationQuery.EXECUTOR.getResultFields(workSlipId, abasConnectionObject);
			final OperationData operationData = new OperationData();
			if (null != result) {
				operationData.operationIdNo = result.getString(OperationQuery.Field.ID_NO);
				operationData.operationSwd = result.getString(OperationQuery.Field.SWD);
				operationData.operationDescription = result.getString(OperationQuery.Field.DESCRIPTION);
				operationData.operationReservationText = result.getString(OperationQuery.Field.ITEM_TEXT);
				operationData.outstandingConfirmationQuantity = result.getBigDecimal(OperationQuery.Field.OUTSTANDING_CONFIRMATION_QUANTITY);
			}
			return operationData;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newSalesOrderData()
		 */
		@Override
		protected SalesOrderData newSalesOrderData() {
			final InfoSystemExecutor.FieldValues result = SalesOrderQuery.EXECUTOR.getResultFields(workSlipId, abasConnectionObject);
			final SalesOrderData salesOrderData = new SalesOrderData();
			if (null != result) {
				salesOrderData.salesOrderItemText = result.getString(SalesOrderQuery.Field.ITEM_TEXT);
				salesOrderData.salesOrderItemText2 = result.getString(SalesOrderQuery.Field.ITEM_TEXT2);
			}
			return salesOrderData;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newBom()
		 */
		@Override
		protected List<BomElement> newBom() {
			return BomQuery.EXECUTOR.getRows(workSlipId, abasConnectionObject);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newFollowingOperations()
		 */
		@Override
		protected List<Operation> newFollowingOperations() {
			return FollowingOperationsQuery.EXECUTOR.getRows(workSlipId, abasConnectionObject);
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
		return new DetailsEdpImpl(abasConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#schedule(phoenix.mes.abas.WorkStation, de.abas.erp.common.type.Id, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void schedule(WorkStation workStation, Id precedingWorkSlipId, AbasConnection<?> abasConnection) throws AbasFunctionException {
		TaskManager.scheduleTask(workSlipId, workStation, precedingWorkSlipId.toString(), abasConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#unSchedule(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void unSchedule(AbasConnection<?> abasConnection) throws AbasFunctionException {
		TaskManager.unScheduleTask(workSlipId, abasConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#interrupt(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void interrupt(AbasConnection<?> abasConnection) throws AbasFunctionException {
		TaskManager.interruptTask(workSlipId, abasConnection);
		clearDetailsStatusCache();
	}

	/**
	 * A gyártási feladat végrehajtási állapotát tartalmazó gyorsítótár kiürítése.
	 */
	protected void clearDetailsStatusCache() {
		if (null != details) {
			details.clearStatusCache();
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#resume(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void resume(AbasConnection<?> abasConnection) throws AbasFunctionException {
		TaskManager.resumeTask(workSlipId, abasConnection);
		clearDetailsStatusCache();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#postCompletionConfirmation(java.math.BigDecimal, java.math.BigDecimal, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void postCompletionConfirmation(BigDecimal yield, BigDecimal scrapQuantity, AbasConnection<?> abasConnection) throws AbasFunctionException {
		TaskManager.postCompletionConfirmation(workSlipId, yield, scrapQuantity, abasConnection);
		if (null != details) {
			details.clearBasicDataCache();
			details.clearOperationDataCache();
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#suspend(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void suspend(AbasConnection<?> abasConnection) throws AbasFunctionException {
		TaskManager.suspendTask(workSlipId, abasConnection);
		clearDetailsStatusCache();
	}

}
