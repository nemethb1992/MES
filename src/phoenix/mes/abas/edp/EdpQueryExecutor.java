/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Aug 24, 2018
 */

package phoenix.mes.abas.edp;

import java.lang.reflect.Field;

/**
 *
 * @author szizo
 */
public class EdpQueryExecutor {

	/**
	 * A lekérdezendő mezők nevei.
	 */
	protected final String[] fieldNames;

	/**
	 * Konstruktor.
	 * @param fieldNamesClass A lekérdezendő mezők neveit tartalmazó osztály.
	 */
	public EdpQueryExecutor(Class<?> fieldNamesClass) {
		final Field[] declaredFields = fieldNamesClass.getDeclaredFields();
		fieldNames = new String[declaredFields.length];
		for (int i = 0; i < declaredFields.length; i++) {
			try {
				fieldNames[i] = (String)declaredFields[i].get(null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @return A lekérdezendő mezők nevei.
	 */
	public String[] getFieldNames() {
		return fieldNames;
	}

}
