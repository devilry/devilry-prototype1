package org.devilry.core.authorize;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

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
	public Object authorize(InvocationContext invocationCtx)
			throws Exception {

		long userId = user.getAuthenticatedUser();
		if (user.isSuperAdmin(userId)) {
			log.debug("SuperAdmin {} granted access to: {}",
					userId, invocationCtx.getMethod().getName());
		} else {
			Method targetMethod = invocationCtx.getMethod();
			String methodName = targetMethod.getName();
			String fullMethodName = invocationCtx.getClass().getName() + "."
					+ methodName;
			Object[] parameters = invocationCtx.getParameters();
			auth(invocationCtx, methodName, fullMethodName, parameters);
			log.debug("Access to method {} granted", fullMethodName);
		}
		

		return invocationCtx.proceed();
	}

	protected abstract void auth(InvocationContext invocationCtx,
			String methodName, String fullMethodName, Object[] parameters)
			throws Exception;
}
