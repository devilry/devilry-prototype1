package org.devilry.core.authorize;

import javax.ejb.EJB;
import javax.interceptor.InvocationContext;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;

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
		"addNodeAdmin", "removeNodeAdmin", "getNodeAdmins");

	@Override
	protected void auth(InvocationContext invocationCtx, String methodName,
		String fullMethodName, Object[] parameters) throws Exception {

//		if (noAuthRequired(methodName, noAuthRequiredMethods)) {
//			return;
//		} else if (authParentAdm(methodName, parameters)) {
//			return;
//		} else {
//			unknown(fullMethodName);
//		}
	}

	private boolean authParentAdm(String methodName, Object[] parameters)
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
