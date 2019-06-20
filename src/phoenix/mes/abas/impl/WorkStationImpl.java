/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 27, 2018
 */

package phoenix.mes.abas.impl;

import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;

import phoenix.mes.abas.GenericWorkStation;

/**
 * Alaposztály a gyártási munkaállomás típus implementálásához.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public abstract class WorkStationImpl<C> implements GenericWorkStation<C> {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 6873301319949108993L;

	/**
	 * Az Abas-beli gépcsoport azonosítója.
	 */
	protected final String workCenterId;

	/**
	 * A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	protected final int number;

	/**
	 * Konstruktor.
	 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	protected WorkStationImpl(String workCenterId, int workStationNumber) {
		if (1 > workStationNumber) {
			throw new IllegalArgumentException("Érvénytelen gépszám: " + workStationNumber);
		}
		this.workCenterId = workCenterId;
		this.number = workStationNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GenericWorkStation)) {
			return false;
		}
		final GenericWorkStation<?> other = (GenericWorkStation<?>)object;
		final Id otherWorkCenterId = other.getWorkCenterId();
		return (number == other.getNumber() && null != otherWorkCenterId && workCenterId.equals(otherWorkCenterId.toString()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + number;
		result = 31 * result + workCenterId.hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [workCenterId=" + workCenterId + ", number=" + number + "]";
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
	 * @see phoenix.mes.abas.GenericWorkStation#getWorkCenterId()
	 */
	@Override
	public Id getWorkCenterId() {
		return new IdImpl(workCenterId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#getNumber()
	 */
	@Override
	public int getNumber() {
		return number;
	}

}
