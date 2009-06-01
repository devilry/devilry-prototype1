package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Remote;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Remote
public interface FileDataBlockRemote {
	
	/**
	 * Create new FileDataBlock with parent id fileMetaId and data fileData
	 * @param fileMetaId
	 * @param fileData
	 */
	void create(long fileMetaId, byte fileData);
		
	/**
	 * Get id of parent FileMeta
	 * @param fileDataId
	 * @return id of FileMeta
	 */
	long getFileMetaId(long fileDataId);
	
	/**
	 * Get data from datablock with id fileDataId
	 * @param fileDataId
	 * @return the data in bytes
	 */
	byte [] getFileData(long fileDataId);
	
	/**
	 * Get size of data of this datablock
	 * @param fileDataId
	 * @return the size of data in number of bytes
	 */
	int getSize(long fileDataId);
	
}