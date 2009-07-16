package org.devilry.core;

/** Base class for all devilry exceptions. */
public class DevilryException extends Exception {

	private static final long serialVersionUID = -8693091370343578654L;

	/**
	 * @param message
	 *            Error message.
	 */
	public DevilryException(final String message) {
		super(message);
	}
}
