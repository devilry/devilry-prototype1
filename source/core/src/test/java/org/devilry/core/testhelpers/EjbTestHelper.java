package org.devilry.core.testhelpers;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EjbTestHelper {

	private String username, password;

	public EjbTestHelper(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	private InitialContext getCtx() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		p.put("openejb.deploymentId.format", "{ejbName}");
		p.put("openejb.jndiname.format",
				"{ejbClass.simpleName}{interfaceType.annotationName}");
		p.put(Context.SECURITY_PRINCIPAL, username);
		p.put(Context.SECURITY_CREDENTIALS, password);
		return new InitialContext(p);
	}


	@SuppressWarnings("unchecked")
	public <E> E getLocalBean(Class<E> beanImplClass) throws NamingException {
		return (E) getCtx().lookup(beanImplClass.getSimpleName() + "Local");
	}

	@SuppressWarnings("unchecked")
	public <E> E getRemoteBean(Class<E> beanImplClass)
			throws NamingException {
		return (E) getCtx().lookup(beanImplClass.getSimpleName() + "Remote");
	}
}
