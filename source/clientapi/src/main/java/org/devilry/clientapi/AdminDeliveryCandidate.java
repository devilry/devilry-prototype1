package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.FileMetaCommon;


public class AdminDeliveryCandidate extends AbstractDeliveryCandidate {
	
	FileMetaCommon fileMeta;
	
	AdminDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
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
