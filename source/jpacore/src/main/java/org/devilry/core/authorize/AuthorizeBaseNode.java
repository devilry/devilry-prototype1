package org.devilry.core.authorize;

public abstract class AuthorizeBaseNode extends AuthorizeBase {
	/** Methods in BaseNodeInterface which do not require any authorization. */
	protected static final MethodNames baseNodeNoAuthRequiredMethods = new MethodNames(
			"getName", "getDisplayName", "exists", "getPath", "getIdFromPath");

	/**
	 * Methods in BaseNodeInterface where the authorized user must be Admin on
	 * the <em>parent-node</em> of the node given as first argument.
	 */
	protected static final MethodNames baseNodeParentAdminMethods = new MethodNames(
			"setName", "setDisplayName", "remove");

}
