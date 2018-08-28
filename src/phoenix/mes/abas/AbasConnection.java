/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Aug 21, 2018
 */

package phoenix.mes.abas;

/**
 * Abas-kapcsolatot reprezentáló típus.
 * @param <C> Az Abas-kapcsolat típusa.
 * @author szizo
 */
public interface AbasConnection<C> {

	/**
	 * Az Abas-adatbázisszerver neve.
	 */
	String SERVER_NAME = "abasdb.pmhu.local";

	/**
	 * Az éles Abas-mandant elérési útja.
	 */
	String PRODUCTION_CLIENT_PATH = "/Abas/pmk";

	/**
	 * Az Abas-tesztmandant elérési útja.
	 */
	String TEST_CLIENT_PATH = "/Abas/dpmk";

	/**
	 * @return Az Abas-kapcsolat objektuma.
	 */
	C getConnectionObject();

	/**
	 * Az Abas-kapcsolat lezárása.
	 */
	void close();

}
