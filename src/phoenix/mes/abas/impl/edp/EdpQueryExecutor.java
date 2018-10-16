/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Aug 24, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.InvalidQueryException;
import de.abas.erp.common.type.Id;

import java.lang.reflect.Field;

/**
 * Osztály EDP-n keresztüli lekérdezésekhez.
 * @author szizo
 */
public class EdpQueryExecutor {

	/**
	 * A lekérdezendő mezők nevei.
	 */
	protected final String[] fieldNames;

	/**
	 * @param fieldNamesClass A lekérdezendő mezők neveit konstansokként tartalmazó osztály.
	 * @return A lekérdezendő mezők nevei, tömb formájában.
	 */
	public static String[] getFieldNames(Class<?> fieldNamesClass) {
		if (null == fieldNamesClass) {
			return null;
		}
		final Field[] declaredFields = fieldNamesClass.getDeclaredFields();
		final String[] fieldNames = new String[declaredFields.length];
		for (int i = 0; i < declaredFields.length; i++) {
			try {
				fieldNames[i] = (String)(declaredFields[i].get(null));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return fieldNames;
	}

	/**
	 * Konstruktor.
	 * @param fieldNamesClass A lekérdezendő mezők neveit konstansokként tartalmazó osztály.
	 */
	public EdpQueryExecutor(Class<?> fieldNamesClass) {
		this(getFieldNames(fieldNamesClass));
	}

	/**
	 * Kosntruktor.
	 * @param fieldNames A lekérdezendő mezők nevei.
	 */
	public EdpQueryExecutor(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	/**
	 * @return A lekérdezendő mezők nevei.
	 */
	public String[] getFieldNames() {
		return fieldNames;
	}

	/**
	 * Adatrekord betöltése.
	 * @param recordId A rekord azonosítója.
	 * @param edpSession Az EDP-munkamenet.
	 * @return Az EDP-lekérdezés objektuma (null, ha a megadott azonosítóval nem található adatrekord).
	 */
	public EDPQuery readRecord(Id recordId, EDPSession edpSession) {
		return readRecord(recordId.toString(), edpSession);
	}

	/**
	 * Adatrekord betöltése.
	 * @param recordId A rekord azonosítója.
	 * @param edpSession Az EDP-munkamenet.
	 * @return Az EDP-lekérdezés objektuma (null, ha a megadott azonosítóval nem található adatrekord).
	 */
	public EDPQuery readRecord(String recordId, EDPSession edpSession) {
		final EDPQuery edpQuery = createQuery(edpSession);
		try {
			return (edpQuery.readRecord(recordId, fieldNames) ? edpQuery : null);
		} catch (InvalidQueryException e) {
			throw new EDPRuntimeException(e);
		}
	}

	/**
	 * EDP-lekérdezés megnyitása.
	 * @param edpSession Az EDP-lekérdezés.
	 * @return A megnyitott EDP-lekérdezés.
	 */
	protected EDPQuery createQuery(EDPSession edpSession) {
		final EDPQuery edpQuery = edpSession.createQuery();
		edpQuery.enableQueryMetaData(false);
		return edpQuery;
	}

}
