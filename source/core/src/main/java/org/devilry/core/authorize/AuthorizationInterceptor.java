package org.devilry.core.authorize;
//
//import javax.annotation.Resource;
//import javax.ejb.EJB;
//import javax.ejb.SessionContext;
//import javax.interceptor.AroundInvoke;
//import javax.interceptor.InvocationContext;
//
//import org.devilry.core.dao.NodeImpl;
//import org.devilry.core.daointerfaces.NodeCommon;
//import org.devilry.core.daointerfaces.NodeLocal;
//import org.devilry.core.daointerfaces.UserCommon;
//import org.devilry.core.daointerfaces.UserLocal;
//
//import java.lang.reflect.Method;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.logging.Logger;
//
//class Allowed extends HashSet<String> {
//	public Allowed(String... methodNames) {
//		for (String n : methodNames)
//			add(n);
//	}
//}
//
//class MethodNames extends HashSet<String> {
//	public MethodNames(Class<?> cls) {
//		for (Method m : cls.getDeclaredMethods())
//			add(m.getName());
//	}
//
//	public String toString() {
//		String s = "";
//		String sep = "";
//		for (String name : this) {
//			s += sep + name;
//			sep = ",";
//		}
//		return s;
//	}
//}
//
//public class AuthorizationInterceptor {
//
//	public static final MethodNames nodeCommonMethods = new MethodNames(
//			NodeCommon.class);
//
//	@Resource
//	SessionContext sessionCtx;
//
//	@EJB(beanInterface = NodeLocal.class)
//	protected NodeCommon nodeBean;
//
//	@EJB(beanInterface = UserLocal.class)
//	protected UserCommon userBean;
//
//	protected InvocationContext invocationCtx = null;
//
//	private final Logger log = Logger.getLogger(getClass().getName());
//
//	protected void authorizeNodeImpl(InvocationContext invocationCtx)
//			throws SecurityException, NoSuchMethodException {
//		long userId = userBean.getAuthenticatedUser();
//
//		Class<?> targetClass = invocationCtx.getTarget().getClass();
//		Method targetMethod = invocationCtx.getMethod();
//		String methodName = targetClass.getName() + "."
//				+ targetMethod.getName();
//		Object[] parameters = invocationCtx.getParameters();
//
//		if (targetClass == NodeImpl.class) {
//			if (nodeCommonMethods.contains(targetMethod.getName())) {
//				if (targetMethod.getName().equals("create")) {
//					if (parameters.length == 2) {
//						log.info("Requires SuperAdmin: " + methodName
//								+ "(String, String)");
//					} else if (parameters.length == 3) {
//						long parentId = (Long) parameters[2];
//						log.info(String.format("Requires Admin role on node with id '%d'", parentId));
//					} else {
//						log.info("Unhandled method: " + methodName);
//					}
//				} else if (parameters.length == 0
//						&& targetMethod.getName().equals("getToplevelNodes")) {
//					log.info("Requires SuperAdmin: " + methodName + "()");
//				} else if (parameters.length == 0
//						&& targetMethod.getName()
//								.equals("getNodesWhereIsAdmin")) {
//
//				} else {
//					log.info("******* " + methodName);
//					long nodeId = (Long) parameters[0];
//					// if (nodeBean.isNodeAdmin(nodeId, userId)) {
//					// } else {
//					// log
//					// .info(String
//					// .format(
//					// "Requires Admin on the node with id '%s': %s(String, String)",
//					// nodeId, methodName));
//					// }
//				}
//			} else {
//				log.info("Unhandled method: " + methodName);
//			}
//		}
//
//		// if (adminAllowed.contains(methodName)) {
//		// log.info(targetMethod.toString());
//		// }
//
//		// if (adminAllowed.contains(targetMethod)) {
//		// } else {
//		// log.info("qqq");
//		// }
//	}
//
//	@AroundInvoke
//	public Object authenticate(InvocationContext invocationCtx)
//			throws Exception {
//		this.invocationCtx = invocationCtx;
//		String userName = sessionCtx.getCallerPrincipal().getName();
//		String methodName = invocationCtx.getMethod().getName();
//		String className = invocationCtx.getTarget().getClass().getName();
//		// log.info(String.format("User: '%s' called the '%s' method in class '%s'%n",
//		// userName, methodName, className));
//		authorizeNodeImpl(invocationCtx);
//		return invocationCtx.proceed();
//	}
//}