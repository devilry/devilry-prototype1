package org.devilry.clientapi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.daointerfaces.UserLocal;

public abstract class AbstractClientAPITst {
	protected InitialContext localCtx;

	

	//Student homerNodeAdmin;
	
	//Student bartStudent;
	//Student lisaStudent;

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
		
		
	}


}