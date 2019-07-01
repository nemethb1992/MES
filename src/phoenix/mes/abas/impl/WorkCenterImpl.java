/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas.impl;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;

import phoenix.mes.abas.GenericWorkCenter;

/**
 * Alaposztály a gépcsoport típus implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class WorkCenterImpl<C> extends CachedDetailsContainer<C, WorkCenterDetails<C>> implements GenericWorkCenter<C> {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -5166818850011328339L;

	/**
	 * A gépcsoport hivatkozási száma.
	 */
	protected final String idNo;

	/**
	 * Konstruktor.
	 * @param idNo A gépcsoport hivatkozási száma.
	 */
	protected WorkCenterImpl(String idNo) {
		this.idNo = idNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GenericWorkCenter)) {
			return false;
		}
		return idNo.equals(((GenericWorkCenter<?>)object).getIdNo());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return idNo.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [idNo=" + idNo + "]";
	}

	/**
	 * Az objektum üres tagváltozókkal nem olvasható vissza.
	 * @throws ObjectStreamException Mindig.
	 * @see java.io.Serializable
	 */
	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("");
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkCenter#getIdNo()
	 */
	@Override
	public String getIdNo() {
		return idNo;
	}

}
