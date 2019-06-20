/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas.impl;

import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;

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
	 * A gépcsoport Abas-beli azonosítója.
	 */
	protected final String id;

	/**
	 * Konstruktor.
	 * @param id A gépcsoport Abas-beli azonosítója.
	 */
	protected WorkCenterImpl(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GenericWorkCenter)) {
			return false;
		}
		final Id otherWorkCenterId = ((GenericWorkCenter<?>)object).getId();
		return null != otherWorkCenterId && id.equals(otherWorkCenterId.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + "]";
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
	 * @see phoenix.mes.abas.GenericWorkCenter#getId()
	 */
	@Override
	public Id getId() {
		return new IdImpl(id);
	}

}
