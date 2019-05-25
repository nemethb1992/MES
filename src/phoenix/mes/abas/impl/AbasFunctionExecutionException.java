/*
 * Osztálykönyvtár az Abas-interfészhez.
 *
 * Created on Dec 13, 2018
 */

package phoenix.mes.abas.impl;

import phoenix.mes.abas.AbasFunctionException;

/**
 * Kivétel-osztály Abas-funkcióhívás végrehajtásakor történt (futásidejű) hibák jelzéséhez.
 * @author szizo
 */
public class AbasFunctionExecutionException extends AbasFunctionException {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = -8643791655590853643L;

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 */
	public AbasFunctionExecutionException(int errorCode) {
		this(errorCode, null);
	}

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 * @param message A hibaüzenet szövege.
	 */
	public AbasFunctionExecutionException(int errorCode, String message) {
		this(errorCode, message, null);
	}

	/**
	 * Konstruktor.
	 * @param errorCode A hiba kódja.
	 * @param message A hibaüzenet szövege.
	 * @param cause A dobható (hiba/kivétel), ami a hiba fellépésekor keletkezett.
	 */
	public AbasFunctionExecutionException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
