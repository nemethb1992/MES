/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Jun 16, 2019
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.GenericAbasConnection;

/**
 * Alaposztály nyelvfüggő gyorsítótárak implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
abstract class LanguageDependentCache<C> {

	/**
	 * Az aktuális kezelőnyelv kódja.
	 */
	protected String operatingLanguageCode;

	/**
	 * Konstruktor.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	protected LanguageDependentCache(GenericAbasConnection<C> abasConnection) {
		resetCacheIfLanguageDoesNotMatch(abasConnection);
	}

	/**
	 * A gyorsítótár alaphelyzetbe hozása, ha az aktuális Abas-kapcsolat nyelve nem egyezik meg a gyorsítótár nyelvével.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	protected void resetCacheIfLanguageDoesNotMatch(GenericAbasConnection<C> abasConnection) {
		final String newOperatingLanguageCode = abasConnection.getOperatingLanguageCode();
		if (!newOperatingLanguageCode.equals(operatingLanguageCode)) {
			if (null != operatingLanguageCode) {
				resetCache(abasConnection);
			}
			operatingLanguageCode = newOperatingLanguageCode;
		}
	}

	/**
	 * A gyorsítótár alaphelyzetbe hozása.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	protected abstract void resetCache(GenericAbasConnection<C> abasConnection);

}
