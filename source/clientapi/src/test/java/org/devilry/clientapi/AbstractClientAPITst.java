package org.devilry.clientapi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.UserLocal;

public abstract class AbstractClientAPITst {
	protected InitialContext localCtx;

	protected UserLocal userBean;
	protected long homerId;
	protected long bartId;
	protected long lisaId;

	Student homerNodeAdmin;
	
	Student bartStudent;
	Student lisaStudent;

	//public static InitialContext CTX = null;

	DevilryConnection connection;
	
	/*
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
*/
	
	protected void setupEjbContainer() throws NamingException {
//		System.setProperty("java.util.logging.config.file", "test-classes/logging.properties");
		
		connection = new DevilryConnectionOpenEJB();
		
		//localCtx = AbstractClientAPITst.getCtx();
		
		addTestUsers();
	}
	
	protected void addTestUsers() throws NamingException {
		//userBean = getRemoteBean(UserImpl.class);
		userBean = connection.getUser();
		
		homerId = userBean.create("Homer Simpson", "homr@doh.com", "123");
		userBean.addIdentity(homerId, "homer");
		
		//homerNodeAdmin = 
				
		bartId = userBean.create("Bart Simpson", "bart@doh.com", "1234");
		userBean.addIdentity(bartId, "bart");
		
		lisaId = userBean.create("Lisa Simpson", "lisa@doh.com", "1234");
		userBean.addIdentity(lisaId, "lisa");
				
		bartStudent = new Student(bartId, connection);
		lisaStudent = new Student(lisaId, connection);
	}

	/*
	@SuppressWarnings("unchecked")
	protected <E> E getLocalBean(Class<E> beanImplClass) throws NamingException {
		return (E) localCtx.lookup(beanImplClass.getSimpleName() + "Local");
	}

	@SuppressWarnings("unchecked")
	protected <E> E getRemoteBean(Class<E> beanImplClass)
			throws NamingException {
		return (E) localCtx.lookup(beanImplClass.getSimpleName() + "Remote");
	}
	*/
}