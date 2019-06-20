/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Jun 16, 2019
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.Id;

import java.util.Locale;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.impl.edp.EdpObjectFactory;

/**
 * Az Abas-interfész alapértelmezett típusú Abas-kapcsolathoz tartozó gyár osztálya.
 * @author szizo
 */
public class AbasObjectFactory implements GenericAbasObjectFactory<EDPSession, AbasConnection> {

	/**
	 * Egyke objektum.
	 */
	public static final AbasObjectFactory INSTANCE = new AbasObjectFactory();

	/**
	 * Az alapértelmezett típusú Abas-kapcsolathoz tartozó tényleges gyár objektum.
	 */
	protected static final GenericAbasObjectFactory<EDPSession, ? super AbasConnection> DELEGATEE = new EdpObjectFactory();

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#openAbasConnection(java.lang.String, java.lang.String, java.util.Locale, boolean)
	 */
	@Override
	public AbasConnection openAbasConnection(String userName, String password, Locale locale, boolean testSystem) throws LoginException {
		return new AbasConnection(userName, password, locale, testSystem);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkCenter(java.lang.String, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkCenter createWorkCenter(String idNo, AbasConnection abasConnection) {
		return (WorkCenter)(DELEGATEE.createWorkCenter(idNo, abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkCenter(de.abas.erp.common.type.Id, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkCenter createWorkCenter(Id id, AbasConnection abasConnection) {
		return (WorkCenter)(DELEGATEE.createWorkCenter(id, abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkStation(phoenix.mes.abas.GenericWorkCenter, int, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkStation createWorkStation(GenericWorkCenter<?> workCenter, int workStationNumber, AbasConnection abasConnection) {
		return (WorkStation)(DELEGATEE.createWorkStation(workCenter, workStationNumber, abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkStation(de.abas.erp.common.type.Id, int, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkStation createWorkStation(Id workCenterId, int workStationNumber, AbasConnection abasConnection) {
		return (WorkStation)(DELEGATEE.createWorkStation(workCenterId, workStationNumber, abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createWorkStation(java.lang.String, int, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public WorkStation createWorkStation(String workCenterIdNo, int workStationNumber, AbasConnection abasConnection) {
		return (WorkStation)(DELEGATEE.createWorkStation(workCenterIdNo, workStationNumber, abasConnection));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericAbasObjectFactory#createTask(de.abas.erp.common.type.Id, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	public Task createTask(Id workSlipId, AbasConnection abasConnection) {
		return (Task)(DELEGATEE.createTask(workSlipId, abasConnection));
	}

}
