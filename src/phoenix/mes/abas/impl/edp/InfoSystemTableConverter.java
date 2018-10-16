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
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály.
	 */
	public InfoSystemTableConverter(String swd, Class<?> tableFieldNamesClass) {
		super(swd, null, tableFieldNamesClass);
	}

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param tableFieldNames A lekérdezendő táblázati mezők nevei.
	 */
	public InfoSystemTableConverter(String swd, String[] tableFieldNames) {
		super(swd, null, tableFieldNames);
	}

	/**
	 * @param filterCriteria Az infosystem indításához szükséges mezőbeállítások.
	 * @param edpSession Az EDP-munkamenet.
	 * @return Az infosystem táblázatsorai objektumok listájaként.
	 */
	public List<R> getRows(EDPEditFieldList filterCriteria, EDPSession edpSession) {
		final EDPEditObject result = executeQuery(filterCriteria, edpSession);
		final int rowCount = result.getRowCount();
		switch (rowCount) {
			case 0:
				return Collections.emptyList();
			case 1:
				return Collections.singletonList(createRowObject(result.getFields(1)));
			default:
				final List<R> rows = new ArrayList<>(rowCount);
				for (int i = 1; i <= rowCount; i++) {
					rows.add(createRowObject(result.getFields(i)));
				}
				return rows;
		}
	}

	/**
	 * Objektum készítése az infosystem táblázatsorából.
	 * @param rowData Az infosystem táblázatsora.
	 * @return A táblázatsor objektummá alakítva.
	 */
	protected abstract R createRowObject(EDPEditFieldList rowData);

}
