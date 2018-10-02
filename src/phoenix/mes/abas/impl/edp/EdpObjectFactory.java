/*
 * Osztálykönyvtár az EDP interfészhez.
 *
 * Created on Aug 22, 2018
 */

package phoenix.mes.abas.impl.edp;

import de.abas.ceks.jedp.DefaultEDPCredentialsProvider;
import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.Id;

import javax.security.auth.login.LoginException;

import phoenix.mes.OperatingLanguage;
import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.WorkStation;

/**
 * Gyár osztály az EDP interfész objektumainak létrehozásához.
 * @author szizo
 */
public class EdpObjectFactory implements AbasObjectFactory<EDPSession> {

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasObjectFactory#openAbasConnection(java.lang.String, java.lang.String, phoenix.mes.OperatingLanguage, boolean)
	 */
	@Override
	public EdpConnection openAbasConnection(String userName, String password, OperatingLanguage operatingLanguage, boolean testSystem) throws LoginException {
		return (new EdpConnection(new DefaultEDPCredentialsProvider(userName, password), operatingLanguage, testSystem));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasObjectFactory#createWorkStation(java.lang.String, int, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public WorkStation createWorkStation(String workCenterIdNo, int workStationNumber, AbasConnection<EDPSession> abasConnection) {
		return (new WorkStationEdpImpl(workCenterIdNo, workStationNumber, abasConnection.getConnectionObject()));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasObjectFactory#createTask(de.abas.erp.common.type.Id, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public TaskEdpImpl createTask(Id workSlipId, AbasConnection<EDPSession> abasConnection) {
		return (new TaskEdpImpl(workSlipId, abasConnection.getConnectionObject()));
	}

}