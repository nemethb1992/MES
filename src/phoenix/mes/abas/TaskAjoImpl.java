/*
 * Domain típusok könyvtára.
 *
 * Created on Oct 6, 2017
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.workorder.SelectableWorkorder;
import de.abas.erp.db.schema.workorder.WorkOrders;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;

/**
 * Gyártási feladat osztálya, AJO-ban implementálva.
 * @author szizo
 */
public class TaskAjoImpl implements Task {

	/**
	 * Gyártási feladat részleteit leíró osztály, AJO-ban implementálva.
	 * @author szizo
	 */
	protected class DetailsAjoImpl implements Details {

		/**
		 * Az Abas-munkamenet.
		 */
		protected final DbContext abasSession;

		/**
		 * Konstruktor.
		 * @param abasSession Az Abas-munkamenet.
		 */
		protected DetailsAjoImpl(DbContext abasSession) {
			this.abasSession = abasSession;
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 2168632003172665470L;

	/**
	 * A feladathoz tartozó munkalap azonosítója.
	 */
	protected final Id workSlipId;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy munkalapot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param abasSession Az Abas-munkamenet.
	 * @return Abas-munkalapazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező munkalap azonosítója.
	 */
	public static Id workSlipIdFilter(Id abasObjectId, DbContext abasSession) {
		if (9 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		final WorkOrders workSlip;
		try {
			workSlip = abasSession.load(WorkOrders.class, abasObjectId);
		} catch (Exception e) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId, e);
		}
		if (EnumWorkOrderType.WorkSlips != workSlip.getType()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		return abasObjectId;
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 * @param abasSession Az Abas-munkamenet.
	 */
	public TaskAjoImpl(Id workSlipId, DbContext abasSession) {
		this(workSlipIdFilter(workSlipId, abasSession));
	}

	/**
	 * Konstruktor.
	 * @param workSlip A feladathoz tartozó munkalap.
	 */
	TaskAjoImpl(SelectableWorkorder workSlip) {
		this(new IdImpl(workSlip.getRawString(WorkOrders.META.id.getName())));
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 */
	private TaskAjoImpl(Id workSlipId) {
		this.workSlipId = workSlipId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Task)) {
			return false;
		}
		return workSlipId.equals(((Task)object).getWorkSlipId());
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
		return "TaskAjoImpl [workSlipId=" + workSlipId + "]";
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
		return workSlipId;
	}

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A feladathoz tartozó munkalap.
	 */
	public WorkOrders getWorkSlip(DbContext abasSession) {
		return abasSession.load(WorkOrders.class, workSlipId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getDetails(de.abas.erp.db.DbContext)
	 */
	@Override
	public Details getDetails(DbContext abasSession) {
		return (new DetailsAjoImpl(abasSession));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getStartDate()
	 */
	@Override
	public AbasDate getStartDate(DbContext abasSession) {
		return getWorkSlip(abasSession).getStartDateDay();
	}

/*
Feladat indítása
 Bemenet: munkamenet
 Ha van ysts időbélyeg: már fut, hiba
 ysts = G|sekundenz, yszeich = G|bzeichen

Lejelentés
 Bemenet: mennyiség, dolgozó (null is lehet), munkamenet
 Ha nincs ysts időbélyeg: nincs elindítva, hiba
 Anyagkivét?
 ysts = ""

Feladat megszakítása
 Bemenet: munkamenet
 ysts = "", yszeich = G|bzeichen
*/
}
