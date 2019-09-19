/*
 * Domain típusok könyvtára.
 *
 * Created on Sep 10, 2019
 */

package phoenix.mes.abas.impl;

import java.net.URI;

import phoenix.mes.abas.GenericTask.Document;

/**
 * Dokumentumot leíró osztály.
 * @author szizo
 */
public class DocumentImpl implements Document {

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 6143345873996456044L;

	/**
	 * A dokumentum megnevezése.
	 */
	protected final String description;

	/**
	 * A dokumentum URI-ja (null, ha nincs megadva vagy a dokumentum nem érhető el).
	 */
	protected final URI uri;

	/**
	 * Konstruktor.
	 * @param description A dokumentum megnevezése.
	 * @param uri A dokumentum URI-ja (null, ha nincs megadva vagy a dokumentum nem érhető el).
	 */
	public DocumentImpl(String description, URI uri) {
		this.description = description;
		this.uri = uri;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Document#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.GenericTask.Document#getURI()
	 */
	@Override
	public URI getURI() {
		return uri;
	}

}
