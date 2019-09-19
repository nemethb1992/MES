/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.CantChangeFieldValException;
import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPEditObject;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1FRUECKMELDUNG;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESCHB;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESDOCLIST;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESTASKADMIN;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESTASKDATA;
import de.abas.erp.db.schema.workorder.WorkOrders;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import phoenix.mes.abas.GenericAbasConnection;
import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.abas.GenericWorkStation;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.impl.TaskImpl;
import phoenix.mes.abas.impl.AbasFunctionExecutionException;
import phoenix.mes.abas.impl.BomElementImpl;
import phoenix.mes.abas.impl.CharacteristicImpl;
import phoenix.mes.abas.impl.DocumentImpl;
import phoenix.mes.abas.impl.InvalidAbasFunctionCallException;
import phoenix.mes.abas.impl.OperationImpl;
import phoenix.mes.abas.impl.TaskDetails;

/**
 * Gyártási feladat osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class TaskEdpImpl extends TaskImpl<EDPSession> implements Task {

	/**
	 * Alaposztály a gyártási feladathoz kapcsolódó adatok lekérdezéséhez.
	 * @param <R> A táblázatsorokból készített objektumok típusa.
	 * @author szizo
	 */
	protected static abstract class InfoSystemQuery<R> extends InfoSystemTableConverter<R> {

		/**
		 * A szűrőmezők nevei.
		 */
		protected final String[] criteriaFieldNames;

		/**
		 * Konstruktor.
		 * @param swd Az infosystem keresőszava.
		 * @param criteriaFieldNames A szűrőmezők nevei.
		 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály.
		 */
		public InfoSystemQuery(String swd, String[] criteriaFieldNames, Class<?> tableFieldNamesClass) {
			this(swd, criteriaFieldNames, null, tableFieldNamesClass);
		}

		/**
		 * Konstruktor.
		 * @param swd Az infosystem keresőszava.
		 * @param criteriaFieldNames A szűrőmezők nevei.
		 * @param headerFieldNamesClass A lekérdezendő fejrészmezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő fejrészmező).
		 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő táblázati mező).
		 */
		public InfoSystemQuery(String swd, String[] criteriaFieldNames, Class<?> headerFieldNamesClass, Class<?> tableFieldNamesClass) {
			super(swd, headerFieldNamesClass, tableFieldNamesClass);
			this.criteriaFieldNames = criteriaFieldNames;
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return Az infosystem lefuttatásának eredménye (null, ha a munkalap nem létezik).
		 */
		public InfoSystemResult getResult(String workSlipId, EDPSession edpSession) {
			final EDPEditObject result = executeQuery(workSlipId, edpSession);
			return (null == result ? null : getResult(result));
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A szűrésnek megfelelő gyártásilista-sorok adatait tartalmazó objektumok listája (null, ha a munkalap nem létezik).
		 */
		public List<R> getRows(String workSlipId, EDPSession edpSession) {
			final EDPEditObject result = executeQuery(workSlipId, edpSession);
			return (null == result ? null : getRows(result));
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#getRows(de.abas.ceks.jedp.EDPEditObject)
		 */
		@Override
		protected List<R> getRows(EDPEditObject result) {
			final List<R> rows = super.getRows(result);
			return (1 < rows.size() ? Collections.unmodifiableList(rows) : rows);
		}

		/**
		 * A lekérdezés végrehajtása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A lekérdezendő mezők értékei az infosystem lefuttatása után (null, ha a munkalap nem létezik).
		 */
		protected EDPEditObject executeQuery(String workSlipId, EDPSession edpSession) {
			final EDPEditFieldList filterCriteria = getFilterCriteria(workSlipId);
			try {
				return executeQuery(filterCriteria, edpSession);
			} catch (EDPRuntimeException e) {
				if (e.getCause() instanceof CantChangeFieldValException) {
					return null;
				}
				throw e;
			}
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @return Az infosystem indításához szükséges mezőbeállítások.
		 */
		protected EDPEditFieldList getFilterCriteria(String workSlipId) {
			try {
				return new EDPEditFieldList(criteriaFieldNames, getCriteriaFieldValues(workSlipId));
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @return A szűrőmezők értékei.
		 */
		protected abstract String[] getCriteriaFieldValues(String workSlipId);

	}

	/**
	 * Alaposztály a gyártási feladat adatainak lekérdezéséhez.
	 * @param <R> A táblázatsorokból készített objektumok típusa.
	 * @author szizo
	 */
	protected static abstract class TaskDataQuery<R> extends InfoSystemQuery<R> {

		/**
		 * Konstruktor.
		 * @param criteriaFieldNames A szűrőmezők nevei.
		 * @param headerFieldNamesClass A lekérdezendő fejrészmezők neveit konstansokként tartalmazó osztály.
		 */
		public TaskDataQuery(String[] criteriaFieldNames, Class<?> headerFieldNamesClass) {
			this(criteriaFieldNames, headerFieldNamesClass, null);
		}

		/**
		 * Konstruktor.
		 * @param criteriaFieldNames A szűrőmezők nevei.
		 * @param headerFieldNamesClass A lekérdezendő fejrészmezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő fejrészmező).
		 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő táblázati mező).
		 */
		public TaskDataQuery(String[] criteriaFieldNames, Class<?> headerFieldNamesClass, Class<?> tableFieldNamesClass) {
			super("MESTASKDATA", criteriaFieldNames, headerFieldNamesClass, tableFieldNamesClass);
		}

		/**
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A lekérdezendő fejrészmezők értékei (null, ha a munkalap nem létezik).
		 */
		public FieldValues getResultHeaderFields(String workSlipId, EDPSession edpSession) {
			final EDPEditObject result = executeQuery(workSlipId, edpSession);
			return (null == result ? null : getResultHeaderFields(result));
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.TaskEdpImpl.InfoSystemQuery#getCriteriaFieldValues(java.lang.String)
		 */
		@Override
		protected String[] getCriteriaFieldValues(String workSlipId) {
			return new String[] {workSlipId, "1", " "};
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected R createRowObject(FieldValues rowData) {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Segédosztály a gyártási feladat alapadatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class BasicDataQuery extends TaskDataQuery<Void> {

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
			 * A gyártási feladat befejezésének (tervezett) napja.
			 */
			public static final String FINISH_DATE = InfosysOw1MESTASKDATA.META.ytterm.getName();

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
			 * A csomagolási utasítás keresőszava.
			 */
			public static final String PACKING_INSTRUCTION_SWD = InfosysOw1MESTASKDATA.META.ypackanwstdsuch.getName();

			/**
			 * A csomagolási mennyiség.
			 */
			public static final String FILLING_QUANTITY = InfosysOw1MESTASKDATA.META.yfmengestd.getName();

			/**
			 * A csomagolóeszköz hivatkozási száma.
			 */
			public static final String PACKAGING_MATERIAL_ID_NO = InfosysOw1MESTASKDATA.META.ypmstdnum.getName();

			/**
			 * A csomagolóeszköz megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String PACKAGING_MATERIAL_DESCRIPTION = InfosysOw1MESTASKDATA.META.ypmstdnamebspr.getName();

			/**
			 * A munkautasítás.
			 */
			public static final String WORK_INSTRUCTION = InfosysOw1MESTASKDATA.META.yanweis.getName();

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
		 * Egyke objektum.
		 */
		public static final BasicDataQuery EXECUTOR = new BasicDataQuery();

		/**
		 * Konstruktor.
		 */
		private BasicDataQuery() {
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.yalap.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, Field.class);
		}

	}

	/**
	 * Segédosztály a kapcsolódó vevői rendelés adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class SalesOrderQuery extends TaskDataQuery<Void> {

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
		 * Egyke objektum.
		 */
		public static final SalesOrderQuery EXECUTOR = new SalesOrderQuery();

		/**
		 * Konstruktor.
		 */
		private SalesOrderQuery() {
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ymb.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, Field.class);
		}

	}

	/**
	 * Segédosztály a művelet adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class OperationQuery extends TaskDataQuery<Void> {

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
		 * Egyke objektum.
		 */
		public static final OperationQuery EXECUTOR = new OperationQuery();

		/**
		 * Konstruktor.
		 */
		private OperationQuery() {
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ymuv.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, Field.class);
		}

	}

	/**
	 * Segédosztály a gyártott cikk műszakiparaméter-jegyzékének lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class CharacteristicsBarQuery extends InfoSystemQuery<Characteristic> {

		/**
		 * A lekérdezendő táblázati mezők nevei.
		 * @author szizo
		 */
		public static final class TableField {

			/**
			 * A műszaki paraméter neve az aktuálisan beállított kezelőnyelven.
			 */
			public static final String NAME = InfosysOw1MESCHB.Row.META.ytbenennbspr.getName();

			/**
			 * A műszaki paraméter értéke.
			 */
			public static final String VALUE = InfosysOw1MESCHB.Row.META.ytq.getName();

			/**
			 * A műszaki paraméter mértékegysége.
			 */
			public static final String UNIT = InfosysOw1MESCHB.Row.META.yteinh.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private TableField() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final CharacteristicsBarQuery EXECUTOR = new CharacteristicsBarQuery();

		/**
		 * Konstruktor.
		 */
		private CharacteristicsBarQuery() {
			super("MESCHB", new String[] {InfosysOw1MESCHB.META.yas.getName(), InfosysOw1MESCHB.META.start.getName()}, TableField.class);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.TaskEdpImpl.InfoSystemQuery#getCriteriaFieldValues(java.lang.String)
		 */
		@Override
		protected String[] getCriteriaFieldValues(String workSlipId) {
			return new String[] {workSlipId, " "};
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected CharacteristicImpl createRowObject(FieldValues rowData) {
			return new CharacteristicImpl(rowData.getString(TableField.NAME),
					rowData.getString(TableField.VALUE),
					rowData.getString(TableField.UNIT));
		}

	}

	/**
	 * Segédosztály a gyártási feladathoz kapcsolódó dokumentumok lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class DocumentsQuery extends InfoSystemQuery<Document> {

		/**
		 * A lekérdezendő táblázati mezők nevei.
		 * @author szizo
		 */
		public static final class TableField {

			/**
			 * A dokumentum megnevezése.
			 */
			public static final String DESCRIPTION = InfosysOw1MESDOCLIST.Row.META.ytname.getName();

			/**
			 * A dokumentum URI-ja.
			 */
			public static final String URI = InfosysOw1MESDOCLIST.Row.META.ytdatei.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private TableField() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final DocumentsQuery EXECUTOR = new DocumentsQuery();

		/**
		 * Konstruktor.
		 */
		private DocumentsQuery() {
			super("MESDOCLIST", new String[] {InfosysOw1MESDOCLIST.META.yas.getName(), InfosysOw1MESDOCLIST.META.start.getName()}, TableField.class);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.TaskEdpImpl.InfoSystemQuery#getCriteriaFieldValues(java.lang.String)
		 */
		@Override
		protected String[] getCriteriaFieldValues(String workSlipId) {
			return new String[] {workSlipId, " "};
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected DocumentImpl createRowObject(FieldValues rowData) {
			URI uri;
			final String schemeSpecificPartOfUri = rowData.getString(TableField.URI);
			if (schemeSpecificPartOfUri.isEmpty()) {
				uri = null;
			} else {
				try {
					uri = new URI("file", schemeSpecificPartOfUri, null);
				} catch (URISyntaxException e) {
					uri = null;
				}
			}
			return new DocumentImpl(rowData.getString(TableField.DESCRIPTION), uri);
		}

	}

	/**
	 * Segédosztály a gyártási feladathoz kapcsolódó darabjegyzék adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class BomQuery extends TaskDataQuery<BomElement> {

		/**
		 * A lekérdezendő (fejrész)mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A megelőző munkalap lejelentett mennyisége.
			 */
			public static final String YIELD_OF_PRECEDING_WORK_SLIP = InfosysOw1MESTASKDATA.META.yelozolejelmenny.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * A lekérdezendő táblázati mezők nevei.
		 * @author szizo
		 */
		public static final class TableField {

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
			 * Az anyagkivét raktárhelye.
			 */
			public static final String WAREHOUSE_LOCATION = InfosysOw1MESTASKDATA.Row.META.ytplatzsuch.getName();

			/**
			 * A tételszöveg.
			 */
			public static final String ITEM_TEXT = InfosysOw1MESTASKDATA.Row.META.ytptext.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private TableField() {
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
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ydbj.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, Field.class, TableField.class);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected BomElementImpl createRowObject(FieldValues rowData) {
			return new BomElementImpl(rowData.getString(TableField.ID_NO),
					rowData.getString(TableField.SWD),
					rowData.getString(TableField.DESCRIPTION),
					rowData.getString(TableField.DESCRIPTION2),
					rowData.getBigDecimal(TableField.QUANTITY_PER_PRODUCT),
					rowData.getString(TableField.STOCK_UNIT_NAME),
					rowData.getString(TableField.WAREHOUSE_LOCATION),
					rowData.getString(TableField.ITEM_TEXT));
		}

	}

	/**
	 * Segédosztály a gyártási feladatot követő műveletek listájának lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class FollowingOperationsQuery extends TaskDataQuery<Operation> {

		/**
		 * A lekérdezendő táblázati mezők nevei.
		 * @author szizo
		 */
		public static final class TableField {

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
			private TableField() {
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
			super(new String[] {InfosysOw1MESTASKDATA.META.yas.getName(), InfosysOw1MESTASKDATA.META.ykovmuv.getName(), InfosysOw1MESTASKDATA.META.start.getName()}, null, TableField.class);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected OperationImpl createRowObject(FieldValues rowData) {
			return new OperationImpl(rowData.getString(TableField.ID_NO),
					rowData.getString(TableField.SWD),
					rowData.getString(TableField.DESCRIPTION),
					rowData.getString(TableField.WORK_CENTER_ID_NO),
					rowData.getString(TableField.WORK_CENTER_DESCRIPTION),
					rowData.getString(TableField.ITEM_TEXT));
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
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void scheduleTask(String workSlipId, GenericWorkStation<?> workStation, String precedingWorkSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			SCHEDULER.executeAbasFunction(scheduleInputFieldNames, new String[] {workSlipId, workStation.getWorkCenter().getIdNo(), Integer.toString(workStation.getNumber()), precedingWorkSlipId, " "}, edpConnection);
		}

		/**
		 * A megadott gyártási feladat beütemezésének visszavonása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void unscheduleTask(String workSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			unscheduleTask(workSlipId, false, edpConnection);
		}

		/**
		 * A megadott gyártási feladat beütemezésének visszavonása ill. felfüggesztése.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param suspend A gyártási feladat felfüggesztésre kerül?
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		protected static void unscheduleTask(String workSlipId, boolean suspend, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			SCHEDULER.executeAbasFunction(unScheduleInputFieldNames, new String[] {workSlipId, suspend ? "1" : "0", " "}, edpConnection);
		}

		/**
		 * A megadott gyártási feladat végrehajtásának félbeszakítása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void interruptTask(String workSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			interruptTask(workSlipId, true, edpConnection);
		}

		/**
		 * A megadott gyártási feladat végrehajtásának félbeszakítása ill. a félbeszakítás visszavonása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param interrupt A gyártás feladat végrehajtása félbeszakításra kerül?
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		protected static void interruptTask(String workSlipId, boolean interrupt, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			SCHEDULER.executeAbasFunction(interruptInputFieldNames, new String[] {workSlipId, interrupt ? "1" : "0", " "}, edpConnection);
		}

		/**
		 * A megadott gyártási feladat végrehajtásának folytatása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void resumeTask(String workSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			interruptTask(workSlipId, false, edpConnection);
		}

		/**
		 * A megadott gyártási feladat lejelentése.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param yield Az elkészült jó mennyiség.
		 * @param scrapQuantity A keletkezett selejt mennyisége.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void postCompletionConfirmation(String workSlipId, BigDecimal yield, BigDecimal scrapQuantity, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			CONFIRMER.executeAbasFunction(completionConfirmationFieldNames, new String[] {workSlipId, yield.toPlainString(), scrapQuantity.toPlainString(), " "}, edpConnection);
		}

		/**
		 * A megadott gyártási feladat felfüggesztése.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void suspendTask(String workSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			unscheduleTask(workSlipId, true, edpConnection);
		}

		/**
		 * A megadott gyártási feladat felfüggesztésének visszavonása.
		 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		public static void unsuspendTask(String workSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			unscheduleTask(workSlipId, false, edpConnection);
		}

		/**
		 * Konstruktor.
		 * @param swd Az infosystem keresőszava.
		 * @param headerFieldNames A lekérdezendő fejrészmezők nevei (null, ha nincs lekérdezendő fejrészmező).
		 */
		private TaskManager(String swd, String[] headerFieldNames) {
			super(swd, headerFieldNames, null);
		}

		/**
		 * Funkcióhívás (lekérdezés) végrehajtása.
		 * @param inputFieldNames Az eredmény lekérdezése előtt beállítandó mezők nevei.
		 * @param inputFieldValues Az eredmény lekérdezése előtt beállítandó mezők értékei.
		 * @param edpConnection Az EDP-kapcsolat.
		 * @throws InvalidAbasFunctionCallException Ha a funkcióhívás érvénytelen bemeneti adatok miatt meghiúsult.
		 * @throws AbasFunctionExecutionException Ha (futásidejű) hiba történt a funkcióhívás végrehajtása során.
		 */
		protected void executeAbasFunction(String[] inputFieldNames, String[] inputFieldValues, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
			try {
				final int errorCode = getResultHeaderFields(new EDPEditFieldList(inputFieldNames, inputFieldValues), edpConnection.getConnectionObject()).getInt(resultFieldName);
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
	protected class TaskDetailsEdpImpl extends TaskDetails<EDPSession> {

		/**
		 * Konstruktor.
		 * @param edpConnection Az EDP-kapcsolat.
		 */
		protected TaskDetailsEdpImpl(GenericAbasConnection<EDPSession> edpConnection) {
			super(edpConnection);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newBasicData()
		 */
		@Override
		protected BasicData newBasicData() {
			final InfoSystemExecutor.FieldValues result = BasicDataQuery.EXECUTOR.getResultHeaderFields(workSlipId, abasConnectionObject);
			final BasicData basicData = new BasicData();
			if (null == result) {
				basicData.status = Status.DELETED;
			} else {
				basicData.workSlipNo = result.getString(BasicDataQuery.Field.ID_NO);
				basicData.finishDate = result.getAbasDate(BasicDataQuery.Field.FINISH_DATE);
				basicData.status = getStatus(result);
				basicData.productIdNo = result.getString(BasicDataQuery.Field.PRODUCT_ID_NO);
				basicData.productSwd = result.getString(BasicDataQuery.Field.PRODUCT_SWD);
				basicData.productDescription = result.getString(BasicDataQuery.Field.PRODUCT_DESCRIPTION);
				basicData.productDescription2 = result.getString(BasicDataQuery.Field.PRODUCT_DESCRIPTION2);
				basicData.packingInstructionSwd = result.getString(BasicDataQuery.Field.PACKING_INSTRUCTION_SWD);
				basicData.fillingQuantity = result.getBigDecimal(BasicDataQuery.Field.FILLING_QUANTITY);
				basicData.packagingMaterialIdNo = result.getString(BasicDataQuery.Field.PACKAGING_MATERIAL_ID_NO);
				basicData.packagingMaterialDescription = result.getString(BasicDataQuery.Field.PACKAGING_MATERIAL_DESCRIPTION);
				basicData.usage = result.getString(BasicDataQuery.Field.USAGE);
				basicData.workInstruction = result.getString(BasicDataQuery.Field.WORK_INSTRUCTION);
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
			final InfoSystemExecutor.FieldValues result = OperationQuery.EXECUTOR.getResultHeaderFields(workSlipId, abasConnectionObject);
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
			final InfoSystemExecutor.FieldValues result = SalesOrderQuery.EXECUTOR.getResultHeaderFields(workSlipId, abasConnectionObject);
			final SalesOrderData salesOrderData = new SalesOrderData();
			if (null != result) {
				salesOrderData.salesOrderItemText = result.getString(SalesOrderQuery.Field.ITEM_TEXT);
				salesOrderData.salesOrderItemText2 = result.getString(SalesOrderQuery.Field.ITEM_TEXT2);
			}
			return salesOrderData;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newProductionListData()
		 */
		@Override
		protected ProductionListData newProductionListData() {
			final InfoSystemTableConverter<BomElement>.InfoSystemResult result = BomQuery.EXECUTOR.getResult(workSlipId, abasConnectionObject);
			final ProductionListData productionListData = new ProductionListData();
			if (null != result) {
				productionListData.yieldOfPrecedingWorkSlip = result.getHeaderFields().getBigDecimal(BomQuery.Field.YIELD_OF_PRECEDING_WORK_SLIP);
				productionListData.bom = result.getRows();
			}
			return productionListData;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newCharacteristicsBar()
		 */
		@Override
		protected List<Characteristic> newCharacteristicsBar() {
			return CharacteristicsBarQuery.EXECUTOR.getRows(workSlipId, abasConnectionObject);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.TaskDetails#newDocuments()
		 */
		@Override
		protected Collection<Document> newDocuments() {
			return DocumentsQuery.EXECUTOR.getRows(workSlipId, abasConnectionObject);
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
	 * @param edpConnection Az EDP-kapcsolat.
	 * @return Abas-munkalapazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező munkalap azonosítója.
	 */
	protected static Id workSlipIdFilter(Id abasObjectId, GenericAbasConnection<EDPSession> edpConnection) {
		if (9 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		final EDPQuery edpQuery = edpConnection.getConnectionObject().createQuery();
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
	 * @param edpConnection Az EDP-kapcsolat.
	 */
	public TaskEdpImpl(Id workSlipId, GenericAbasConnection<EDPSession> edpConnection) {
		this(workSlipIdFilter(workSlipId, edpConnection).toString());
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
	 */
	protected TaskEdpImpl(String workSlipId) {
		super(workSlipId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.impl.CachedDetailsContainer#newDetails(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	protected TaskDetailsEdpImpl newDetails(GenericAbasConnection<EDPSession> edpConnection) {
		return new TaskDetailsEdpImpl(edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask#schedule(phoenix.mes.abas.GenericWorkStation, de.abas.erp.common.type.Id, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void schedule(GenericWorkStation<?> workStation, Id precedingWorkSlipId, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.scheduleTask(workSlipId, workStation, precedingWorkSlipId.toString(), edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask#unschedule(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void unschedule(GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.unscheduleTask(workSlipId, edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask#interrupt(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void interrupt(GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.interruptTask(workSlipId, edpConnection);
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
	 * @see phoenix.mes.abas.GenericTask#resume(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void resume(GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.resumeTask(workSlipId, edpConnection);
		clearDetailsStatusCache();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask#postCompletionConfirmation(java.math.BigDecimal, java.math.BigDecimal, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void postCompletionConfirmation(BigDecimal yield, BigDecimal scrapQuantity, GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.postCompletionConfirmation(workSlipId, yield, scrapQuantity, edpConnection);
		if (null != details) {
			details.clearBasicDataCache();
			details.clearOperationDataCache();
		}
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask#suspend(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void suspend(GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.suspendTask(workSlipId, edpConnection);
		clearDetailsStatusCache();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask#unsuspend(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public void unsuspend(GenericAbasConnection<EDPSession> edpConnection) throws AbasFunctionException {
		TaskManager.unsuspendTask(workSlipId, edpConnection);
		clearDetailsStatusCache();
	}

}
