package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.FileMetaCommon;
import org.devilry.core.daointerfaces.FileMetaLocal;

public abstract class AbstractDevilryFileStream {

	int fileSize = 0;
	String filePath;
	
	FileMetaCommon fileMeta;
	long fileMetaId;
	
	protected DevilryConnection connection;
	
	AbstractDevilryFileStream(long fileMetaId, DevilryConnection connection) {
		this.fileMetaId = fileMetaId;
		this.connection = connection;
	}
	
	protected FileMetaCommon getFileMetaBean() throws NamingException {
		return fileMeta == null ? fileMeta = connection.getFileMeta() : fileMeta;
	}
	
	public int getFileSize() throws NamingException {
		return getFileMetaBean().getSize(fileMetaId);
	}
	
	public String getFilePath() throws NamingException {
		return getFileMetaBean().getFilePath(fileMetaId);
	}
	
}
