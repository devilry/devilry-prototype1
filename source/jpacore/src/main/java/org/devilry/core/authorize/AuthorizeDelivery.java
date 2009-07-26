package org.devilry.core.authorize;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;

import javax.ejb.EJB;
import javax.interceptor.InvocationContext;

public class AuthorizeDelivery extends AuthorizeBase {

	@EJB
	private AssignmentNodeLocal assignment;

	@EJB
	private DeliveryLocal delivery;

	private static final MethodNames noAuthRequiredMethods = new MethodNames(
			"exists", "getAssignment", "getDeliveriesWhereIsExaminer",
			"getDeliveriesWhereIsStudent");

	/**
	 * Methods in DeliveryCommon where the authorized user must be Admin on the
	 * <em>assignment</em> where the given delivery belongs to be allowed
	 * access.
	 */
	private static final MethodNames assignmentAdminMethods = new MethodNames(
			"addExaminer", "addStudent", "create", "getExaminers",
			"getStudents", "isStudent", "isExaminer", "remove",
			"removeExaminer", "removeStudent");

	/**
	 * Methods that only students can access.
	 */
	private static final MethodNames studentMethods = new MethodNames(
			"create");

	/**
	 * Methods that any of assignmentAdmin, student or examiner might access
	 */
	private static final MethodNames anyMethods = new MethodNames(
			"getDeliveryCandidates", "getLastDeliveryCandidate",
			"getLastValidDeliveryCandidate");

	@Override
	protected void auth(InvocationContext invocationCtx, String methodName,
						String fullMethodName, Object[] parameters)
			throws Exception {

		if (noAuthRequired(methodName, noAuthRequiredMethods)) {
			return;
		} else if (assignmnentAdminRequired(methodName, parameters)) {
			return;
		} else {
			unknown(fullMethodName);
		}
	}

	private boolean assignmnentAdminRequired(String methodName,
											 Object[] parameters)
			throws UnauthorizedException, NoSuchObjectException {

		if (!assignmentAdminMethods.contains(methodName)) {
			return false;
		}
		long id = (Long) parameters[0];
		long assignmentId;
		if (methodName.equals("create")) {
			assignmentId = id;
		} else {
			assignmentId = delivery.getAssignment(id);
		}

		if (!assignment.isAssignmentAdmin(assignmentId)) {
			throw new UnauthorizedException(String.format(
					"Access to method %s requires Admin rights on the " +
							"assignment.", methodName));
		}
		return true;
	}
}
