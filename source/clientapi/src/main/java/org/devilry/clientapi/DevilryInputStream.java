package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.FileDataBlockCommon;


public class DevilryInputStream extends AbstractDevilryFileStream {

	FileDataBlockCommon fileDataBlock;
	
	DevilryInputStream(long fileMetaId, DevilryConnection connection) {
		super(fileMetaId, connection);
	}

	private FileDataBlockCommon getFileDataBlockBean() throws NamingException {
		return fileDataBlock == null ? fileDataBlock = connection.getFileDataBlock() : fileDataBlock;
	}
	
	
	Iterator<Long> fileBlockIterator = null;
	
	private Iterator<Long> getFileBlockIterator() throws NamingException {
		
		if (fileBlockIterator != null)
			return fileBlockIterator;
				
		List<Long> ids = getFileMetaBean().getFileDataBlocks(fileMetaId);
		return fileBlockIterator = ids.iterator();
	}
	
	public byte [] read() throws NamingException {
		
		if (!getFileBlockIterator().hasNext()) {
			fileBlockIterator = null;
			return null;
		}

		long fileDataBlockId = getFileBlockIterator().next();
		
		return getFileDataBlockBean().getFileData(fileDataBlockId);
	}
}
