package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.FileDataBlockLocal;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.daointerfaces.FileMetaLocal;

public class DevilryOutputStream extends AbstractDevilryFileStream {

	FileDataBlockLocal fileDataBlock;
	
	DevilryOutputStream(long fileMetaId, DevilryConnection connection) {
		super(fileMetaId, connection);
	}

	private FileDataBlockLocal getFileDataBlockBean() throws NamingException {
		return fileDataBlock == null ? fileDataBlock = connection.getFileDataBlock() : fileDataBlock;
	}
	
	public void write(byte [] data) throws NamingException {
		getFileDataBlockBean().create(fileMetaId, data);
	}
}
