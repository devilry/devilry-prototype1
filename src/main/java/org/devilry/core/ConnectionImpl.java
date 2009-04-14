
package org.devilry.core;

import javax.ejb.Stateful;
import javax.annotation.security.RolesAllowed;

@Stateful
public class ConnectionImpl implements ConnectionRemote {

	@RolesAllowed("admin")
	public String test() {
		return "It worked!";
	}
}

