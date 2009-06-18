package org.devilry.core.authorize;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.daointerfaces.NodeLocal;

public class AuthorizeNode extends AuthorizeBaseNode {

	/** Methods in NodeCommon which do not require any authorization. */
	private static final MethodNames noAuthRequiredMethods = new MethodNames(
			"getNodesWhereIsAdmin", "isNodeAdmin", "getParentNode",
			"getChildNodes", "getChildCourses");

	/**
	 * Methods in NodeCommon where the authorized user must be Admin on the
	 * <em>parent-node</em> of the node given as first argument to be allowed
	 * access.
	 */
	private static final MethodNames parentNodeAdminMethods = new MethodNames(
			"addNodeAdmin", "removeNodeAdmin", "getNodeAdmins");


	@EJB
	private NodeLocal node;

	protected void auth(InvocationContext invocationCtx)
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

		// No authorization required?
		if (noAuthRequiredMethods.contains(methodName)
				|| baseNodeNoAuthRequiredMethods.contains(methodName)) {
			log.debug("No authorization required for method: {}", fullMethodName);
		}

		// Requires parent node admin?
		else if(parentNodeAdminMethods.contains(methodName)
				|| baseNodeParentAdminMethods.contains(methodName)) {
			parentNodeAdminRequired(fullMethodName, parameters);
		}
		
		// If the method has not yet been handled, and it is not among
		// the methods which requires no authorization: deny access.
		else {
			log.error("No authorization rule set for method: {}", 
					fullMethodName);
		}

	}

	/** Check that the authorized user is admin on the parent-node
	 * of the node given as first argument, and deny access if this
	 * is not the case. */
	private void parentNodeAdminRequired(String fullMethodName,
			Object[] parameters) throws UnauthorizedException {
		long nodeId = (Long) parameters[0];

		try {
			long parentId = node.getParentNode(nodeId);
			if (!node.isNodeAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						"Access to method %s requires Admin rights on the " +
						"parent-node.", fullMethodName));
			} else {
				log.debug("Access to method {} granted", fullMethodName);
			} 
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					"Access to method %s requires Admin rights on " +
					"the parent-node, and the given node, %d, does " +
					"not have a parent-node.", fullMethodName, nodeId));
		}
	}
	
	/** Make sure only an Admin on the parent-node can create a node. */ 
	private void authCreate(String fullMethodName, Object[] parameters)
			throws UnauthorizedException {
		long parentId = (Long) parameters[2];
		
		try {
			if(!node.isNodeAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						"Access to method %s requires Admin rights on the " +
						"parent-node.", fullMethodName));				
			}
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					"Access to method %s requires Admin rights on " +
					"the parent-node, and the given node, %d, does " +
					"not have a parent-node.", fullMethodName, parentId));
		}
	}
}