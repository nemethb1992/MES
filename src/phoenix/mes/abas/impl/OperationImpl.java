/*
 * Domain típusok könyvtára.
 *
 * Created on Nov 23, 2018
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.Task.Operation;

/**
 * Gyártási tevékenységet (műveletet) leíró osztály.
 * @author szizo
 */
public class OperationImpl implements Operation {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -7278900219601840732L;

	/**
	 * @return A művelet hivatkozási száma.
	 */
	protected final String idNo;

	/**
	 * @return A művelet keresőszava.
	 */
	protected final String swd;

	/**
	 * @return A művelet megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	protected final String description;

	/**
	 * @return A művelethez rendelt gépcsoport hivatkozási száma.
	 */
	protected final String workCenterIdNo;

	/**
	 * @return A művelethez rendelt gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	protected final String workCenterDescription;

	/**
	 * @return A műveletsor tételszövege a gyártási listában.
	 */
	protected final String itemText;

	/**
	 * Konstruktor.
	 * @param idNo A művelet hivatkozási száma.
	 * @param swd A művelet keresőszava.
	 * @param description A művelet megnevezése az aktuálisan beállított kezelőnyelven.
	 * @param workCenterIdNo A művelethez rendelt gépcsoport hivatkozási száma.
	 * @param workCenterDescription A művelethez rendelt gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
	 * @param itemText A műveletsor tételszövege a gyártási listában.
	 */
	public OperationImpl(String idNo, String swd, String description, String workCenterIdNo, String workCenterDescription, String itemText) {
		this.idNo = idNo;
		this.swd = swd;
		this.description = description;
		this.workCenterIdNo = workCenterIdNo;
		this.workCenterDescription = workCenterDescription;
		this.itemText = itemText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Operation#getIdNo()
	 */
	@Override
	public String getIdNo() {
		return idNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Operation#getSwd()
	 */
	@Override
	public String getSwd() {
		return swd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Operation#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Operation#getWorkCenterIdNo()
	 */
	@Override
	public String getWorkCenterIdNo() {
		return workCenterIdNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Operation#getWorkCenterDescription()
	 */
	@Override
	public String getWorkCenterDescription() {
		return workCenterDescription;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.Operation#getItemText()
	 */
	@Override
	public String getItemText() {
		return itemText;
	}

}
