/*
 * Domain típusok könyvtára.
 *
 * Created on Jun 16, 2019
 */

package phoenix.mes.abas;

import de.abas.ceks.jedp.EDPSession;

/**
 * Gyártási feladat adattípusa, az alapértelmezett típusú Abas-kapcsolattal.
 * @author szizo
 */
public interface Task extends GenericTask<EDPSession> {
}
