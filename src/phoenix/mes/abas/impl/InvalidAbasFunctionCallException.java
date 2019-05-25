/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Dec 13, 2018
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.AbasFunctionException;

/**
 * Kivétel-osztály érvénytelen Abas-funkcióhívások jelzéséhez.
 * @author szizo
 */
public class InvalidAbasFunctionCallException extends AbasFunctionException {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 3685333507606133732L;

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 */
	public InvalidAbasFunctionCallException(int errorCode) {
		this(errorCode, null);
	}

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 * @param message A hibaüzenet szövege.
	 */
	public InvalidAbasFunctionCallException(int errorCode, String message) {
		this(errorCode, message, null);
	}

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 * @param message A hibaüzenet szövege.
	 * @param cause A dobható (hiba/kivétel), ami a hiba fellépésekor keletkezett.
	 */
	public InvalidAbasFunctionCallException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
