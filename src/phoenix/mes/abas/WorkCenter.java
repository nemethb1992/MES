/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 19, 2019
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPSession;

/**
 * Gépcsoport adattípusa, az alapértelmezett típusú Abas-kapcsolattal.
 * @author szizo
 */
public interface WorkCenter extends GenericWorkCenter<EDPSession> {
}
