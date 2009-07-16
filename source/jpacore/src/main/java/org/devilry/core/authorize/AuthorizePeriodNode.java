package org.devilry.core.authorize;

import javax.ejb.EJB;
import javax.interceptor.InvocationContext;

import org.devilry.core.InvalidUsageException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeLocal;

public class AuthorizePeriodNode extends AuthorizeBaseNode {
	/*
	 
	 noauth - getAssignments
	 noauth - getParentCourse
	 noauth - getPeriodsWhereIsStudent
	 noauth - getPeriodsWhereIsExaminer
	 noauth - getPeriodsWhereIsAdmin
	 noauth - getStartDate
	 noauth - getEndDate
	 noauth - getPeriodNodeId
	 noauth - getIdFromPath
	 
	 periodAdmin - getStudents
	 periodAdmin - isStudent
	 periodAdmin - addStudent
	 periodAdmin - removeStudent
	 periodAdmin - getExaminers
	 periodAdmin - isExaminer
	 periodAdmin - addExaminer
	 periodAdmin - removeExaminer
	 periodAdmin - isPeriodAdmin
	 
	 courseAdmin - addPeriodAdmin
	 courseAdmin - removePeriodAdmin
	 courseAdmin - getPeriodAdmins
	 courseAdmin - setStartDate
	 courseAdmin - setEndDate
	 
	 courseAdmin - create
	 
	 authBaseNode - exists
	 authBaseNode - getPath
	 
	 baseNodeParentAdminMethods - remove	 
	 
	 */
		
	/** Methods in CourseNodeCommon which do not require any authorization. */
	private static final MethodNames noAuthRequiredMethods = new MethodNames(
			"getPeriodsWhereIsStudent", 
			"getPeriodsWhereIsExaminer", 
			"getPeriodsWhereIsAdmin", 
			"getParentCourse",
			"getAssignments",
			"getStartDate",
			"getEndDate",
			"getPeriodNodeId",
			"getIdFromPath"
	);

	/**
	 * Methods in CourseNodeCommon where the authorized user must be Admin on
	 * the <em>parent-node</em> of the course-node given as first argument to
	 * be allowed access.
	 */
	private static final MethodNames parentCourseAdminMethods = new MethodNames(
			"addPeriodAdmin",
			"removePeriodAdmin",
			"getPeriodAdmins",
			"setStartDate",
			"setEndDate"
	);

	private static final MethodNames periodAdminMethods = new MethodNames(
			"addStudent",
			"removeStudent", 
			"getStudents",
			"isStudent",
			"getExaminers",
			"addExaminer",
			"isExaminer",
			"removeExaminer", 
			"addCourseAdmin", 
			"removeCourseAdmin", 
			"getCourseAdmins",	
			"isPeriodAdmin"
	);

	@EJB
	private PeriodNodeLocal period;
	
	@EJB
	private CourseNodeLocal course;
	
	private static final String parentCourseAdminRightsErrmsg =
			"Access to method %s requires Admin rights on the course-node";

	private static final String periodAdminRightsErrmsg =
		"Access to method %s requires Admin rights on the period-node";
	
	private static final String noParentCourseErrmsg =
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

		// Requires parent course admin?
		else if (parentCourseAdminMethods.contains(methodName)
				|| baseNodeParentAdminMethods.contains(methodName)) {
			parentCourseAdminRequired(fullMethodName, parameters);
		}
		// Requires period admin
		else if (periodAdminMethods.contains(methodName)) {
			periodAdminRequired(fullMethodName, parameters);
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
	private void parentCourseAdminRequired(String fullMethodName,
			Object[] parameters) throws UnauthorizedException {
		long nodeId = (Long) parameters[0];

		try {
			long parentId = period.getParentCourse(nodeId);
			if (!course.isCourseAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						parentCourseAdminRightsErrmsg, fullMethodName));
			} else {
			} 
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					noParentCourseErrmsg, fullMethodName, nodeId));
		}
	}
	
	/** Check that the authorized user is admin on the parent-node
	 * of the node given as first argument, and deny access if this
	 * is not the case. */
	private void periodAdminRequired(String fullMethodName,
			Object[] parameters) throws UnauthorizedException {
		long periodNodeId = (Long) parameters[0];

		try {
									
			if (!period.isPeriodAdmin(periodNodeId)) {
				throw new UnauthorizedException(String.format(
						periodAdminRightsErrmsg, fullMethodName));
			} 
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					noParentCourseErrmsg, fullMethodName, periodNodeId));
		}
	}
	
	/** Make sure only an Admin on the parent-node can create a period. */ 
	private void authCreate(String fullMethodName, Object[] parameters)
			throws UnauthorizedException {
		long parentId = (Long) parameters[4];
		
		try {
			if(!course.isCourseAdmin(parentId)) {
				throw new UnauthorizedException(String.format(
						parentCourseAdminRightsErrmsg, fullMethodName));				
			}
		} catch (NoSuchObjectException e) {
			throw new UnauthorizedException(String.format(
					noParentCourseErrmsg, fullMethodName, parentId));
		}
	}
}
