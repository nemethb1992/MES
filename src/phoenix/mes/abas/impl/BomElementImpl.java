/*
 * Domain típusok könyvtára.
 *
 * Created on Sep 28, 2018
 */

package phoenix.mes.abas.impl;

import java.math.BigDecimal;

import phoenix.mes.abas.Task.BomElement;

/**
 * Gyártási feladathoz kapcsolódó darabjegyzék elemeit leíró osztály.
 * @author szizo
 */
public class BomElementImpl implements BomElement {

	/**
	 * A beépülő cikk hivatkozási száma.
	 */
	protected final String idNo;

	/**
	 * A beépülő cikk keresőszava.
	 */
	protected final String swd;

	/**
	 * A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	protected final String description;

	/**
	 * A beépülő cikk második megnevezése.
	 */
	protected final String description2;

	/**
	 * A beépülési mennyiség (egy késztermékre vonatkozóan).
	 */
	protected final BigDecimal quantityPerProduct;

	/**
	 * A mennyiségi egység (raktáregység).
	 */
	protected final String stockUnit;

	/**
	 * Konstruktor.
	 * @param idNo A beépülő cikk hivatkozási száma.
	 * @param swd A beépülő cikk keresőszava.
	 * @param description A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
	 * @param description2 A beépülő cikk második megnevezése.
	 * @param quantityPerProduct A beépülési mennyiség (egy késztermékre vonatkozóan).
	 * @param stockUnit A mennyiségi egység (raktáregység).
	 */
	public BomElementImpl(String idNo, String swd, String description, String description2, BigDecimal quantityPerProduct, String stockUnit) {
		this.idNo = idNo;
		this.swd = swd;
		this.description = description;
		this.description2 = description2;
		this.quantityPerProduct = quantityPerProduct;
		this.stockUnit = stockUnit;
	}

	/**
	 * @return A beépülő cikk hivatkozási száma.
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @return A beépülő cikk keresőszava.
	 */
	public String getSwd() {
		return swd;
	}

	/**
	 * @return A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return A beépülő cikk második megnevezése.
	 */
	public String getDescription2() {
		return description2;
	}

	/**
	 * @return A beépülési mennyiség (egy késztermékre vonatkozóan).
	 */
	public BigDecimal getQuantityPerProduct() {
		return quantityPerProduct;
	}

	/**
	 * @return A mennyiségi egység (raktáregység).
	 */
	public String getStockUnit() {
		return stockUnit;
	}

}
