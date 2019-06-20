/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.GenericAbasConnection;
import phoenix.mes.abas.GenericWorkCenter;

/**
 * Alaposztály gépcsoportok adatait leíró osztályok implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class WorkCenterDetails<C> extends LanguageDependentCache<C> implements GenericWorkCenter.Details {

	/**
	 * A gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	protected String description;

	/**
	 * Konstruktor.
	 * @param abasConnection Az Abas-kapcsolat.
	 */
	protected WorkCenterDetails(GenericAbasConnection<C> abasConnection) {
		super(abasConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkCenter.Details#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

}
