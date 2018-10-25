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
	 * @return A vizsgált kezdődátum-intervallumba eső, a gépcsoportra betervezett, de konkrét munkaállomáshoz hozzá nem rendelt gyártási feladatok listája.
	 */
	List<Task> getUnassignedTasks(AbasDate startDateUntil, AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A gépcsoportra betervezett, de felfüggesztett gyártási feladatok listája.
	 */
	List<Task> getSuspendedTasks(AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A munkaállomásra betervezett és végrehajtható gyártási feladatok listája.
	 */
	List<Task> getScheduledTasks(AbasConnection<?> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A munkaállomás első végrehajtható gyártási feladata (null, ha a munkaállomás feladatlistája üres).
	 */
	Task getFirstScheduledTask(AbasConnection<?> abasConnection);

}
