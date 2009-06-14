package org.devilry.core;

/** Thrown when creating a node with invalid name. */
public class InvalidNameException extends DevilryException {

	private static final long serialVersionUID = -5914385135371864991L;

	/**
	 * @param message
	 *            Error message.
	 */
	public InvalidNameException(final String message) {
		super(message);
	}

}
