package org.devilry.core.bendik;

import javax.ejb.Remote;

@Remote
public interface ClientRemote {
	// public FileOutputTransferStream getRemoteFile(String path);

    public void addFile(String fileName, byte [] data);
    public String getStatus();
}