package org.devilry.core.authorize;


import javax.ejb.EJB;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.NodeLocal;

public class AuthorizeNode extends AuthorizeBaseNode {
	@EJB
	private NodeLocal node;

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


	
	private static final String parentAdminRightsErrmsg =
			"Access to method %s requires Admin rights on the parent-node";

	
	protected void auth(InvocationContext invocationCtx,
			String methodName, String fullMethodName, Object[] parameters)
			throws InvalidUsageException, UnauthorizedException,
			NoSuchObjectException {

		// No authorization required?
		if (noAuthRequiredMethods.contains(methodName)
				|| baseNodeNoAuthRequiredMethods.contains(methodName)) {
			log.debug("No authorization required for method: {}",
					fullMethodName);
		}

		// Requires parent node admin?
		else if(parentNodeAdminMethods.contains(methodName)
				|| baseNodeParentAdminMethods.contains(methodName)) {
			parentNodeAdminRequired(fullMethodName, parameters);
		}
		
		// create() allowed?
		else if(methodName.equals("create")) {
			if(parameters.length == 3) {
				authCreate(fullMethodName, parameters);
			} else {
				throw new UnauthorizedException(String.format(
					"The %s method with no parentId is only accessable by " +
					"SuperAdmin.", fullMethodName));
			}
		}

		else {
			unknown(fullMethodName);
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
						parentAdminRightsErrmsg, fullMethodName));
			} else {
			} 
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
				"Access to method %s requires Admin rights on the " +
				"parent-node, and the given node, %d, does not have a " +
				"parent-node.", fullMethodName, nodeId));
		}
	}
	
	/** Make sure only an Admin on the parent-node can create a node. */ 
	private void authCreate(String fullMethodName, Object[] parameters)
			throws UnauthorizedException {
		long parentId = (Long) parameters[2];
		
		try {
			if(!node.isNodeAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						parentAdminRightsErrmsg, fullMethodName));				
			}
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
				"Access to method %s requires Admin rights on the " +
				"parent-node, and the given parent-node, %d, does not exists.",
				fullMethodName, parentId));
		}
	}
}