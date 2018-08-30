/*
 * Osztálykönyvtár az AJO interfészhez.
 *
 * Created on Aug 21, 2018
 */

package phoenix.mes.abas.ajo;

import de.abas.ceks.jedp.DefaultEDPCredentialsProvider;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.enums.EnumLanguageCode;
import de.abas.erp.db.DbContext;

import javax.security.auth.login.LoginException;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;

/**
 * Gyár osztály az AJO interfész objektumainak létrehozásához.
 * @author szizo
 */
public class AjoObjectFactory implements AbasObjectFactory<DbContext> {

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasObjectFactory#openAbasConnection(java.lang.String, java.lang.String, de.abas.erp.common.type.enums.EnumLanguageCode, boolean)
	 */
	@Override
	public AjoConnection openAbasConnection(String userName, String password, EnumLanguageCode operatingLanguage, boolean testSystem) throws LoginException {
		return (new AjoConnection(new DefaultEDPCredentialsProvider(userName, password), operatingLanguage, testSystem));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasObjectFactory#createWorkStation(java.lang.String, int, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public WorkStationAjoImpl createWorkStation(String workCenterIdNo, int workStationNumber, AbasConnection<DbContext> abasConnection) {
		return (new WorkStationAjoImpl(workCenterIdNo, workStationNumber, abasConnection.getConnectionObject()));
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.AbasObjectFactory#createTask(de.abas.erp.common.type.Id, phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public TaskAjoImpl createTask(Id workSlipId, AbasConnection<DbContext> abasConnection) {
		return (new TaskAjoImpl(workSlipId, abasConnection.getConnectionObject()));
	}

}
