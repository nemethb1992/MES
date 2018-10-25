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

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.Task;

/**
 * Alaposztály a gyártási feladat típus implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class TaskImpl<C> implements Task {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -2756046396258670508L;

	/**
	 * A gyártási feladathoz tartozó munkalap azonosítója.
	 */
	protected final String workSlipId;

	/**
	 * Az Abas-kapcsolat osztálya.
	 */
	protected final Class<C> abasConnectionType;

	/**
	 * A gyártási feladat részleteit leíró objektum.
	 */
	protected transient TaskDetails<C> details = null;

	/**
	 * Konstruktor.
	 * @param workSlipId A gyártási feladathoz tartozó munkalap azonosítója.
	 * @param abasConnectionType Az Abas-kapcsolat osztálya.
	 */
	protected TaskImpl(String workSlipId, Class<C> abasConnectionType) {
		this.workSlipId = workSlipId;
		this.abasConnectionType = abasConnectionType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Task)) {
			return false;
		}
		final Id otherWorkSlipId = ((Task)object).getWorkSlipId();
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
	 * @see phoenix.mes.abas.Task#getWorkSlipId()
	 */
	@Override
	public Id getWorkSlipId() {
		return (new IdImpl(workSlipId));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getDetails(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public TaskDetails<C> getDetails(AbasConnection<?> abasConnection) {
		@SuppressWarnings("unchecked")
		final AbasConnection<C> abasConnectionC = (AbasConnection<C>)abasConnection;
		if (null == details) {
			details = newDetails(abasConnectionC);
		} else {
			details.setAbasConnectionObject(abasConnectionC, abasConnectionType);
		}
		return details;
	}

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gyártási feladat részleteit leíró, újonnan létrehozott objektum.
	 */
	protected abstract TaskDetails<C> newDetails(AbasConnection<C> abasConnection);

}
