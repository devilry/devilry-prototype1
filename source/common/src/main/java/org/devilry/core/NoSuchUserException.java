package org.devilry.core;

/** Thrown when requesting a user that does not exist. */
public class NoSuchUserException extends DevilryException {
	private static final long serialVersionUID = 5540968817126299489L;

	/**
	 * @param message
	 *            Error message.
	 */
	public NoSuchUserException(final String message) {
		super(message);
	}

}
