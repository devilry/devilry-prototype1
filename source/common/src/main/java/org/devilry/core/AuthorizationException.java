package org.devilry.core;


/** Thrown when there is an authorization problem. */
public class AuthorizationException extends DevilryException {
	public AuthorizationException(String message) {
		super(message);
	}
}