package org.devilry;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/** Static helper functions for unittests.
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
public class Helpers {

	/** Load an embedded openejb with all beans on the classpath, and find
	 * the requested bean.
	 *
	 * @param remoteInterface Bean class.
	 * @return The requested bean.
	 * @throws javax.naming.NamingException If the bean cannot be found.
	 */
	public static <T> T loadBean(Class<T> remoteInterface) throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		Context ctx = new InitialContext(p);
		Object ref = ctx.lookup(remoteInterface.getName());
		return (T) ref;
	}
}