package org.devilry.core;

/** Thrown to indicate invalid usage of a resource or operation. */
public class InvalidUsageException extends DevilryException {
	private static final long serialVersionUID = 705309294702885150L;

	/**
	 * @param message
	 *            Error message.
	 */
	public InvalidUsageException(final String message) {
		super(message);
	}
}
