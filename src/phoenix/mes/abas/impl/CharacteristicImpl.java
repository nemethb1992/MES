/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 15, 2019
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.GenericTask.Characteristic;

/**
 * Műszaki paramétert leíró osztály.
 * @author szizo
 */
public class CharacteristicImpl implements Characteristic {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -9014772449193827864L;

	/**
	 * A műszaki paraméter neve az aktuálisan beállított kezelőnyelven.
	 */
	protected final String name;

	/**
	 * A műszaki paraméter értéke.
	 */
	protected final String value;

	/**
	 * A műszaki paraméter mértékegysége.
	 */
	protected final String unit;

	/**
	 * Konstruktor.
	 * @param name A műszaki paraméter neve az aktuálisan beállított kezelőnyelven.
	 * @param value A műszaki paraméter értéke.
	 * @param unit A műszaki paraméter mértékegysége.
	 */
	public CharacteristicImpl(String name, String value, String unit) {
		this.name = name;
		this.value = value;
		this.unit = unit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Characteristic#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Characteristic#getValue()
	 */
	@Override
	public String getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Characteristic#getUnit()
	 */
	@Override
	public String getUnit() {
		return unit;
	}

}
