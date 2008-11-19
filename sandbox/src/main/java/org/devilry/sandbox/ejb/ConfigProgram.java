package org.devilry.sandbox.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConfigProgram {
	public static void main(String[] args) throws NamingException {

		// Embed openejb
		Properties properties = new Properties();
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		InitialContext ctx = new InitialContext(properties);

		// Connect to the local interface
		Object object = ctx.lookup("ConfigImplLocal");
		ConfigLocal c = (ConfigLocal) object;

		// Use the object just as a normal object
		System.out.println("Max file size: " + c.getMaxFileSize());
		System.out.println("Site name: " + c.getSiteName());
	}
}
