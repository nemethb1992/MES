/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 27, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.CantChangeFieldValException;
import de.abas.ceks.jedp.EDPEditFieldList;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.infosystem.custom.ow1.InfosysOw1MESWORKSTATION;
import de.abas.erp.db.schema.capacity.WorkCenter;

import java.util.List;

import phoenix.mes.abas.GenericAbasConnection;
import phoenix.mes.abas.GenericWorkCenter;
import phoenix.mes.abas.WorkStation;
import phoenix.mes.abas.impl.WorkStationImpl;

/**
 * Gyártási munkaállomás osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class WorkStationEdpImpl extends WorkStationImpl<EDPSession> implements WorkStation {

	/**
	 * Segédosztály a munkaállomáshoz kapcsolódó gyártási feladatok lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class WorkSlipQuery extends InfoSystemTableConverter<TaskEdpImpl> {

		/**
		 * A szűrőmezők nevei a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt gyártási feladatok lekérdezéséhez.
		 */
		protected static final String[] unassignedTasksFilterCriteria = {InfosysOw1MESWORKSTATION.META.ymgr.getName(), InfosysOw1MESWORKSTATION.META.ystermig.getName(), InfosysOw1MESWORKSTATION.META.start.getName()};

		/**
		 * A szűrőmezők nevei a munkaállomásra beütemezett és végrehajtható gyártási feladatok lekérdezéséhez.
		 */
		protected static final String[] scheduledTasksFilterCriteria = {InfosysOw1MESWORKSTATION.META.ymgr.getName(), InfosysOw1MESWORKSTATION.META.ygepszam.getName(), InfosysOw1MESWORKSTATION.META.start.getName()};

		/**
		 * A szűrőmezők nevei a munkaállomás első beütemezett és végrehajtható gyártási feladatának lekérdezéséhez.
		 */
		protected static final String[] firstScheduledTaskFilterCriteria = {InfosysOw1MESWORKSTATION.META.ymgr.getName(), InfosysOw1MESWORKSTATION.META.ygepszam.getName(), InfosysOw1MESWORKSTATION.META.ycsakelso.getName(), InfosysOw1MESWORKSTATION.META.start.getName()};

		/**
		 * Egyke objektum.
		 */
		public static final WorkSlipQuery EXECUTOR = new WorkSlipQuery();

		/**
		 * Konstruktor.
		 */
		private WorkSlipQuery() {
			super("MESWORKSTATION", new String[] {InfosysOw1MESWORKSTATION.Row.META.ytas.getName()});
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.edp.InfoSystemTableConverter#createRowObject(phoenix.mes.abas.impl.edp.InfoSystemExecutor.FieldValues)
		 */
		@Override
		protected TaskEdpImpl createRowObject(FieldValues rowData) {
			return new TaskEdpImpl(rowData.fieldValues.getField(1).getValue());
		}

		/**
		 * @param workCenterIdNo A gépcsoport hivatkozási száma.
		 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa (AbasDate.INFINITY, ha nincs szükség időkorlátra).
		 * @param edpSession Az EDP-munkamenet.
		 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt gyártási feladatok listája.
		 */
		public List<TaskEdpImpl> getUnassignedTasks(String workCenterIdNo, AbasDate startDateUntil, EDPSession edpSession) {
			try {
				return getRows(new EDPEditFieldList(unassignedTasksFilterCriteria, new String[] {workCenterIdNo, startDateUntil.toString(), " "}), edpSession);
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * @param workCenterIdNo A gépcsoport hivatkozási száma.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A munkaállomásra beütemezett és végrehajtható gyártási feladatok listája.
		 */
		public List<TaskEdpImpl> getScheduledTasks(String workCenterIdNo, int workStationNumber, EDPSession edpSession) {
			try {
				return getRows(new EDPEditFieldList(scheduledTasksFilterCriteria, new String[] {workCenterIdNo, Integer.toString(workStationNumber), " "}), edpSession);
			} catch (CantChangeFieldValException e) {
				throw new EDPRuntimeException(e);
			}
		}

		/**
		 * @param workCenterIdNo A gépcsoport hivatkozási száma.
		 * @param workStationNumber A munkaállomás sorszáma.
		 * @param edpSession Az EDP-munkamenet.
		 * @return A munkaállomásra elsőként beütemezett és végrehajtható gyártási feladat (null, ha a munkaállomás feladatlistája üres).
		 */
		public TaskEdpImpl getFirstScheduledTask(String workCenterIdNo, int workStationNumber, EDPSession edpSession) {
			try {
				final List<TaskEdpImpl> taskList = getRows(new EDPEditFieldList(firstScheduledTaskFilterCriteria, new String[] {workCenterIdNo, Integer.toString(workStationNumber), "1", " "}), edpSession);
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
	 * Konstruktor.
	 * @param workCenter A gépcsoport.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	public WorkStationEdpImpl(GenericWorkCenter<?> workCenter, int workStationNumber) {
		super(workCenter.getIdNo(), workStationNumber);
	}

	/**
	 * Konstruktor.
	 * @param workCenterIdNo Az Abas-beli gépcsoport hivatkozási száma.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 * @param edpConnection Az EDP-kapcsolat.
	 */
	public WorkStationEdpImpl(String workCenterIdNo, int workStationNumber, GenericAbasConnection<EDPSession> edpConnection) {
		this(WorkCenterEdpImpl.getWorkCenterData(workCenterIdNo, edpConnection), workStationNumber);
	}

	/**
	 * Konstruktor.
	 * @param workCenterData A gépcsoport adatai.
	 * @param workStationNumber A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	protected WorkStationEdpImpl(EDPQuery workCenterData, int workStationNumber) {
		super(workCenterData.getField(WorkCenter.META.idno.getName()), workStationNumber);
		workCenterData.breakQuery();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#getWorkCenter()
	 */
	@Override
	public WorkCenterEdpImpl getWorkCenter() {
		return new WorkCenterEdpImpl(workCenterIdNo);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#getUnassignedTasks(de.abas.erp.common.type.AbasDate, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public List<TaskEdpImpl> getUnassignedTasks(AbasDate startDateUntil, GenericAbasConnection<EDPSession> edpConnection) {
		return WorkSlipQuery.EXECUTOR.getUnassignedTasks(workCenterIdNo, startDateUntil, edpConnection.getConnectionObject());
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#getScheduledTasks(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public List<TaskEdpImpl> getScheduledTasks(GenericAbasConnection<EDPSession> edpConnection) {
		return WorkSlipQuery.EXECUTOR.getScheduledTasks(workCenterIdNo, number, edpConnection.getConnectionObject());
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#startFirstScheduledTask(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public TaskEdpImpl startFirstScheduledTask(GenericAbasConnection<EDPSession> edpConnection) {
		return WorkSlipQuery.EXECUTOR.getFirstScheduledTask(workCenterIdNo, number, edpConnection.getConnectionObject());
	}

}
