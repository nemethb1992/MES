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
	 * @param abasConnection Az Abas-kapcsolat.
	 * @param abasConnectionType Az Abas-kapcsolat (elvárt) osztálya.
	 * @return Az Abas-kapcsolat objektuma.
	 * @throws IllegalArgumentException Ha az Abas-kapcsolat nem a megadott típusú.
	 */
	public static <C> C getConnectionObject(AbasConnection<?> abasConnection, Class<C> abasConnectionType) {
		try {
			final C connectionObject = abasConnectionType.cast(abasConnection.getConnectionObject());
			if (null == connectionObject) {
				throw new NullPointerException("Az Abas-kapcsolat objektuma nem lehet null!");
			}
			return connectionObject;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Nem megfelelő Abas-kapcsolattípus: " + abasConnection.getClass().getName(), e);
		}
	}

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
