/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Dec 13, 2018
 */

package phoenix.mes.abas;

/**
 * Kivétel-osztály Abas-funkcióhívások során fellépő hibák jelzéséhez.
 * @author szizo
 */
public class AbasFunctionException extends Exception {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 6038834042893480303L;

	/**
	 * A hiba kódja.
	 */
	protected final int errorCode;

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 */
	public AbasFunctionException(int errorCode) {
		this(errorCode, null);
	}

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 * @param message A hibaüzenet szövege.
	 */
	public AbasFunctionException(int errorCode, String message) {
		this(errorCode, message, null);
	}

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 * @param message A hibaüzenet szövege.
	 * @param cause A dobható (hiba/kivétel), ami a hiba fellépésekor keletkezett.
	 */
	public AbasFunctionException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * @return A hiba kódja.
	 */
	public int getErrorCode() {
		return errorCode;
	}

}
