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

public abstract class AuthorizeBase {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	protected SessionContext sessionCtx;

	@EJB
	protected UserLocal user;

	@AroundInvoke
	public Object authorize(InvocationContext invocationCtx) throws Exception {

		long userId = user.getAuthenticatedUser();
		Method targetMethod = invocationCtx.getMethod();
		String methodName = targetMethod.getName();
		String fullMethodName = invocationCtx.getClass().getName() + "." + methodName;

		if (user.isSuperAdmin(userId)) {
			log.debug("SuperAdmin {} granted access to: {}", userId, fullMethodName);
		} else {
			Object[] parameters = invocationCtx.getParameters();
			auth(invocationCtx, methodName, fullMethodName, parameters);

			// If no exception is thrown by the auth method, the user
			// is authenticated.
			log.debug("User {} granted access to: {}", userId,
				fullMethodName);
		}

		return invocationCtx.proceed();
	}

	protected abstract void auth(InvocationContext invocationCtx,
		String methodName, String fullMethodName, Object[] parameters)
		throws Exception;
}
