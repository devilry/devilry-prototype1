package org.devilry.core;

/** Thrown to indicate that a operation or access to a requested resource is not allowed. */
public class UnauthorizedException extends DevilryException {
	private static final long serialVersionUID = -7541215486776843376L;

	public UnauthorizedException(String message) {
		super(message);
	}
}

