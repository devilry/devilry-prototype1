package org.devilry.core.authorize;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;

import javax.ejb.EJB;
import javax.interceptor.InvocationContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

	private static final MethodNames studentMethods = new MethodNames(
			"isStudent");

	private static final MethodNames examinerMethods = new MethodNames(
			"isExaminer");

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
		} else if (isStudentCalled(methodName, parameters, invocationCtx)) {
			return;
		} else if (isExaminerCalled(methodName, parameters, invocationCtx)) {
			return;
		} else if (anyRequired(methodName, parameters, invocationCtx)) {
			return;
		} else if (assignmnentAdminRequired(methodName, parameters)) {
			return;
		} else {
			unknown(fullMethodName);
		}
	}


	private boolean noAuth(InvocationContext invocationCtx) {
		return invocationCtx.getContextData().containsKey("skip auth");
	}


	/**
	 * Put the "skip auth" key into context data, and call the given method
	 * on the target class.
	 * Because of the noAuth() test, authentication will be skipped, and
	 * the given method is run even if the authenticated user does not have
	 * access.
	 */
	private boolean isSomethingNoAuth(InvocationContext invocationCtx,
									  Object[] parameters, String methodName)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Put "skip auth" into getContextData for noAuth()
		invocationCtx.getContextData().put("skip auth", true);

		// Get the isStudent/isExaminer method from the target class using
		// reflection. Note that we cannot use invocationCtx.getMethod() because
		// this method is not only used when isStudent()/isExaminer() is called
		// directly.
		Class[] paramTypes = new Class[]{Long.TYPE};
		Method isSomethingMethod =
				invocationCtx.getTarget().getClass()
						.getMethod(methodName, paramTypes);
		return (Boolean) isSomethingMethod
				.invoke(invocationCtx.getTarget(), parameters);
	}

	private boolean isStudentNoAuth(InvocationContext invocationCtx,
									Object[] parameters)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return isSomethingNoAuth(invocationCtx, parameters, "isStudent");
	}

	private boolean isExaminerNoAuth(InvocationContext invocationCtx,
									 Object[] parameters)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return isSomethingNoAuth(invocationCtx, parameters, "isExaminer");
	}


	private boolean isExaminerCalled(String methodName, Object[] parameters,
									 InvocationContext invocationCtx)
			throws UnauthorizedException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		if (!methodName.equals("isExaminer")) {
			return false;
		}
		if (noAuth(invocationCtx)) {
			return true;
		}

		if (isExaminerNoAuth(invocationCtx, parameters)) {
			return true;
		} else {
			throw new UnauthorizedException("Access to method isExaminer " +
					"requires the authenticated user to be Examiner on the " +
					"delivery");
		}
	}


	private boolean isStudentCalled(String methodName, Object[] parameters,
									InvocationContext invocationCtx)
			throws UnauthorizedException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		if (!methodName.equals("isStudent")) {
			return false;
		}
		if (noAuth(invocationCtx)) {
			return true;
		}

		if (isStudentNoAuth(invocationCtx, parameters)) {
			return true;
		} else {
			throw new UnauthorizedException("Access to method isStudent " +
					"requires the authenticated user to be Student on the " +
					"delivery");
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

	private boolean anyRequired(String methodName, Object[] parameters,
								InvocationContext invocationCtx)
			throws UnauthorizedException, NoSuchObjectException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (!anyMethods.contains(methodName)) {
			return false;
		}

		long deliveryId = (Long) parameters[0];
		if (isStudentNoAuth(invocationCtx, parameters) ||
				isExaminerNoAuth(invocationCtx, parameters)) {
			return true;
		} else {
			long assignmentId = delivery.getAssignment(deliveryId);
			if (assignment.isAssignmentAdmin(assignmentId)) {
				return true;
			}
		}
		return false;
	}
}
