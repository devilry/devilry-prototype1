package org.devilry.core;

/** Thrown when creating a new node, and a node with the same path already
 * exists. */
public class PathExistsException extends DevilryException {

	private static final long serialVersionUID = -6907681527529603034L;

	/**
	 * @param message
	 *            Error message.
	 */
	public PathExistsException(final String message) {
		super(message);
	}

}
