package org.devilry.core.bendik;

import java.io.IOException;
import javax.ejb.Remote;

@Remote
public interface RemoteFileBean {
	public void write(byte [] fileData) throws IOException;
	public void close();
	public String getStatus();
}
