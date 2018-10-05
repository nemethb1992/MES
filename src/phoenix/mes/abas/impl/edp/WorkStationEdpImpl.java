/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 27, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.CantChangeFieldValException;
import de.abas.ceks.jedp.EDPConstants;
import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.StandardEDPSelection;
import de.abas.ceks.jedp.StandardEDPSelectionCriteria;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESWSLIPS;
import de.abas.erp.db.schema.capacity.WorkCenter;

import java.util.List;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.Task;
import phoenix.mes.abas.impl.WorkStationImpl;

/**
 * Gyártási munkaállomás osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class WorkStationEdpImpl extends WorkStationImpl {

	/**
	 * Segédosztály a munkaállomáshoz kapcsolódó gyártási feladatok lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class WorkSlipQuery extends InfoSystemTableConverter<Task> {

		/**
		 * A szűrőmezők nevei a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt gyártási feladatok lekérdezéséhez.
		 */
		protected static final String[] unassignedTasksFilterCriteria = {InfosysOw1MESWSLIPS.META.ymgr.getName(), InfosysOw1MESWSLIPS.META.ystermig.getName(), InfosysOw1MESWSLIPS.META.start.getName()};

		/**
		 * A szűrőmezők nevei a munkaállomáshoz hozzárendelt, de felfüggesztett gyártási feladatok lekérdezéséhez.
		 */
		protected static final String[] suspendedTasksFilterCriteria = {InfosysOw1MESWSLIPS.META.ymgr.getName(), InfosysOw1MESWSLIPS.META.ymnum.getName(), InfosysOw1MESWSLIPS.META.ymegszak.getName(), InfosysOw1MESWSLIPS.META.start.getName()};

		/**
		 * A szűrőmezők nevei a munkaállomáshoz hozzárendelt és végrehajtható gyártási feladatok lekérdezéséhez.
		 */
		protected static final String[] executableTasksFilterCriteria = {InfosysOw1MESWSLIPS.META.ymgr.getName(), InfosysOw1MESWSLIPS.META.ymnum.getName(), InfosysOw1MESWSLIPS.META.start.getName()};

		/**
		 * A szűrőmezők nevei a munkaállomás következő végrehajtható gyártási feladatának lekérdezéséhez.
		 */
		protected static final String[] nextExecutableTaskFilterCriteria = {InfosysOw1MESWSLIPS.META.ymgr.getName(), InfosysOw1MESWSLIPS.META.ymnum.getName(), InfosysOw1MESWSLIPS.META.ycsakelso.getName(), InfosysOw1MESWSLIPS.META.start.getName()};

		/**
		 * Egyke objektum.
		 */
		public static final WorkSlipQuery EXECUTOR = new WorkSlipQuery();

		/**
		 * Konstruktor.
		 */
		private WorkSlipQuery() {
			super("MESWSLIPS", new String[] {InfosysOw1MESWSLIPS.Row.META.ytas.getName()});
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(de.abas.ceks.jedp.EDPEditFieldList)
		 */
		@Override
		protected TaskEdpImpl createRowObject(EDPEditFieldList rowData) {
			return new TaskEdpImpl(new IdImpl(rowData.getField(1).getValue()));
		}

		/**
		 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
		 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa (AbasDate.INFINITY, ha nincs szükség időkorlátra).
		 * @param edpSession Az EDP-munkamenet.
		 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt gyártási feladatok listája.
		 */
		public List<Task> getUnassignedTasks(String workCenterId, AbasDate startDateUntil, EDPSession edpSession) {
			try {
				return getRows(new EDPEditFieldList(unassignedTasksFilterCriteria, new String[] {workCenterId, startDateUntil.toString(), " "}), edpSession);
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A munkaállomáshoz hozzárendelt, de felfüggesztett gyártási feladatok listája.
		 */
		public List<Task> getSuspendedTasks(String workCenterId, int workStationNumber, EDPSession edpSession) {
			try {
				return getRows(new EDPEditFieldList(suspendedTasksFilterCriteria, new String[] {workCenterId, Integer.toString(workStationNumber), "1", " "}), edpSession);
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A munkaállomáshoz hozzárendelt és végrehajtható gyártási feladatok listája.
		 */
		public List<Task> getExecutableTasks(String workCenterId, int workStationNumber, EDPSession edpSession) {
			try {
				return getRows(new EDPEditFieldList(executableTasksFilterCriteria, new String[] {workCenterId, Integer.toString(workStationNumber), " "}), edpSession);
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * @param workCenterId Az Abas-beli gépcsoport azonosítója.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A munkaállomás következő végrehajtható gyártási feladata (null, ha a munkaállomás feladatlistája üres).
		 */
		public Task getNextExecutableTask(String workCenterId, int workStationNumber, EDPSession edpSession) {
			try {
				final List<Task> taskList = getRows(new EDPEditFieldList(nextExecutableTaskFilterCriteria, new String[] {workCenterId, Integer.toString(workStationNumber), "1", " "}), edpSession);
				return (taskList.isEmpty() ? null : taskList.get(0));
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
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
		return WorkSlipQuery.EXECUTOR.getUnassignedTasks(workCenterId, startDateUntil, EdpConnection.getEdpSession(abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getSuspendedTasks(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public List<Task> getSuspendedTasks(AbasConnection<?> abasConnection) {
		return WorkSlipQuery.EXECUTOR.getSuspendedTasks(workCenterId, number, EdpConnection.getEdpSession(abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getExecutableTasks(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public List<Task> getExecutableTasks(AbasConnection<?> abasConnection) {
		return WorkSlipQuery.EXECUTOR.getExecutableTasks(workCenterId, number, EdpConnection.getEdpSession(abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#getNextExecutableTask(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public Task getNextExecutableTask(AbasConnection<?> abasConnection) {
		return WorkSlipQuery.EXECUTOR.getNextExecutableTask(workCenterId, number, EdpConnection.getEdpSession(abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.WorkStation#setTaskExecutionOrder(de.abas.erp.common.type.Id, de.abas.erp.common.type.Id, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public void setTaskExecutionOrder(Id taskId, Id precedingTaskId, AbasConnection<?> abasConnection) {
		// TODO
		throw new RuntimeException("Not yet implemented!");
	}

}
