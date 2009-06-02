package org.devilry.core.dao;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;


public class AuthorizationInterceptor {
	@Resource
	SessionContext sessionCtx;

	protected InvocationContext invocationCtx = null;

	@AroundInvoke
	public Object authenticate(InvocationContext invocationCtx) throws Exception {
		Logger l = Logger.getLogger("saker");
		this.invocationCtx = invocationCtx;
		String userName = sessionCtx.getCallerPrincipal().getName();
		String methodName = invocationCtx.getMethod().getName();
		String className = invocationCtx.getTarget().getClass().getName();
//		l.info(String.format("User: '%s' called the '%s' method in class '%s'%n", userName, methodName, className));
		return invocationCtx.proceed();
	}
}