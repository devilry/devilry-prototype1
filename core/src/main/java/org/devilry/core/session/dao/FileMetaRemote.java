package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Remote;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Remote
public interface FileMetaRemote {
	
	/**
	 * Create new FileMeta with deliveryCandidate parent deliveryCandidateId
	 * @param deliveryCandidateId
	 * @param filePath
	 */
	long create(long deliveryCandidateId, String filePath);
		
	/**
	 * Get id of parent deliveryCandidate
	 * @param fileMetaId
	 * @return
	 */
	long getDeliveryCandidate(long fileMetaId);
	
	/**
	 * Get the filepath for the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return the path
	 */
	String getFilePath(long fileMetaId);
		
	/**
	 * Get size of all FileDataBlocks referencing the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return
	 */
	int getSize(long fileMetaId);
	
	/**
	 * Get list of ids of all FileDataBlocks refencing the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return
	 */
	List<Long> getFileDataBlocks(long fileMetaId);
	
}