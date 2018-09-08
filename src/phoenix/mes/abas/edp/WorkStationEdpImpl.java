/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 27, 2018
 */

package phoenix.mes.abas.edp;

import de.abas.ceks.jedp.EDPConstants;
import de.abas.ceks.jedp.EDPEditor;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.StandardEDPSelection;
import de.abas.ceks.jedp.StandardEDPSelectionCriteria;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumFilingModeExtended;
import de.abas.erp.common.type.enums.EnumMaterialProvidedTransition;
import de.abas.erp.common.type.enums.EnumProductionListElementType;
import de.abas.erp.common.type.enums.EnumTypeCommands;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MES;
import de.abas.erp.db.schema.capacity.WorkCenter;
import de.abas.erp.db.schema.materialsallocation.MaterialsAllocation;
import de.abas.erp.db.schema.purchasing.Reservations;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.Order;
import de.abas.erp.db.selection.SelectionBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbstractWorkStation;
import phoenix.mes.abas.Task;

/**
 * Gyártási munkaállomás osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class WorkStationEdpImpl extends AbstractWorkStation {

	/**
	 * Segédosztály a gyártási feladat részleteinek lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class WorkSlipQuery extends EdpQueryExecutor {

		/**
		 * A gyártási feladat részleteinek lekérdezéséhez szükséges mezőnevek.
		 * @author szizo
		 */
		public static final class Field {

			public static final String ID = WorkOrders.META.id.getName();

			public static final String WORK_ORDER_SUGGESTION = WorkOrders.META.wOrderInWSlips.join(WorkOrders.META.lastReservation).getName();

			public static final String OPERATION_RESERVATION = WorkOrders.META.lastReservation.getName();

			/**
			 * Statikus osztály: private konstruktor, hogy ne lehessen példányosítani.
			 */
			private Field() {
			}

		}

		/**
		 * Egyke objektum.
		 */
		public static final WorkSlipQuery EXECUTOR = new WorkSlipQuery();

		/**
		 * Konstruktor.
		 */
		private WorkSlipQuery() {
			super(Field.class);
		}

	}

	protected static final class ReservationQuery extends EdpQueryExecutor {

		public static final class Field {

			public static final String ID = Reservations.META.id.getName();

			public static final String OUTSTANDING_QUANTITY = Reservations.META.outstDelQty.getName();

			public static final String PREVIOUS_RESERVATION_ID = Reservations.META.prevResSameLev.getName();

			public static final String SCHEDULED = Reservations.META.scheduled.getName();

			public static final String ELEMENT_TYPE = Reservations.META.elemType.getName();

			public static final String WORK_SLIP_ID = Reservations.META.workSlip.getName();

			public static final String MATERIAL_TRANSITION = Reservations.META.provMatTransition.getName();

			public static final String ORIGINAL_QUANTITY = Reservations.META.unitQty.getName();

		}

		/**
		 * Egyke objektum.
		 */
		public static final ReservationQuery EXECUTOR = new ReservationQuery();

		/**
		 * Konstruktor.
		 */
		private ReservationQuery() {
			super(Field.class);
		}

	}

	/**
	 * Segédosztály a munkaállomáshoz kapcsolódó feladatok lekérdezéséhez.
	 * @author szizo
	 */
	protected class TaskManager0 {

		/**
		 * Az EDP-munkamenet.
		 */
		protected final EDPSession edpSession;

		/**
		 * Konstruktor.
		 * @param edpSession Az EDP-munkamenet.
		 */
		protected TaskManager0(EDPSession edpSession) {
			this.edpSession = edpSession;
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
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.startQuery(new StandardEDPSelection(WorkOrders.META.tableDesc().toString(), new StandardEDPSelectionCriteria(selectionCriteria.build().getCriteria())), WorkSlipQuery.EXECUTOR.getFieldNames());
				while (edpQuery.getNextRecord()) {
					if (isExecutableOperation(edpQuery.getField(WorkSlipQuery.Field.OPERATION_RESERVATION), edpQuery.getField(WorkSlipQuery.Field.WORK_ORDER_SUGGESTION))) {
						taskList.add(new TaskEdpImpl(new IdImpl(edpQuery.getField(WorkSlipQuery.Field.ID))));
					}
				}
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			}
			return taskList;
		}

		protected boolean isExecutableOperation(String operationReservationId, String workOrderSuggestionId) {
			final Map<String, Map<String, String>> reservations = getReservations(workOrderSuggestionId);
			Map<String, String> reservation = reservations.get(operationReservationId);
			if (isCompletedReservation(reservation)) {
				return false;
			}
			while (null != (reservation = reservations.get(reservation.get(ReservationQuery.Field.PREVIOUS_RESERVATION_ID)))) {
				if ("0".equals(reservation.get(ReservationQuery.Field.SCHEDULED))) {
					continue;
				}
				switch (EnumProductionListElementType.fromInternal(reservation.get(ReservationQuery.Field.ELEMENT_TYPE))) {
					case Product:
						if (isOpenMaterialReservation(reservation)) {
							return false;
						}
						break;
					case Operation:
						if (!Id.NULLREF.toString().equals(reservation.get(ReservationQuery.Field.WORK_SLIP_ID))) {
							return (EnumMaterialProvidedTransition.ParallelBeginning == EnumMaterialProvidedTransition.fromInternal(ReservationQuery.Field.MATERIAL_TRANSITION)
									? isPartiallyCompletedReservation(reservation)
									: isCompletedReservation(reservation));
						}
						break;
					default:
				}
			}
			return true;
		}

		protected Map<String, Map<String, String>> getReservations(String workOrderSuggestionId) {
			final Map<String, Map<String, String>> reservations = new HashMap<>();
			final StandardEDPSelectionCriteria selectionCriteria = new StandardEDPSelectionCriteria();
			selectionCriteria.setAliveFlag(EDPConstants.ALIVEFLAG_ALIVE);
			selectionCriteria.set(Reservations.META.reservingProcess.getName() + "==" + workOrderSuggestionId);
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				final String[] fieldNames = ReservationQuery.EXECUTOR.getFieldNames();
				edpQuery.startQuery(new StandardEDPSelection(Reservations.META.tableDesc().toString(), selectionCriteria), fieldNames);
				final int hashMapCapacity = (int)Math.ceil(fieldNames.length / 0.75);
				while (edpQuery.getNextRecord()) {
					final Map<String, String> reservationData = new HashMap<>(hashMapCapacity);
					for (String fieldName : fieldNames) {
						reservationData.put(fieldName, edpQuery.getField(fieldName));
					}
					reservations.put(edpQuery.getField(ReservationQuery.Field.ID), reservationData);
				}
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			}
			return reservations;
		}

		/**
		 * @param reservation A vizsgálandó foglalás.
		 * @return A foglalás lezárt, azaz a nyitott mennyisége nulla vagy negatív?
		 */
		public boolean isCompletedReservation(Map<String, String> reservation) {
			return (0 >= new BigDecimal(reservation.get(ReservationQuery.Field.OUTSTANDING_QUANTITY)).signum());
		}

		/**
		 * @param materialReservation A vizsgálandó anyagfoglalás.
		 * @return Az anyagfoglalás nyitott, azaz (legalább részben) nem készletfedezetű?
		 */
		public boolean isOpenMaterialReservation(Map<String, String> materialReservation) {
			if (isCompletedReservation(materialReservation)) {
				return false;
			}
			final SelectionBuilder<MaterialsAllocation> selectionCriteria = SelectionBuilder.create(MaterialsAllocation.class);
			selectionCriteria.setFilingMode(EnumFilingModeExtended.Both);
			selectionCriteria.add(Conditions.eq(MaterialsAllocation.META.res.getName(), materialReservation.get(ReservationQuery.Field.ID)));
			selectionCriteria.add(Conditions.eq(MaterialsAllocation.META.scheduled, true));
			selectionCriteria.add(Conditions.eq(MaterialsAllocation.META.active, true));
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.startQuery(new StandardEDPSelection(MaterialsAllocation.META.tableDesc().toString(), new StandardEDPSelectionCriteria(selectionCriteria.build().getCriteria())), MaterialsAllocation.META.procure.getName());
				if (edpQuery.getNextRecord()) {
					if (!Id.NULLREF.toString().equals(edpQuery.getField(1))) {
						edpQuery.breakQuery();
						return true;
					}
				}
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			}
			return false;
		}

		/**
		 * @param reservation A vizsgálandó foglalás.
		 * @return A foglalás legalább részben lezárt, azaz a nyitott mennyisége kevesebb, mint az eredeti mennyisége?
		 */
		public boolean isPartiallyCompletedReservation(Map<String, String> reservation) {
			return (1 == (new BigDecimal(reservation.get(ReservationQuery.Field.ORIGINAL_QUANTITY))).compareTo(new BigDecimal(reservation.get(ReservationQuery.Field.OUTSTANDING_QUANTITY))));
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
			final EDPQuery edpQuery = edpSession.createQuery();
			try {
				edpQuery.enableQueryMetaData(false);
				edpQuery.startQuery(new StandardEDPSelection(WorkOrders.META.tableDesc().toString(), new StandardEDPSelectionCriteria(createExecutableWorkSlipSelectionBuilder().build().getCriteria())), WorkSlipQuery.EXECUTOR.getFieldNames());
				while (edpQuery.getNextRecord()) {
					if (isExecutableOperation(edpQuery.getField(WorkSlipQuery.Field.OPERATION_RESERVATION), edpQuery.getField(WorkSlipQuery.Field.WORK_ORDER_SUGGESTION))) {
						edpQuery.breakQuery();
						return (new TaskEdpImpl(new IdImpl(edpQuery.getField(WorkSlipQuery.Field.ID))));
					}
				}
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			}
			return null;
		}

	}

	/**
	 * Segédosztály a munkaállomáshoz kapcsolódó feladatok lekérdezéséhez.
	 * @author szizo
	 */
	protected class TaskManager {

		/**
		 * Az EDP-munkamenet.
		 */
		protected final EDPSession edpSession;

		/**
		 * Konstruktor.
		 * @param edpSession Az EDP-munkamenet.
		 */
		protected TaskManager(EDPSession edpSession) {
			this.edpSession = edpSession;
		}

		/**
		 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa (AbasDate.INFINITY, ha nincs szükség időkorlátra).
		 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt feladatok listája.
		 */
		public List<Task> getUnassignedTasks(AbasDate startDateUntil) {
			return createTaskList(0, false, startDateUntil);
		}

		/**
		 * A munkalap-szűrés végrehajtása.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param suspendedTasks A felfüggesztett munkalapokat kell kilistázni?
		 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa (null, ha nincs szükség időkorlátra).
		 * @return A szűrésnek megfelelő feladatok listája.
		 */
		protected List<Task> createTaskList(int workStationNumber, boolean suspendedTasks, AbasDate startDateUntil) {
			final EDPEditor infoSystemQuery = edpSession.createEditor();
			try {
				infoSystemQuery.beginEditCmd(Integer.toString(EnumTypeCommands.Infosystem.getCode()), "MES");
				infoSystemQuery.setFieldVal(InfosysOw1MES.META.ymgr.getName(), workCenterId);
				infoSystemQuery.setFieldVal(InfosysOw1MES.META.ymnum.getName(), Integer.toString(workStationNumber));
				infoSystemQuery.setFieldVal(InfosysOw1MES.META.ystop.getName(), Boolean.toString(suspendedTasks));
				if (null != startDateUntil) {
					infoSystemQuery.setFieldVal(InfosysOw1MES.META.ysterm.getName(), startDateUntil.toString());
				}
				infoSystemQuery.setFieldVal(InfosysOw1MES.META.start.getName(), "");
				final int rowCount = infoSystemQuery.getRowCount();
				switch (rowCount) {
					case 0:
						return Collections.emptyList();
					case 1:
						return Collections.singletonList(new TaskEdpImpl(new IdImpl(infoSystemQuery.getFieldVal(1, InfosysOw1MES.Row.META.ytas.getName()))));
					default:
						final List<Task> taskList = new ArrayList<>(rowCount);
						for (int i = 1; i <= rowCount; i++) {
							taskList.add(new TaskEdpImpl(new IdImpl(infoSystemQuery.getFieldVal(i, InfosysOw1MES.Row.META.ytas.getName()))));
						}
						return taskList;
				}
			} catch (Exception e) {
				throw new EDPRuntimeException(e);
			} finally {
				if (infoSystemQuery.isActive()) {
					infoSystemQuery.endEditCancel();
				}
			}
		}

		/**
		 * @return A munkaállomáshoz hozzárendelt, de felfüggesztett feladatok listája.
		 */
		public List<Task> getSuspendedTasks() {
			return createTaskList(number, true, null);
		}

		/**
		 * @return A munkaállomáshoz hozzárendelt és végrehajtható feladatok listája.
		 */
		public List<Task> getExecutableTasks() {
			return createTaskList(number, false, null);
		}

		/**
		 * @return A munkaállomás következő végrehajtható feladata (null, ha a munkaállomás feladatlistája üres).
		 */
		public Task getNextExecutableTask() {
			final List<Task> taskList = getExecutableTasks();
			return (taskList.isEmpty() ? null : taskList.get(0));
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 5481097916115784173L;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy gépcsoportot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param edpSession Az EDP-munkamenet.
	 * @return Abas-gépcsoportazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező gépcsoport azonosítója.
	 */
	protected static Id workCenterIdFilter(Id abasObjectId, EDPSession edpSession) {
		if (8 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + abasObjectId);
		}
		final EDPQuery edpQuery = edpSession.createQuery();
		try {
			edpQuery.enableQueryMetaData(false);
			if (!edpQuery.readRecord(abasObjectId.toString(), WorkCenter.META.id.getName())) {
				throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + abasObjectId);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + abasObjectId, e);
		}
		return abasObjectId;
	}

	/**
	 * Abas-beli gépcsoport azonosítójának megkeresése hivatkozási szám alapján.
	 * @param workCenterIdNo Az Abas-beli gépcsoport hivatkozási száma.
	 * @param edpSession Az EDP-munkamenet.
	 * @return Az Abas-beli gépcsoport azonosítója.
	 */
	protected static String getWorkCenterId(String workCenterIdNo, EDPSession edpSession) {
		final StandardEDPSelectionCriteria selectionCriteria = new StandardEDPSelectionCriteria();
		selectionCriteria.setAliveFlag(EDPConstants.ALIVEFLAG_ALIVE);
		selectionCriteria.set(WorkCenter.META.idno.getName() + "==" + workCenterIdNo);
		final EDPQuery edpQuery = edpSession.createQuery();
		try {
			edpQuery.enableQueryMetaData(false);
			edpQuery.startQuery(new StandardEDPSelection(WorkCenter.META.tableDesc().toString(), selectionCriteria), WorkCenter.META.id.getName());
			if (edpQuery.getNextRecord()) {
				return edpQuery.getField(1);
			}
			throw new EDPRuntimeException("Ismeretlen gépcsoport: " + workCenterIdNo);
		} catch (Exception e) {
			throw new EDPRuntimeException(e);
		}
	}

	/**
	 * Konstruktor.
	 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param edpSession Az EDP-munkamenet.
	 */
	public WorkStationEdpImpl(Id workCenterId, int workStationNumber, EDPSession edpSession) {
		super(workCenterIdFilter(workCenterId, edpSession).toString(), workStationNumber);
	}

	/**
	 * Konstruktor.
	 * @param workCenterIdNo Az Abas-beli gépcsoport hivatkozási száma.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param edpSession Az EDP-munkamenet.
	 */
	public WorkStationEdpImpl(String workCenterIdNo, int workStationNumber, EDPSession edpSession) {
		super(getWorkCenterId(workCenterIdNo, edpSession), workStationNumber);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getUnassignedTasks(de.abas.erp.common.type.AbasDate, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public List<Task> getUnassignedTasks(AbasDate startDateUntil, AbasConnection<?> abasConnection) {
		return (new TaskManager(EdpConnection.getEdpSession(abasConnection))).getUnassignedTasks(startDateUntil);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getSuspendedTasks(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public List<Task> getSuspendedTasks(AbasConnection<?> abasConnection) {
		return (new TaskManager(EdpConnection.getEdpSession(abasConnection))).getSuspendedTasks();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getExecutableTasks(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public List<Task> getExecutableTasks(AbasConnection<?> abasConnection) {
		return (new TaskManager(EdpConnection.getEdpSession(abasConnection))).getExecutableTasks();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getNextExecutableTask(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public Task getNextExecutableTask(AbasConnection<?> abasConnection) {
		return (new TaskManager(EdpConnection.getEdpSession(abasConnection))).getNextExecutableTask();
	}

	@Override
	public void setTaskExecutionOrder(Id taskId, Id precedingTaskId, AbasConnection<?> abasConnection) {
		// TODO Auto-generated method stub
	}

}
