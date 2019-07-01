/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 27, 2018
 */

package phoenix.mes.abas.impl;

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
	 * A gépcsoport hivatkozási száma.
	 */
	protected final String workCenterIdNo;

	/**
	 * A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	protected final int number;

	/**
	 * Konstruktor.
	 * @param workCenterIdNo A gépcsoport hivatkozási száma.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	protected WorkStationImpl(String workCenterIdNo, int workStationNumber) {
		if (1 > workStationNumber) {
			throw new IllegalArgumentException("Érvénytelen gépszám: " + workStationNumber);
		}
		this.workCenterIdNo = workCenterIdNo;
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
		final GenericWorkStation<?> otherWorkStation = (GenericWorkStation<?>)object;
		return (number == otherWorkStation.getNumber() && workCenterIdNo.equals(otherWorkStation.getWorkCenter().getIdNo()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + number;
		result = 31 * result + workCenterIdNo.hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [workCenterIdNo=" + workCenterIdNo + ", number=" + number + "]";
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
	 * @see phoenix.mes.abas.GenericWorkStation#getNumber()
	 */
	@Override
	public int getNumber() {
		return number;
	}

}
