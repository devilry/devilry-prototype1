package org.devilry.core.session.dao;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractDaoTst {
	protected InitialContext localCtx;

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
			CTX = new InitialContext(p);
		}
		return CTX;
	}

	protected void setupEjbContainer() throws NamingException {
		localCtx = AbstractDaoTst.getCtx();
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