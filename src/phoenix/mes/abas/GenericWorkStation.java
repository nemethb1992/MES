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
 * Gyártási munkaállomás adattípusa.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public interface GenericWorkStation<C> extends Serializable {

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
	List<? extends GenericTask<C>> getUnassignedTasks(AbasDate startDateUntil, GenericAbasConnection<C> abasConnection);

	/**
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return A munkaállomásra beütemezett és végrehajtható gyártási feladatok listája.
	 */
	List<? extends GenericTask<C>> getScheduledTasks(GenericAbasConnection<C> abasConnection);

	/**
	 * A munkaállomásra elsőként beütemezett és végrehajtható gyártási feladat elindítása.
	 * @param abasConnection Az Abas-kapcsolat.
	 * @return Az elindított gyártási feladat (null, ha a munkaállomás feladatlistája üres).
	 */
	GenericTask<C> startFirstScheduledTask(GenericAbasConnection<C> abasConnection);

}
