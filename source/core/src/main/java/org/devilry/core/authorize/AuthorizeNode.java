package org.devilry.core.authorize;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.devilry.core.AuthorizationException;
import org.devilry.core.InvalidUsageException;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.daointerfaces.NodeLocal;


/** 
 * 
 *
 */
public class AuthorizeNode extends AuthorizeBaseNode {
	private final Logger log = Logger.getLogger(getClass().getName());

	/**
	 * Methods in NodeCommon where the authorized user must be admin on the node
	 * given as first argument.
	 */
	protected static final MethodNames nodeAdminMethods = new MethodNames(
			"getChildnodes", "getChildcourses");

	/**
	 * Methods in NodeCommon where the authorized user must be admin on the
	 * parent-node to be allowed access.
	 */
	protected static final MethodNames parentNodeAdminMethods = new MethodNames(
			"addNodeAdmin", "removeNodeAdmin", "getNodeAdmins");

	/** Methods in NodeCommon which do not require any authorization. */
	protected static final MethodNames noAuthRequiredMethods = new MethodNames(
			"getNodesWhereIsAdmin", "isNodeAdmin", "getParentNode");

	@EJB
	private NodeLocal nodeBean;

	@AroundInvoke
	public Object authorize(InvocationContext invocationCtx) throws Exception {
		Class<?> targetClass = invocationCtx.getTarget().getClass();
		if (targetClass != NodeImpl.class) {
			throw new InvalidUsageException("The " + getClass().getName()
					+ " interceptor can only be used on "
					+ NodeImpl.class.getName());
		}

		Method targetMethod = invocationCtx.getMethod();
		String fullMethodName = targetClass.getName() + "."
				+ targetMethod.getName();
		Object[] parameters = invocationCtx.getParameters();

		String methodName = targetMethod.getName();
		long userId = userBean.getAuthenticatedUser();
//		if (baseNodeAdminMethods.contains(methodName)) {
//			long nodeId = (Long) parameters[0];
//			if (!nodeBean.isNodeAdmin(nodeId)) {
//				throw new AuthorizationException(String.format(
//						"Not authorized to access method : %s", fullMethodName));
//			}
//		}
		if(nodeAdminMethods.contains(methodName)) {
			long nodeId = (Long) parameters[0];
			if (!nodeBean.isNodeAdmin(nodeId)) {
				throw new AuthorizationException(String.format(
						"Not authorized to access method : %s", fullMethodName));
			}
		}

		return invocationCtx.proceed();
	}
}