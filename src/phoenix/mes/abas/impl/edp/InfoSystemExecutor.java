/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Oct 3, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPEditObject;
import de.abas.ceks.jedp.EDPEditor;
import de.abas.ceks.jedp.EDPException;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.type.AbasUnit;

import java.math.BigDecimal;

/**
 * Osztály infosystemeken keresztüli lekérdezésekhez.
 * @author szizo
 */
public class InfoSystemExecutor {

	/**
	 * Segédosztály több mezőérték együttes átadásához.
	 * @author szizo
	 */
	public static class FieldValues {

		/**
		 * A mezőértékek.
		 */
		protected final EDPEditFieldList fieldValues;

		/**
		 * Konstruktor.
		 * @param fieldValues A mezőértékek.
		 */
		protected FieldValues(EDPEditFieldList fieldValues) {
			this.fieldValues = fieldValues;
		}

		/**
		 * @param fieldName A mező neve.
		 * @return A megadott mező tartalma szöveges formában.
		 */
		public String getString(String fieldName) {
			return fieldValues.getField(fieldName).getValue();
		}

		/**
		 * @param fieldName A mező neve.
		 * @return A megadott mező tartalma AbasDate típusú dátumként.
		 */
		public AbasDate getAbasDate(String fieldName) {
			return AbasDate.valueOf(getString(fieldName));
		}

		/**
		 * @param fieldName A mező neve.
		 * @return A megadott mező tartalma AbasUnit típusú mértékegységként.
		 */
		public AbasUnit getAbasUnit(String fieldName) {
			return AbasUnit.UNITS.valueOf(getString(fieldName));
		}

		/**
		 * @param fieldName A mező neve.
		 * @return A megadott mező tartalma BigDecimal típusú tizedestörtként.
		 */
		public BigDecimal getBigDecimal(String fieldName) {
			return (new BigDecimal(getString(fieldName)));
		}

		/**
		 * @param fieldName A mező neve.
		 * @return A megadott mező tartalma logikai értékként.
		 */
		public boolean getBoolean(String fieldName) {
			return "1".equals(getString(fieldName));
		}

		/**
		 * @param fieldName A mező neve.
		 * @return A megadott mező tartalma egész számként.
		 */
		public int getInt(String fieldName) {
			return Integer.parseInt(getString(fieldName));
		}

	}

	/**
	 * Az infosystem keresőszava.
	 */
	protected final String swd;

	/**
	 * A lekérdezendő fejrészmezők nevei.
	 */
	protected final String[] headerFieldNames;

	/**
	 * A lekérdezendő táblázati mezők nevei.
	 */
	protected final String[] tableFieldNames;

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param headerFieldNamesClass A lekérdezendő fejrészmezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő fejrészmező).
	 * @param tableFieldNamesClass A lekérdezendő táblázati mezők neveit konstansokként tartalmazó osztály (null, ha nincs lekérdezendő táblázati mező).
	 */
	public InfoSystemExecutor(String swd, Class<?> headerFieldNamesClass, Class<?> tableFieldNamesClass) {
		this(swd, EdpQueryExecutor.getFieldNames(headerFieldNamesClass), EdpQueryExecutor.getFieldNames(tableFieldNamesClass));
	}

	/**
	 * Konstruktor.
	 * @param swd Az infosystem keresőszava.
	 * @param headerFieldNames A lekérdezendő fejrészmezők nevei (null, ha nincs lekérdezendő fejrészmező).
	 * @param tableFieldNames A lekérdezendő táblázati mezők nevei (null, ha nincs lekérdezendő táblázati mező).
	 */
	public InfoSystemExecutor(String swd, String[] headerFieldNames, String[] tableFieldNames) {
		this.swd = swd;
		this.headerFieldNames = headerFieldNames;
		this.tableFieldNames = tableFieldNames;
	}

	/**
	 * Infosystemen keresztüli lekérdezés végrehajtása.
	 * @param inputFieldValues Az eredmény lekérdezése előtti mezőbeállítások (null, ha nincs szükség bemenetekre).
	 * @param edpSession Az EDP-munkamenet.
	 * @return A lekérdezendő mezők értékei az infosystem lefuttatása után.
	 */
	public EDPEditObject executeQuery(EDPEditFieldList inputFieldValues, EDPSession edpSession) {
		final EDPEditor infoSystemQuery = edpSession.createEditor();
		try {
			infoSystemQuery.beginEditCmd("(Infosystem)", swd);
			if (null != inputFieldValues) {
				infoSystemQuery.updateEditFields(inputFieldValues);
			}
			return infoSystemQuery.getEditObject(headerFieldNames, tableFieldNames);
		} catch (EDPException e) {
			throw new EDPRuntimeException(e);
		} finally {
			if (infoSystemQuery.isActive()) {
				infoSystemQuery.endEditCancel();
			}
		}
	}

	/**
	 * Infosystemen keresztüli lekérdezés végrehajtása.
	 * @param inputFieldValues Az eredmény lekérdezése előtti mezőbeállítások (null, ha nincs szükség bemenetekre).
	 * @param edpSession Az EDP-munkamenet.
	 * @return A lekérdezendő fejrészmezők értékei az infosystem lefuttatása után.
	 */
	public FieldValues getResultHeaderFields(EDPEditFieldList inputFieldValues, EDPSession edpSession) {
		return (new FieldValues(executeQuery(inputFieldValues, edpSession).getHeaderFields()));
	}

}
