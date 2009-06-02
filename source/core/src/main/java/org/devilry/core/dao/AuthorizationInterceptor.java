package org.devilry.core.dao;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class AuthorizationInterceptor {
	@Resource
	SessionContext ctx;

	@AroundInvoke
	public Object authenticate(InvocationContext ic) throws Exception {
		System.out.println("username=" + ctx.getCallerPrincipal().getName());
		return ic.proceed();
	}
}
