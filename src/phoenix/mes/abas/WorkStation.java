/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 7, 2018
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.db.DbContext;

import java.io.Serializable;
import java.util.List;

/**
 * Gyártási munkaállomás típusa.
 * @author szizo
 */
public interface WorkStation extends Serializable {

	/**
	 * @return Az Abas-beli gépcsoport azonosítója.
	 */
	Id getWorkCenterId();

	/**
	 * @return A munkaállomás (egyedi) sorszáma a gépcsoporton belül.
	 */
	int getNumber();

	/**
	 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa.
	 * @param abasSession Az Abas-munkamenet.
	 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt feladatok listája.
	 */
	List<Task> getUnassignedTasks(AbasDate startDateUntil, DbContext abasSession);

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A munkaállomáshoz hozzárendelt, de felfüggesztett feladatok listája.
	 */
	List<Task> getSuspendedTasks(DbContext abasSession);

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A munkaállomáshoz hozzárendelt és végrehajtható feladatok listája.
	 */
	List<Task> getExecutableTasks(DbContext abasSession);

	/**
	 * @param abasSession Az Abas-munkamenet.
	 * @return A munkaállomás következő végrehajtható feladata (null, ha a munkaállomás feladatlistája üres).
	 */
	Task getNextExecutableTask(DbContext abasSession);

}
