package org.devilry.sandbox.ejb;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ConfigImplTest {

	private InitialContext initialContext;

	@Before
	public void setUp() throws Exception {
		Properties properties = new Properties();
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");

		initialContext = new InitialContext(properties);
	}

	@Test
	public void configViaRemoteInterface() throws Exception {
		Object object = initialContext.lookup("ConfigImplRemote");

		assertNotNull(object);
		assertTrue(object instanceof ConfigRemote);
		ConfigRemote c = (ConfigRemote) object;
		assertEquals("Demo site", c.getSiteName());
		assertEquals(5, c.getMaxFileSize());
	}

	@Test
    public void configViaLocalInterface() throws Exception {
        Object object = initialContext.lookup("ConfigImplLocal");

		assertNotNull(object);
		assertTrue(object instanceof ConfigLocal);
		ConfigLocal c = (ConfigLocal) object;
		assertEquals("Demo site", c.getSiteName());
		assertEquals(5, c.getMaxFileSize());
    }
}
