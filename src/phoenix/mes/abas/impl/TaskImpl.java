/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl;

import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;

import phoenix.mes.abas.GenericTask;

/**
 * Alaposztály a gyártási feladat típus implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class TaskImpl<C> extends CachedDetailsContainer<C, TaskDetails<C>> implements GenericTask<C> {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -2756046396258670508L;

	/**
	 * A gyártási feladathoz tartozó munkalap azonosítója.
	 */
	protected final String workSlipId;

	/**
	 * Konstruktor.
	 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
	 */
	protected TaskImpl(String workSlipId) {
		this.workSlipId = workSlipId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GenericTask)) {
			return false;
		}
		final Id otherWorkSlipId = ((GenericTask<?>)object).getWorkSlipId();
		return null != otherWorkSlipId && workSlipId.equals(otherWorkSlipId.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return workSlipId.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [workSlipId=" + workSlipId + "]";
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
	 * @see phoenix.mes.abas.GenericTask#getWorkSlipId()
	 */
	@Override
	public Id getWorkSlipId() {
		return new IdImpl(workSlipId);
	}

}
