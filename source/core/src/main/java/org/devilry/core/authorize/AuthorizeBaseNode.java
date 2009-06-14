package org.devilry.core.authorize;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;

import org.devilry.core.daointerfaces.UserLocal;

public abstract class AuthorizeBaseNode {
	/**
	 * Methods in BaseNodeInterface where the authorized user must be admin on
	 * the node given as first argument.
	 */
	protected static final MethodNames baseNodeAdminMethods = new MethodNames(
			"setName", "getName", "setDisplayName", "getDisplayName", "exists",
			"getPath");

	
	@Resource
	protected SessionContext sessionCtx;

	@EJB
	protected UserLocal userBean;

}
