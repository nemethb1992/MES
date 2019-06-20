/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.EDPConstants;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPRuntimeException;
import de.abas.ceks.jedp.EDPSession;
import de.abas.ceks.jedp.InvalidQueryException;
import de.abas.ceks.jedp.StandardEDPSelection;
import de.abas.ceks.jedp.StandardEDPSelectionCriteria;
import de.abas.erp.common.type.Id;
import de.abas.erp.db.schema.capacity.WorkCenter;

import phoenix.mes.abas.GenericAbasConnection;
import phoenix.mes.abas.impl.WorkCenterDetails;
import phoenix.mes.abas.impl.WorkCenterImpl;

/**
 * Gépcsoport osztálya, EDP-ben implementálva.
 * @author szizo
 */
public class WorkCenterEdpImpl extends WorkCenterImpl<EDPSession> implements phoenix.mes.abas.WorkCenter {

	/**
	 * Segédosztály a gépcsoport adatainak lekérdezéséhez.
	 * @author szizo
	 */
	protected static final class WorkCenterQuery extends EdpQueryExecutor {

		/**
		 * A lekérdezendő (fejrész)mezők nevei.
		 * @author szizo
		 */
		public static final class Field {

			/**
			 * A gépcsoport Abas-beli azonosítója.
			 */
			public static final String ID = WorkCenter.META.id.getName();

			/**
			 * A gépcsoport megnevezése az aktuálisan beállított kezelőnyelven.
			 */
			public static final String DESCRIPTION = WorkCenter.META.descrOperLang.getName();

		}

		/**
		 * Egyke objektum.
		 */
		public static final WorkCenterQuery EXECUTOR = new WorkCenterQuery();

		/**
		 * Konstruktor.
		 */
		private WorkCenterQuery() {
			super(Field.class);
		}

	}

	/**
	 * Gépcsoport adatait leíró osztály, EDP-ben implementálva.
	 * @author szizo
	 */
	protected class WorkCenterDetailsEdpImpl extends WorkCenterDetails<EDPSession> {

		/**
		 * Konstruktor.
		 * @param edpConnection Az EDP-kapcsolat.
		 */
		protected WorkCenterDetailsEdpImpl(GenericAbasConnection<EDPSession> edpConnection) {
			super(edpConnection);
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.impl.LanguageDependentCache#resetCache(phoenix.mes.abas.GenericAbasConnection)
		 */
		@Override
		protected void resetCache(GenericAbasConnection<EDPSession> edpConnection) {
			loadCache(loadWorkCenterData(id, edpConnection));
		}

		/**
		 * @param workCenterData A gépcsoport adatai.
		 */
		protected void loadCache(EDPQuery workCenterData) {
			description = workCenterData.getField(WorkCenterQuery.Field.DESCRIPTION);
			workCenterData.breakQuery();
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 1012924355218098399L;

	/**
	 * @param idNo A gépcsoport hivatkozási száma.
	 * @param edpConnection Az EDP-kapcsolat.
	 * @return A gépcsoport adatai.
	 */
	protected static EDPQuery getWorkCenterData(String idNo, GenericAbasConnection<EDPSession> edpConnection) {
		final StandardEDPSelectionCriteria selectionCriteria = new StandardEDPSelectionCriteria();
		selectionCriteria.setAliveFlag(EDPConstants.ALIVEFLAG_ALIVE);
		selectionCriteria.set(WorkCenter.META.idno.getName() + "==" + idNo);
		final EDPQuery edpQuery = edpConnection.getConnectionObject().createQuery();
		try {
			edpQuery.enableQueryMetaData(false);
			edpQuery.startQuery(new StandardEDPSelection(WorkCenter.META.tableDesc().toString(), selectionCriteria), WorkCenterQuery.EXECUTOR.getFieldNames());
			if (edpQuery.getNextRecord()) {
				return edpQuery;
			}
			throw new IllegalArgumentException("Ismeretlen gépcsoport: " + idNo);
		} catch (InvalidQueryException e) {
			throw new EDPRuntimeException(e);
		}
	}

	/**
	 * A gépcsoport adatainak betöltése.
	 * @param id A gépcsoport Abas-beli azonosítója.
	 * @param edpConnection Az EDP-kapcsolat.
	 * @return A gépcsoport adatai.
	 */
	protected static EDPQuery loadWorkCenterData(Id id, GenericAbasConnection<EDPSession> edpConnection) {
		if (8 != id.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + id);
		}
		return loadWorkCenterData(id.toString(), edpConnection);
	}

	/**
	 * A gépcsoport adatainak betöltése.
	 * @param id A gépcsoport Abas-beli azonosítója.
	 * @param edpConnection Az EDP-kapcsolat.
	 * @return A gépcsoport adatai.
	 */
	protected static EDPQuery loadWorkCenterData(String id, GenericAbasConnection<EDPSession> edpConnection) {
		final EDPQuery edpQuery = WorkCenterQuery.EXECUTOR.readRecord(id, edpConnection.getConnectionObject());
		if (null == edpQuery) {
			throw new IllegalArgumentException("Nem gépcsoport-azonosító: " + id);
		}
		return edpQuery;
	}

	/**
	 * Konstruktor.
	 * @param idNo A gépcsoport hivatkozási száma.
	 * @param edpConnection Az EDP-kapcsolat.
	 */
	public WorkCenterEdpImpl(String idNo, GenericAbasConnection<EDPSession> edpConnection) {
		this(getWorkCenterData(idNo, edpConnection), edpConnection);
	}

	/**
	 * Konstruktor.
	 * @param id A gépcsoport Abas-beli azonosítója.
	 * @param edpConnection Az EDP-kapcsolat.
	 */
	public WorkCenterEdpImpl(Id id, GenericAbasConnection<EDPSession> edpConnection) {
		this(loadWorkCenterData(id, edpConnection), edpConnection);
	}

	/**
	 * Konstruktor.
	 * @param workCenterData A gépcsoport adatai.
	 * @param edpConnection Az EDP-kapcsolat.
	 */
	protected WorkCenterEdpImpl(EDPQuery workCenterData, GenericAbasConnection<EDPSession> edpConnection) {
		super(workCenterData.getField(WorkCenterQuery.Field.ID));
		details = newDetails(edpConnection);
		((WorkCenterDetailsEdpImpl)details).loadCache(workCenterData);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.impl.CachedDetailsContainer#newDetails(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	protected WorkCenterDetailsEdpImpl newDetails(GenericAbasConnection<EDPSession> edpConnection) {
		return new WorkCenterDetailsEdpImpl(edpConnection);
	}

}
