package org.devilry.core.session;

import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import org.devilry.core.digest.FileMetaDigest;

@Remote
public interface DeliveryCandidateRemote {
	public void init(long id);
	public long addFile(String filePath);
	public List<Long> getFileIds();
	public FileMetaDigest getFileMeta(long fileId);
	public FileMetaDigest getFileMeta(String filePath);
	public Collection<FileMetaDigest> getFileMetas();
}