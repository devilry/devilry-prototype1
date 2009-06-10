package org.devilry.core;


/** Thrown when requesting a object that does not exist. */
public class NoSuchObjectException extends DevilryException {
	public NoSuchObjectException(String message) {
		super(message);
	}
}
