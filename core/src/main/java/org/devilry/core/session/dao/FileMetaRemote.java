package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Remote;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Remote
public interface FileMetaRemote {
	
	void create(long deliveryCandidateId);
		
	long getDeliveryCandidateId(long fileMetaId);
	
	String getFilePath(long fileMetaId);
		
	int getSize(long fileMetaId);
	
	List<Long> getFileDataBlocks(long fileMetaId);
	
}