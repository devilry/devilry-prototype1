package org.devilry.core.session.dao;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.UserLocal;

public abstract class AbstractDaoTst {
	protected InitialContext localCtx;

	protected UserLocal userBean;
	protected long homerId;


	public static InitialContext CTX = null;

	public static InitialContext getCtx() throws NamingException {
		if (CTX == null) {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.openejb.client.LocalInitialContextFactory");
			p.put("openejb.deploymentId.format",
					"{ejbName}{interfaceType.annotationName}");
			p.put("openejb.jndiname.format",
					"{ejbName}{interfaceType.annotationName}");
			p.put(Context.SECURITY_PRINCIPAL, "homer");
			p.put(Context.SECURITY_CREDENTIALS, "doh");
			CTX = new InitialContext(p);
		}
		return CTX;
	}

	protected void setupEjbContainer() throws NamingException {
//		System.setProperty("java.util.logging.config.file", "test-classes/logging.properties");
		localCtx = AbstractDaoTst.getCtx();
		userBean = getRemoteBean(UserImpl.class);
		homerId = userBean.create("Homer Simpson", "homr@doh.com", "123");
		userBean.addIdentity(homerId, "homer");
	}

	@SuppressWarnings("unchecked")
	protected <E> E getLocalBean(Class<E> beanImplClass) throws NamingException {
		return (E) localCtx.lookup(beanImplClass.getSimpleName() + "Local");
	}

	@SuppressWarnings("unchecked")
	protected <E> E getRemoteBean(Class<E> beanImplClass)
			throws NamingException {
		return (E) localCtx.lookup(beanImplClass.getSimpleName() + "Remote");
	}
}