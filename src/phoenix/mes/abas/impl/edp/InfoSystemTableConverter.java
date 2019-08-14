/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Oct 4, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPEditObject;
import de.abas.ceks.jedp.EDPSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Osztály infosystemeken keresztüli lekérdezésekhez.
 * @param <R> A táblázatsorokból készített objektumok típusa.
 * @author szizo
 */
public abstract class InfoSystemTableConverter<R> extends InfoSystemExecutor {

	/**
	 * Segédosztály az eredmények együttes átadásához.
	 * @author szizo
	 */
	public class InfoSystemResult {

		/**
		 * A lekérdezendő fejrészmezők értékei az infosystem lefuttatása után.
		 */
		protected final FieldValues headerFields;

		/**
		 * Az infosystem táblázatsorai objektumok listájaként.
		 */
		protected final List<R> rows;

		/**
		 * Konstruktor.
		 * @param headerFields A lekérdezendő fejrészmezők értékei az infosystem lefuttatása után.
		 * @param rows Az infosystem táblázatsorai objektumok listájaként.
		 */
		protected InfoSystemResult(FieldValues headerFields, List<R> rows) {
			this.headerFields = headerFields;
			this.rows = rows;
		}

		/**
		 * @return A lekérdezendő fejrészmezők értékei az infosystem lefuttatása után.
		 */
		public FieldValues getHeaderFields() {
			return headerFields;
		}

		/**
		 * @return Az infosystem táblázatsorai objektumok listájaként.
		 */
		public List<R> getRows() {
			return rows;
		}

	}

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály.
	 */
	public InfoSystemTableConverter(String swd, Class<?> tableFieldNamesClass) {
		this(swd, null, tableFieldNamesClass);
	}

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param headerFieldNamesClass A lekérdezendő fejrészmezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő fejrészmező).
	 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály.
	 */
	public InfoSystemTableConverter(String swd, Class<?> headerFieldNamesClass, Class<?> tableFieldNamesClass) {
		super(swd, headerFieldNamesClass, tableFieldNamesClass);
	}

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param tableFieldNames A lekérdezendő táblázati mezők nevei.
	 */
	public InfoSystemTableConverter(String swd, String[] tableFieldNames) {
		this(swd, null, tableFieldNames);
	}

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param headerFieldNames A lekérdezendő fejrészmezők nevei (null, ha nincs lekérdezendő fejrészmező).
	 * @param tableFieldNames A lekérdezendő táblázati mezők nevei.
	 */
	public InfoSystemTableConverter(String swd, String[] headerFieldNames, String[] tableFieldNames) {
		super(swd, headerFieldNames, tableFieldNames);
	}

	/**
	 * @param inputFieldValues Az eredmény lekérdezése előtti mezőbeállítások (null, ha nincs szükség bemenetekre).
	 * @param edpSession Az EDP-munkamenet.
	 * @return Az infosystem lefuttatásának eredménye.
	 */
	public InfoSystemResult getResult(EDPEditFieldList inputFieldValues, EDPSession edpSession) {
		return getResult(executeQuery(inputFieldValues, edpSession));
	}

	/**
	 * @param result A lekérdezendő mezők értékei az infosystem lefuttatása után.
	 * @return Az infosystem lefuttatásának eredménye.
	 */
	protected InfoSystemResult getResult(EDPEditObject result) {
		return new InfoSystemResult(getResultHeaderFields(result), getRows(result));
	}

	/**
	 * @param inputFieldValues Az eredmény lekérdezése előtti mezőbeállítások (null, ha nincs szükség bemenetekre).
	 * @param edpSession Az EDP-munkamenet.
	 * @return Az infosystem táblázatsorai objektumok listájaként.
	 */
	public List<R> getRows(EDPEditFieldList inputFieldValues, EDPSession edpSession) {
		return getRows(executeQuery(inputFieldValues, edpSession));
	}

	/**
	 * @param result A lekérdezendő mezők értékei az infosystem lefuttatása után.
	 * @return Az infosystem táblázatsorai objektumok listájaként.
	 */
	protected List<R> getRows(EDPEditObject result) {
		final int rowCount = result.getRowCount();
		switch (rowCount) {
			case 0:
				return Collections.emptyList();
			case 1:
				return Collections.singletonList(createRowObject(new FieldValues(result.getFields(1))));
			default:
				final List<R> rows = new ArrayList<>(rowCount);
				for (int i = 1; i <= rowCount; i++) {
					rows.add(createRowObject(new FieldValues(result.getFields(i))));
				}
				return rows;
		}
	}

	/**
	 * Objektum készítése az infosystem táblázatsorából.
	 * @param rowData Az infosystem táblázatsora.
	 * @return A táblázatsor objektummá alakítva.
	 */
	protected abstract R createRowObject(FieldValues rowData);

}
