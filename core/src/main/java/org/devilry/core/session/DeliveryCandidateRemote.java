package org.devilry.core.session;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeliveryCandidateRemote {
	public void init(long id);
	public long addFile(String filePath);
	public List<Long> getFileIds();
}