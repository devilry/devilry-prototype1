package org.devilry.core.authorize;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
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
	 * Methods in NodeCommon where the authorized user must be Admin on the
	 * <em>parent-node</em> of the node given as first argument to be allowed
	 * access.
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
		long userId = userBean.getAuthenticatedUser();
		if(!userBean.isSuperAdmin(userId)) {
			auth(invocationCtx);
		}

		return invocationCtx.proceed();
	}
	
	
	private void auth(InvocationContext invocationCtx)
			throws InvalidUsageException, UnauthorizedException,
			NoSuchObjectException {
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

		// Requires admin on given node?
		if (nodeAdminMethods.contains(methodName)
				|| baseNodeAdminMethods.contains(methodName)) {
			long nodeId = (Long) parameters[0];
			if (!nodeBean.isNodeAdmin(nodeId)) {
				throw new UnauthorizedException(String.format(
						"Access to method '%s' requires Admin rights on " +
						"node %d.", fullMethodName, nodeId));
			} else if(log.getLevel() == Level.FINEST) {
				log.finest(String.format("Access to method %s allowed",
						methodName));
			}
		}

		// Requires parent node admin?
		else if(parentNodeAdminMethods.contains(methodName)
				|| baseNodeParentAdminMethods.contains(methodName)) {
			long nodeId = (Long) parameters[0];
			if (!nodeBean.isNodeAdmin(nodeId)) {
				throw new UnauthorizedException(String.format(
						"Not authorized to access method : %s",
						fullMethodName));
			} else if(log.getLevel() == Level.FINEST) {
				log.finest(String.format("Access to method %s allowed", methodName));
			} 
		}
		
		// If the method has not yet been handled, and it is not among
		// the methods which requires no authorization, deny authorization.
		else if (!noAuthRequiredMethods.contains(methodName)) {
			log.warning(String.format("Authorization of method '%s' is not " +
					"defined, so access is denied.", methodName));
		}
	}
	
}