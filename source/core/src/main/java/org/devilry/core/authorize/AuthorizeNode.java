package org.devilry.core.authorize;


import javax.ejb.EJB;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
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
	
	
	private static final String parentAdminRightsErrmsg =
			"Access to method %s requires Admin rights on the parent-node";

	private static final String noParentNodeErrmsg =
			"Access to method %s requires Admin rights on the parent-node, " +
			"and the given node, %d, does not have a parent-node.";
	
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
					"The %s with no parentId is only accessable by " +
					"SuperAdmin.", fullMethodName));
			}
		}

		// If the method has not yet been handled, and it is not among
		// the methods which requires no authorization: deny access.
		else {
			throw new UnauthorizedException(
					"No authorization rule set for non-SuperAdmin users " +
					"on method: " + fullMethodName);
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
					noParentNodeErrmsg, fullMethodName, nodeId));
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
					noParentNodeErrmsg, fullMethodName, parentId));
		}
	}
}