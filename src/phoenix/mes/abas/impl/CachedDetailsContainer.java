/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Jun 16, 2019
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.GenericAbasConnection;

/**
 * Alaposztály gyorsítótárazott adatokat tartalmazó osztályok implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @param <D> A nyelvfüggő gyorsítótár típusa.
 * @author szizo
 */
abstract class CachedDetailsContainer<C, D extends LanguageDependentCache<C>> {

	/**
	 * A nyelvfüggő gyorsítótár.
	 */
	protected transient D details = null;

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A nyelvfüggő gyorsítótár.
	 */
	public D getDetails(GenericAbasConnection<C> abasConnection) {
		if (null == details) {
			details = newDetails(abasConnection);
		} else {
			details.resetCacheIfLanguageDoesNotMatch(abasConnection);
		}
		return details;
	}

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gyorsítótárazott adatokat tartalmazó, újonnan létrehozott objektum.
	 */
	protected abstract D newDetails(GenericAbasConnection<C> abasConnection);

}
