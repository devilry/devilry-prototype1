package org.devilry.core.authorize;

import javax.ejb.EJB;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeLocal;

public class AuthorizeAssignmentNode extends AuthorizeBaseNode {
	/*
	 		 
	 authBaseNode:
	 - getPath
	 - exists
	 
	 baseNodeParentAdminMethods:
	 - remove
	 	 
	 noauth:
	 - getAssignmentNodeId
	 - getDeadline
	 - getParentPeriod
	 - getAssignmentNode
	 - getAssignmentsWhereIsAdmin
	 - getIdFromPath
	 	 
	 assignmentAdmin:
	 - isAssignmentAdmin
	 	 
	 periodAdmin:
	 - create
	 - removeAssignmentAdmin
	 - getAssignmentAdmins
	 - addAssignmentAdmin
	 - create
	 - setDeadline
	 - getDeliveries
	 
	 */
		
	/** Methods in CourseNodeCommon which do not require any authorization. */
	private static final MethodNames noAuthRequiredMethods = new MethodNames(
			"getDeadline",
			"getAssignmentNodeId",
			"getParentPeriod",
			"getAssignmentNode",
			"getAssignmentsWhereIsAdmin",
			"getIdFromPath",
			"getDeliveries"
	);

	/**
	 * Methods in CourseNodeCommon where the authorized user must be Admin on
	 * the <em>parent-node</em> of the course-node given as first argument to
	 * be allowed access.
	 */
	private static final MethodNames parentPeriodAdminMethods = new MethodNames(
			"removeAssignmentAdmin",
			"getAssignmentAdmins",
			"addAssignmentAdmin",
			"setDeadline"
	);

	private static final MethodNames assignmentAdminMethods = new MethodNames(
			"isAssignmentAdmin"
	);

	@EJB
	private AssignmentNodeLocal assignment;
	
	@EJB
	private PeriodNodeLocal period;
	
	
	private static final String parentPeriodAdminRightsErrmsg =
			"Access to method %s requires Admin rights on the course-node";

	private static final String assignmentAdminRightsErrmsg =
		"Access to method %s requires Admin rights on the period-node";
	
	private static final String noParentPeriodErrmsg =
			"Access to method %s requires Admin rights on the parent-course, " +
			"and the given period, %d, does not have a parent-course.";
	
	
	
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

		// Requires parent period admin?
		else if (parentPeriodAdminMethods.contains(methodName)
				|| baseNodeParentAdminMethods.contains(methodName)) {
			parentPeriodAdminRequired(fullMethodName, parameters);
		}
		// Requires assignment admin
		else if (assignmentAdminMethods.contains(methodName)) {
			assignmentAdminRequired(fullMethodName, parameters);
		}
		
		// create() allowed?
		else if (methodName.equals("create")) {
			authCreate(fullMethodName, parameters);
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
	private void parentPeriodAdminRequired(String fullMethodName,
			Object[] parameters) throws UnauthorizedException {
		long nodeId = (Long) parameters[0];

		try {
			long parentId = assignment.getParentPeriod(nodeId);
			if (!period.isPeriodAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						parentPeriodAdminRightsErrmsg, fullMethodName));
			} else {
			} 
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					noParentPeriodErrmsg, fullMethodName, nodeId));
		}
	}
	
	/** Check that the authorized user is admin on the parent-node
	 * of the node given as first argument, and deny access if this
	 * is not the case. */
	private void assignmentAdminRequired(String fullMethodName,
			Object[] parameters) throws UnauthorizedException {
		long assignmentNodeId = (Long) parameters[0];

		try {
									
			if (!assignment.isAssignmentAdmin(assignmentNodeId)) {
				throw new UnauthorizedException(String.format(
						assignmentAdminRightsErrmsg, fullMethodName));
			} 
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					noParentPeriodErrmsg, fullMethodName, assignmentNodeId));
		}
	}
	
	/** Make sure only an Admin on the parent-node can create an assignment. */ 
	private void authCreate(String fullMethodName, Object[] parameters)
			throws UnauthorizedException {
		long parentId = (Long) parameters[4];
		
		try {
			if(!period.isPeriodAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						parentPeriodAdminRightsErrmsg, fullMethodName));				
			}
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					noParentPeriodErrmsg, fullMethodName, parentId));
		}
	}
}
