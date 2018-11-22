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
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 1470242670840659210L;

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
	 * A beépülési mennyiség egységének (raktáregység) neve.
	 */
	protected final String stockUnit;

	/**
	 * A tételszöveg.
	 */
	protected final String itemText;

	/**
	 * Konstruktor.
	 * @param idNo A beépülő cikk hivatkozási száma.
	 * @param swd A beépülő cikk keresőszava.
	 * @param description A beépülő cikk megnevezése az aktuálisan beállított kezelőnyelven.
	 * @param description2 A beépülő cikk második megnevezése.
	 * @param quantityPerProduct A beépülési mennyiség (egy késztermékre vonatkozóan).
	 * @param stockUnit A mennyiségi egység (raktáregység).
	 * @param itemText A tételszöveg.
	 */
	public BomElementImpl(String idNo, String swd, String description, String description2, BigDecimal quantityPerProduct, String stockUnit, String itemText) {
		this.idNo = idNo;
		this.swd = swd;
		this.description = description;
		this.description2 = description2;
		this.quantityPerProduct = quantityPerProduct;
		this.stockUnit = stockUnit;
		this.itemText = itemText;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getIdNo()
	 */
	public String getIdNo() {
		return idNo;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getSwd()
	 */
	public String getSwd() {
		return swd;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getDescription2()
	 */
	public String getDescription2() {
		return description2;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getQuantityPerProduct()
	 */
	public BigDecimal getQuantityPerProduct() {
		return quantityPerProduct;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getStockUnit()
	 */
	public String getStockUnit() {
		return stockUnit;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task.BomElement#getItemText()
	 */
	public String getItemText() {
		return itemText;
	}

}
