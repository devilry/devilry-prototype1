package org.devilry.core;

/** Thrown when requesting the parent of a toplevel node. */
public class NoParentException extends NoSuchObjectException {

	private static final long serialVersionUID = 169373294721365951L;

	/**
	 * @param message
	 *            Error message.
	 */
	public NoParentException(final String message) {
		super(message);
	}

}
