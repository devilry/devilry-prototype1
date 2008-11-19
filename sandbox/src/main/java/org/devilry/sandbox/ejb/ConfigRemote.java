package org.devilry.sandbox.ejb;

import javax.ejb.Remote;


@Remote
public interface ConfigRemote {
	public String getSiteName();

	public int getMaxFileSize();
}
