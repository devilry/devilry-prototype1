package org.devilry.core;

/** Thrown when requesting the parent of a toplevel node. */
public class NoParentException extends DevilryException {

	public NoParentException(String message) {
		super(message);
	}

}
