package org.devilry.core.authorize;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.UserLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AuthorizeBaseNode {
	/** Methods in BaseNodeInterface which do not require any authorization. */
	protected static final MethodNames baseNodeNoAuthRequiredMethods =
		new MethodNames("getName", "getDisplayName", "exists", "getPath");

	/** Log. */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Methods in BaseNodeInterface where the authorized user must be Admin on
	 * the <em>parent-node</em> of the node given as first argument.
	 */
	protected static final MethodNames baseNodeParentAdminMethods =
			new MethodNames("setName", "setDisplayName");



	/** Session context is used to identify the authenticated user. */
	@Resource
	protected SessionContext sessionCtx;

	/** Used to get information about the authenticated user. */
	@EJB
	protected UserLocal user;
	
	
	@AroundInvoke
	public Object authorize(InvocationContext invocationCtx) throws Exception {
		long userId = user.getAuthenticatedUser();
		if (user.isSuperAdmin(userId)) {
			log.debug("SuperAdmin {} granted access to: {}",
					userId, invocationCtx.getMethod().getName());
		} else {
			auth(invocationCtx);
		}

		return invocationCtx.proceed();
	}

	protected abstract void auth(InvocationContext invocationCtx)
		throws Exception;
}
