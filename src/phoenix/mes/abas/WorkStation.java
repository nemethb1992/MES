/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 16, 2019
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPSession;
import de.abas.erp.common.type.AbasDate;

import java.util.List;

/**
 * Gyártási munkaállomás adattípusa, az alapértelmezett típusú Abas-kapcsolattal.
 * @author szizo
 */
public interface WorkStation extends GenericWorkStation<EDPSession> {

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#getUnassignedTasks(de.abas.erp.common.type.AbasDate, phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	List<? extends Task> getUnassignedTasks(AbasDate startDateUntil, GenericAbasConnection<EDPSession> abasConnection);

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#getScheduledTasks(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	List<? extends Task> getScheduledTasks(GenericAbasConnection<EDPSession> abasConnection);

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericWorkStation#startFirstScheduledTask(phoenix.mes.abas.GenericAbasConnection)
	 */
	@Override
	Task startFirstScheduledTask(GenericAbasConnection<EDPSession> abasConnection);

}
