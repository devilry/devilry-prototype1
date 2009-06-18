package org.devilry.clientapi;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.FileMetaCommon;


public class StudentDeliveryCandidate extends AbstractDeliveryCandidate {
	
	FileMetaCommon fileMeta;
	
	StudentDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(deliveryCandidateId, connection);
	}
	
	protected FileMetaCommon getFileMetaBean() throws NamingException {
		return fileMeta == null ? fileMeta = connection.getFileMeta() : fileMeta;
	}
	
	public DevilryOutputStream addFile(String filePath) throws NamingException {
		
		long fileMetaId = getFileMetaBean().create(deliveryCandidateId, filePath);
		
		DevilryOutputStream outputStream = new DevilryOutputStream(fileMetaId, connection);
		return outputStream;
	}
	
	public List<DevilryInputStream> getDeliveryFiles() throws NamingException {
		
		List<Long> fileIds = getDeliveryCandidateBean().getFiles(deliveryCandidateId);
		
		LinkedList<DevilryInputStream> files = new LinkedList<DevilryInputStream>();
		
		for (long id : fileIds) {
			files.add(new DevilryInputStream(id, connection));
		}
		
		return files;
	}
}
