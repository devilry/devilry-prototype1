package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.FileDataBlockCommon;

public class DevilryOutputStream extends AbstractDevilryFileStream {

	FileDataBlockCommon fileDataBlock;
	
	DevilryOutputStream(long fileMetaId, DevilryConnection connection) {
		super(fileMetaId, connection);
	}

	private FileDataBlockCommon getFileDataBlockBean() throws NamingException {
		return fileDataBlock == null ? fileDataBlock = connection.getFileDataBlock() : fileDataBlock;
	}
	
	public void write(byte [] data) throws NamingException {
		getFileDataBlockBean().create(fileMetaId, data);
	}
}
