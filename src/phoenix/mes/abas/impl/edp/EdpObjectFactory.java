/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.Id;

import java.util.Locale;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.GenericAbasObjectFactory;
import phoenix.mes.abas.GenericWorkCenter;

/**
 * Gyár osztály az EDP interfész objektumainak létrehozásához.
 * @author szizo
 */
public class EdpObjectFactory implements GenericAbasObjectFactory<EDPSession, EdpConnection> {

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#openAbasConnection(java.lang.String, java.lang.String, java.util.Locale, boolean)
	 */
	@Override
	public EdpConnection openAbasConnection(String userName, String password, Locale locale, boolean testSystem) throws LoginException {
		return new EdpConnection(userName, password, locale, testSystem);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkCenter(java.lang.String, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkCenterEdpImpl createWorkCenter(String idNo, EdpConnection edpConnection) {
		return new WorkCenterEdpImpl(idNo, edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkCenter(de.abas.erp.common.type.Id, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkCenterEdpImpl createWorkCenter(Id id, EdpConnection edpConnection) {
		return new WorkCenterEdpImpl(id, edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkStation(phoenix.mes.abas.GenericWorkCenter, int, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkStationEdpImpl createWorkStation(GenericWorkCenter<?> workCenter, int workStationNumber, EdpConnection edpConnection) {
		return new WorkStationEdpImpl(workCenter, workStationNumber);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkStation(de.abas.erp.common.type.Id, int, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkStationEdpImpl createWorkStation(Id workCenterId, int workStationNumber, EdpConnection edpConnection) {
		return new WorkStationEdpImpl(workCenterId, workStationNumber, edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkStation(java.lang.String, int, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkStationEdpImpl createWorkStation(String workCenterIdNo, int workStationNumber, EdpConnection edpConnection) {
		return new WorkStationEdpImpl(workCenterIdNo, workStationNumber, edpConnection);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createTask(de.abas.erp.common.type.Id, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public TaskEdpImpl createTask(Id workSlipId, EdpConnection edpConnection) {
		return new TaskEdpImpl(workSlipId, edpConnection);
	}

}
