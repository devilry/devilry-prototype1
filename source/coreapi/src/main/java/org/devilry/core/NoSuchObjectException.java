package org.devilry.core;

/** Thrown when requesting a object that does not exist. */
public class NoSuchObjectException extends DevilryException {

	private static final long serialVersionUID = -6871752230516827802L;

	/**
	 * @param message
	 *            Error message.
	 */
	public NoSuchObjectException(final String message) {
		super(message);
	}
}
