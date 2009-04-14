package org.devilry.core;

import javax.ejb.Stateless;

@Stateless(mappedName="ClientBeanRemote")
public class ClientBean implements ClientRemote {
	public RemoteFile addFile(String path) {
		return new RemoteFile(path);
	}

	public RemoteFile getFile(String path) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}