/*
 * Domain típusok könyvtára.
 *
 * Created on Aug 7, 2018
 */

package phoenix.mes.abas;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;

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
	 * @param startDateUntil A vizsgált kezdődátum-intervallum felső határa (AbasDate.INFINITY, ha nincs szükség időkorlátra).
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt feladatok listája.
	 */
	List<Task> getUnassignedTasks(AbasDate startDateUntil, AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A munkaállomáshoz hozzárendelt, de felfüggesztett feladatok listája.
	 */
	List<Task> getSuspendedTasks(AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A munkaállomáshoz hozzárendelt és végrehajtható feladatok listája.
	 */
	List<Task> getExecutableTasks(AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A munkaállomás következő végrehajtható feladata (null, ha a munkaállomás feladatlistája üres).
	 */
	Task getNextExecutableTask(AbasConnection<?> abasConnection);

	void setTaskExecutionOrder(Id taskId, Id precedingTaskId, AbasConnection<?> abasConnection);

}
