package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.FileMetaCommon;

public abstract class AbstractDevilryFileStream {
	
	protected DevilryConnection connection;
	
	FileMetaCommon fileMeta;
	long fileMetaId;
	
	AbstractDevilryFileStream(long fileMetaId, DevilryConnection connection) {
		this.fileMetaId = fileMetaId;
		this.connection = connection;
	}
	
	protected FileMetaCommon getFileMetaBean() throws NamingException {
		return fileMeta == null ? 
				fileMeta = connection.getFileMeta() : fileMeta;
	}
	
	public int getFileSize() throws NamingException {
		return getFileMetaBean().getSize(fileMetaId);
	}
	
	public String getFilePath() throws NamingException {
		return getFileMetaBean().getFilePath(fileMetaId);
	}
	
}
