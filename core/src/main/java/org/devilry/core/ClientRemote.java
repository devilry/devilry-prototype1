package org.devilry.core;

import javax.ejb.Remote;

@Remote
public interface ClientRemote {
	public RemoteFile addFile(String path);
	public RemoteFile getFile(String path);
}