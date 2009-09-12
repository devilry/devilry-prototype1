package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.FileMetaCommon;


public class StudentDeliveryCandidate extends AbstractDeliveryCandidate {
	
	FileMetaCommon fileMeta;
	
	StudentDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(deliveryCandidateId, connection);
	}
	
	protected FileMetaCommon getFileMetaBean() throws NamingException {
		return fileMeta == null ? fileMeta = connection.getFileMeta() : fileMeta;
	}
	
	public DevilryOutputStream addFile(String filePath) throws NamingException, UnauthorizedException {
		
		long fileMetaId = getFileMetaBean().create(deliveryCandidateId, filePath);
		
		DevilryOutputStream outputStream = new DevilryOutputStream(fileMetaId, connection);
		return outputStream;
	}
	
}
