package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.FileMetaCommon;
import org.devilry.core.daointerfaces.FileMetaLocal;

public abstract class AbstractDevilryFileStream {
	
	protected DevilryConnection connection;
	
	FileMetaCommon fileMeta;
	long fileMetaId;
	
	AbstractDevilryFileStream(long fileMetaId, DevilryConnection connection) throws NamingException {
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
