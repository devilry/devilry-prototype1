package org.devilry.core;

/** Thrown when creating a new node, and a node with the same path already
 * exists. */
public class PathExistsException extends DevilryException {

	public PathExistsException(String message) {
		super(message);
	}

}
