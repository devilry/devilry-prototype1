package org.devilry.core.session;

import org.junit.Before;
import org.junit.Test;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.Ignore;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
public class NodeMgrTest {

	NodeMgrRemote bean;

	@Before
	public void setUp() throws NamingException {
//		Properties p = new Properties();
//		p.setProperty(Context.SECURITY_PRINCIPAL, "peter");
//		p.setProperty(Context.SECURITY_CREDENTIALS, "pan");
//		p.put(Context.INITIAL_CONTEXT_FACTORY,
//				"org.apache.openejb.client.LocalInitialContextFactory");
//		p.put("openejb.home", "/Users/espeak/code/devilry/core/tull");
//		Context ctx = new InitialContext(p);
//		Object ref = ctx.lookup("NodeMgrImpl");
//		bean = (NodeMgrRemote) ref;
	}

	@Ignore
	@Test
	public void stuff() {
//		bean.addNode("uio");
		System.out.println("hei");
	}
}