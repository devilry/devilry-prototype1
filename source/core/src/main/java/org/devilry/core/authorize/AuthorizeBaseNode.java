package org.devilry.core.authorize;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;

import org.devilry.core.daointerfaces.UserLocal;

public abstract class AuthorizeBaseNode {
	/**
	 * Methods in BaseNodeInterface where the authorized user must be Admin on
	 * the <em>parent-node</em> of the node given as first argument.
	 */
	protected static final MethodNames baseNodeParentAdminMethods =
			new MethodNames("setName", "setDisplayName");

	/** Methods in BaseNodeInterface where the authorized user must be Admin on
	 * the node given as first argument. */
	protected static final MethodNames baseNodeAdminMethods =
			new MethodNames();

	/** Methods in BaseNodeInterface which do not require any authorization. */
	protected static final MethodNames baseNodeNoAuthRequiredMethods =
			new MethodNames("exists", "getPath", "getName", "getDisplayName",
					"getChildcourses", "getChildnodes");

	@Resource
	protected SessionContext sessionCtx;

	@EJB
	protected UserLocal userBean;

}
