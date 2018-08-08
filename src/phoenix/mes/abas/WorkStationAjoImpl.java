/*
 * Domain típusok könyvtára.
 *
 * Created on Oct 9, 2017
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumFilingModeExtended;
import de.abas.erp.common.type.enums.EnumMaterialProvidedTransition;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.DatabaseIterator;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.FieldSet;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.capacity.WorkCenter;
import de.abas.erp.db.schema.materialsallocation.MaterialsAllocation;
import de.abas.erp.db.schema.purchasing.Reservations;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.Order;
import de.abas.erp.db.selection.SelectionBuilder;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gyártási munkaállomás osztálya, AJO-ban implementálva.
 * @author szizo
 */
public class WorkStationAjoImpl implements WorkStation {

	/**
	 * Segédosztály a munkaállomáshoz kapcsolódó feladatok lekérdezéséhez.
	 * @author szizo
	 */
	protected class TaskManager {

		/**
		 * Az Abas-munkamenet.
		 */
		protected final DbContext abasSession;

		/**
		 * Konstruktor.
		 * @param abasSession Az Abas-munkamenet.
		 */
		protected TaskManager(DbContext abasSession) {
			this.abasSession = abasSession;
		}

		/**
		 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa.
		 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt feladatok listája.
		 */
		public List<Task> getUnassignedTasks(AbasDate startDateUntil) {
			final SelectionBuilder<WorkOrders> selectionCriteria = createWorkSlipSelectionBuilder(0, false);
			selectionCriteria.add(Conditions.le(WorkOrders.META.startDateDay, startDateUntil));
			return createTaskList(selectionCriteria);
		}

		/**
		 * Közös szűrőfeltételek összeállítása munkalapok lekérdezéséhez.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param suspendedTasks A felfüggesztett munkalapokat kell kilistázni?
		 * @return A szűrőfeltételek.
		 */
		protected SelectionBuilder<WorkOrders> createWorkSlipSelectionBuilder(int workStationNumber, boolean suspendedTasks) {
			final SelectionBuilder<WorkOrders> selectionCriteria = SelectionBuilder.create(WorkOrders.class);
			selectionCriteria.setFilingMode(EnumFilingModeExtended.Active);
			selectionCriteria.addOrder(Order.asc(WorkOrders.META.startDateDay));
			selectionCriteria.add(Conditions.eq(WorkOrders.META.type, EnumWorkOrderType.WorkSlips));
			selectionCriteria.add(Conditions.eq(WorkOrders.META.workCenter.getName(), workCenterId));
			selectionCriteria.add(Conditions.eq(WorkOrders.META.ires1, workStationNumber));
			// VORORT
			selectionCriteria.add(Conditions.eq(WorkOrders.META.warehGrp.getName(), "9"));
			selectionCriteria.add(Conditions.eq(WorkOrders.META.bres1, suspendedTasks));
			selectionCriteria.add(Conditions.gt(WorkOrders.META.unitQty, BigDecimal.ZERO));
			return selectionCriteria;
		}

		/**
		 * A munkalap-szűrés végrehajtása.
		 * @param selectionCriteria Szűrőfeltételek a munkalapok lekérdezéséhez.
		 * @return A szűrésnek megfelelő feladatok listája.
		 */
		protected List<Task> createTaskList(SelectionBuilder<WorkOrders> selectionCriteria) {
			final List<Task> taskList = new ArrayList<>();
			final DatabaseIterator<WorkOrders> workSlipIterator = createWorkSlipIterator(selectionCriteria);
			try {
				while (workSlipIterator.hasNext()) {
					final WorkOrders workSlip = workSlipIterator.next();
					if (isExecutableWorkSlip(workSlip)) {
						taskList.add(new TaskAjoImpl(workSlip));
					}
				}
			} finally {
				workSlipIterator.close();
			}
			return taskList;
		}

		/**
		 * A munkalap-szűrés elindítása.
		 * @param selectionCriteria Szűrőfeltételek a munkalapok lekérdezéséhez.
		 * @return A munkalap-szűrés iterátor-objektuma.
		 */
		protected DatabaseIterator<WorkOrders> createWorkSlipIterator(SelectionBuilder<WorkOrders> selectionCriteria) {
			final Query<WorkOrders> query = abasSession.createQuery(selectionCriteria.build());
			query.setFields(FieldSet.of(Collections.singleton(WorkOrders.META.lastReservation)));
			query.setLazyLoad(false);
			return query.iterator();
		}

		/**
		 * @param workSlip A vizsgálandó munkalap.
		 * @return A munkalap végrehajtható, azaz nem anyaghiányos és az előző munkalap készre van jelentve?
		 */
		public boolean isExecutableWorkSlip(WorkOrders workSlip) {
			Reservations reservation = (Reservations)workSlip.getLastReservation();
			if (isCompletedReservation(reservation)) {
				return false;
			}
			while (null != (reservation = reservation.getPrevResSameLev())) {
				if (!reservation.getScheduled()) {
					continue;
				}
				switch (reservation.getElemType()) {
					case Product:
						if (isOpenMaterialReservation(reservation)) {
							return false;
						}
						break;
					case Operation:
						if (null != reservation.getWorkSlip()) {
							return (EnumMaterialProvidedTransition.ParallelBeginning == reservation.getProvMatTransition()
									? isPartiallyCompletedReservation(reservation)
									: isCompletedReservation(reservation));
						}
						break;
					default:
				}
			}
			return true;
		}

		/**
		 * @param reservation A vizsgálandó foglalás.
		 * @return A foglalás lezárt, azaz a nyitott mennyisége nulla vagy negatív?
		 */
		public boolean isCompletedReservation(Reservations reservation) {
			return (0 >= reservation.getOutstDelQty().signum());
		}

		/**
		 * @param materialReservation A vizsgálandó anyagfoglalás.
		 * @return Az anyagfoglalás nyitott, azaz (legalább részben) nem készletfedezetű?
		 */
		public boolean isOpenMaterialReservation(Reservations materialReservation) {
			if (isCompletedReservation(materialReservation)) {
				return false;
			}
			final SelectionBuilder<MaterialsAllocation> selectionCriteria = SelectionBuilder.create(MaterialsAllocation.class);
			selectionCriteria.setFilingMode(EnumFilingModeExtended.Both);
			selectionCriteria.add(Conditions.eq(MaterialsAllocation.META.res, materialReservation));
			selectionCriteria.add(Conditions.eq(MaterialsAllocation.META.scheduled, true));
			selectionCriteria.add(Conditions.eq(MaterialsAllocation.META.active, true));
			final Query<MaterialsAllocation> query = abasSession.createQuery(selectionCriteria.build());
			query.setFields(FieldSet.of(Collections.singleton(MaterialsAllocation.META.procure)));
			query.setLazyLoad(false);
			final DatabaseIterator<MaterialsAllocation> materialsAllocationIterator = query.iterator();
			try {
				while (materialsAllocationIterator.hasNext()) {
					if (null != materialsAllocationIterator.next().getProcure()) {
						return true;
					}
				}
			} finally {
				materialsAllocationIterator.close();
			}
			return false;
		}

		/**
		 * @param reservation A vizsgálandó foglalás.
		 * @return A foglalás legalább részben lezárt, azaz a nyitott mennyisége kevesebb, mint az eredeti mennyisége?
		 */
		public boolean isPartiallyCompletedReservation(Reservations reservation) {
			return (1 == reservation.getUnitQty().compareTo(reservation.getOutstDelQty()));
		}

		/**
		 * @return A munkaállomáshoz hozzárendelt, de felfüggesztett feladatok listája.
		 */
		public List<Task> getSuspendedTasks() {
			return createTaskList(createWorkSlipSelectionBuilder(number, true));
		}

		/**
		 * @return A munkaállomáshoz hozzárendelt és végrehajtható feladatok listája.
		 */
		public List<Task> getExecutableTasks() {
			return createTaskList(createExecutableWorkSlipSelectionBuilder());
		}

		/**
		 * Szűrőfeltételek összeállítása a munkaállomáshoz hozzárendelt és végrehajtható munkalapok lekérdezéséhez.
		 * @return A szűrőfeltételek.
		 */
		protected SelectionBuilder<WorkOrders> createExecutableWorkSlipSelectionBuilder() {
			final SelectionBuilder<WorkOrders> selectionCriteria = createWorkSlipSelectionBuilder(number, false);
			selectionCriteria.addOrder(Order.asc(WorkOrders.META.ires2));
			return selectionCriteria;
		}

		/**
		 * @return A munkaállomás következő végrehajtható feladata (null, ha a munkaállomás feladatlistája üres).
		 */
		public Task getNextExecutableTask() {
			final DatabaseIterator<WorkOrders> workSlipIterator = createWorkSlipIterator(createExecutableWorkSlipSelectionBuilder());
			try {
				while (workSlipIterator.hasNext()) {
					final WorkOrders workSlip = workSlipIterator.next();
					if (isExecutableWorkSlip(workSlip)) {
						return (new TaskAjoImpl(workSlip));
					}
				}
			} finally {
				workSlipIterator.close();
			}
			return null;
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -8124014658222366958L;

	/**
	 * Az Abas-beli gépcsoport azonosítója.
	 */
	protected final String workCenterId;

	/**
	 * A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	protected final int number;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy gépcsoportot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param abasSession Az Abas-munkamenet.
	 * @return Abas-gépcsoportazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező gépcsoport azonosítója.
	 */
	public static Id workCenterIdFilter(Id abasObjectId, DbContext abasSession) {
		if (8 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + abasObjectId);
		}
		try {
			abasSession.load(WorkCenter.class, abasObjectId);
		} catch (Exception e) {
			throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + abasObjectId, e);
		}
		return abasObjectId;
	}

	/**
	 * Konstruktor.
	 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param abasSession Az Abas-munkamenet.
	 */
	public WorkStationAjoImpl(Id workCenterId, int workStationNumber, DbContext abasSession) {
		this(workCenterIdFilter(workCenterId, abasSession).toString(), workStationNumber);
	}

	/**
	 * Konstruktor.
	 * @param workCenter Az Abas-beli gépcsoport.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	public WorkStationAjoImpl(WorkCenter workCenter, int workStationNumber) {
		this(workCenter.getRawString(WorkCenter.META.id.getName()), workStationNumber);
	}

	/**
	 * Konstruktor.
	 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	private WorkStationAjoImpl(String workCenterId, int workStationNumber) {
		this.workCenterId = workCenterId;
		this.number = workStationNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof WorkStation)) {
			return false;
		}
		final WorkStation other = (WorkStation)object;
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
		return "WorkStationAjoImpl [workCenterId=" + workCenterId + ", number=" + number + "]";
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
	 * @see phoenix.mes.abas.WorkStation#getWorkCenterId()
	 */
	@Override
	public Id getWorkCenterId() {
		return (new IdImpl(workCenterId));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getNumber()
	 */
	@Override
	public int getNumber() {
		return number;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getUnassignedTasks(de.abas.erp.common.type.AbasDate, de.abas.erp.db.DbContext)
	 */
	@Override
	public List<Task> getUnassignedTasks(AbasDate startDateUntil, DbContext abasSession) {
		return (new TaskManager(abasSession)).getUnassignedTasks(startDateUntil);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getSuspendedTasks(de.abas.erp.db.DbContext)
	 */
	@Override
	public List<Task> getSuspendedTasks(DbContext abasSession) {
		return (new TaskManager(abasSession)).getSuspendedTasks();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getExecutableTasks(de.abas.erp.db.DbContext)
	 */
	@Override
	public List<Task> getExecutableTasks(DbContext abasSession) {
		return (new TaskManager(abasSession)).getExecutableTasks();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getNextExecutableTask(de.abas.erp.db.DbContext)
	 */
	@Override
	public Task getNextExecutableTask(DbContext abasSession) {
		return (new TaskManager(abasSession)).getNextExecutableTask();
	}

}
