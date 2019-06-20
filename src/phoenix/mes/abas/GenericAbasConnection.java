/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Aug 21, 2018
 */

package phoenix.mes.abas;

/**
 * Abas-kapcsolatot reprezentáló adattípus.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public interface GenericAbasConnection<C> {

	/**
	 * @return Az Abas-kapcsolat objektuma.
	 */
	C getConnectionObject();

	/**
	 * @return Az Abas-kapcsolat aktuális kezelőnyelvének kódja.
	 */
	String getOperatingLanguageCode();

	/**
	 * @return A bejelentkezett felhasználó neve az aktuális kezelőnyelvnek megfelelő formában.
	 */
	String getUserDisplayName();

	/**
	 * Az Abas-kapcsolat lezárása.
	 */
	void close();

}
